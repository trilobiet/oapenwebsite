package com.trilobiet.oapen.oapenwebsite.data.oapen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.cache.annotation.Cacheable;

import com.trilobiet.graphqlweb.dao.ArticleDao;
import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.dao.FieldValueQuery.MatchType;
import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.ArticleOutline;
import com.trilobiet.graphqlweb.datamodel.Category;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLFieldValueQuery;
import com.trilobiet.oapen.oapenwebsite.data.ArticleService;
import com.trilobiet.oapen.oapenwebsite.markdown2html.Md2HtmlConverter;

public class OApenArticleService implements ArticleService {
	
	private final ArticleDao articleDao;
	private final Md2HtmlConverter md2HtmlConverter;
	
	public OApenArticleService(ArticleDao articleDao, Md2HtmlConverter md2HtmlConverter) {
		this.articleDao = articleDao;
		this.md2HtmlConverter = md2HtmlConverter;
	}

	@Override
	public List<Category> getCategories() throws DaoException {

		return articleDao.listCategories();
	}

	@Override
	@Cacheable(value="topicsCache",key="#topic.id")
	public List<Article> getArticlesByTopic(Topic topic) throws DaoException {
		
		List<Article> articles = selectArticlesForTopic(topic);
		md2HtmlConverter.convertArticles(articles);
		return articles;
	}
	
	@Override
	@Cacheable(value="articlesCache",key="#slug")
	public Optional<Article> getArticleBySlug(String slug) throws DaoException {

		Optional<Article> oa = articleDao.getBySlug(slug);
		oa.ifPresent( a -> md2HtmlConverter.convert(a) );
		return oa;
	}
	
	@Override
	@Cacheable(value="articlesCache",key="#field+'-is-'+#value")
	public List<Article> getByFieldValue(String field, String value) throws DaoException {
		
		GraphQLFieldValueQuery q = 
				new GraphQLFieldValueQuery.Builder(field,value)
				.setSort("index:asc")
				.build();
		
		List<Article> articles = articleDao.find(q);
		md2HtmlConverter.convertArticles(articles);
		
		return articles;
	}

	@Override
	@Cacheable(value="articlesCache",key="#field+'-contains-'+#value")
	public List<Article> getByFieldContainsValue(String field, String value) throws DaoException {
		
		GraphQLFieldValueQuery q = 
				new GraphQLFieldValueQuery.Builder(field,value)
				.setMatchType(MatchType.CONTAINS)
				.setSort("index:asc")
				.build();
		
		List<Article> articles = articleDao.find(q);
		
		md2HtmlConverter.convertArticles(articles);
		
		return articles;
	}

	/**
	 * Find articles that have at least one of the inputed tags as their own tag
	 * @param tags
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Set<ArticleOutline> getLinked(Article article) throws DaoException {

		Set<ArticleOutline> articles = new HashSet<>(); 
		List<String> tagList = tagList( article );

		tagList.stream()
		.filter(tag -> tag.trim().length()>1) // matches on empty tags will return the entire collection 
		.forEach( tag -> {
			try {
				articles.addAll(
					getByFieldContainsValue("tags",tag)
				);
			} catch (DaoException e) { /* just forget it*/	}
		});
		
		// remove article itself from linked list
		articles.removeIf(a->a.getId().equals(article.getId()));

		TreeSet<ArticleOutline> sorted = new TreeSet<>(new ArticleTitleComparator());
		sorted.addAll(articles);
		
		return sorted;
	}
	
	@Cacheable(value="articlesCache",key="#topic.id")
	private List<Article> selectArticlesForTopic(Topic topic) throws DaoException {
		
		List<Article> articles = new ArrayList<>();
		String displaytype = topic.getArticleDisplay();
		
		if(displaytype == null || displaytype.equals("Show_list_of_articles")) {
			articles = articleDao.list(topic, "index");
		}

		else if (displaytype.equals("Show_first_article") 
			  || displaytype.equals("Show_lead_article_and_list_titles")) {
			
			List<ArticleOutline> aoList = topic.getArticles();
			if (!aoList.isEmpty()) {
				ArticleOutline ao = aoList.get(0);
				Optional<Article> first = articleDao.get(ao.getId());
				if (first.isPresent()) articles.add(first.get());
			}
		}	
		
		// Other display options do not include articles, just an empty list
		
		md2HtmlConverter.convertArticles(articles);
		return articles;
	}	


	private List<String> tagList(Article article) {
		
		List<String> taglist = new ArrayList<>();

		String tags = article.getTags();

		if(tags != null) {
			String[] artags = tags.split(",");
			taglist.addAll( Arrays.asList(artags) );
		}
		return taglist;
	}
	
}
