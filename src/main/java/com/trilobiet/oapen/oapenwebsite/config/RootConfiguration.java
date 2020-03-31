package com.trilobiet.oapen.oapenwebsite.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.trilobiet.graphqlweb.dao.ArticleDao;
import com.trilobiet.graphqlweb.dao.FileDao;
import com.trilobiet.graphqlweb.dao.SnippetDao;
import com.trilobiet.graphqlweb.dao.TopicDao;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLArticleDao;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLFileDao;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLSnippetDao;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLTopicDao;
import com.trilobiet.oapen.oapenwebsite.data.ArticleService;
import com.trilobiet.oapen.oapenwebsite.data.FileService;
import com.trilobiet.oapen.oapenwebsite.data.SnippetService;
import com.trilobiet.oapen.oapenwebsite.data.TopicService;
import com.trilobiet.oapen.oapenwebsite.data.oapen.OApenArticleService;
import com.trilobiet.oapen.oapenwebsite.data.oapen.OApenFileService;
import com.trilobiet.oapen.oapenwebsite.data.oapen.OApenSnippetService;
import com.trilobiet.oapen.oapenwebsite.data.oapen.OApenTopicService;
import com.trilobiet.oapen.oapenwebsite.markdown2html.FlexmarkMd2HtmlConverter;
import com.trilobiet.oapen.oapenwebsite.markdown2html.Md2HtmlConverter;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryService;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.dspace.DSpaceRepositoryService;
import com.trilobiet.oapen.oapenwebsite.rss.RssService;
import com.trilobiet.oapen.oapenwebsite.rss.hypotheses.HypothesesRssService;

@Configuration
@ComponentScan (
	basePackages = {"com.trilobiet.oapen.oapenwebsite"},
	excludeFilters = {
			@Filter( type=FilterType.ANNOTATION, value=EnableWebMvc.class ) 
	}
)
public class RootConfiguration {
	
	@Autowired
	public Environment env;	
	
	@Bean 
	public TopicService topicService() {
		return new OApenTopicService( topicDao(), md2HtmlConverter() );
	}

	@Bean 
	public ArticleService articleService() {
		return new OApenArticleService( articleDao(), md2HtmlConverter() );
	}

	@Bean 
	public SnippetService snippetService() {
		return new OApenSnippetService( snippetDao(), md2HtmlConverter() );
	}
	
	@Bean 
	public FileService fileService() {
		return new OApenFileService( fileDao() );
	}

	@Bean 
	public RssService rssService() {
		return new HypothesesRssService(env.getProperty("url_feed_hypotheses"));
	}
	
	@Bean
	public TopicDao topicDao() {
		// http://localhost:1337/graphql
		return new GraphQLTopicDao(env.getProperty("url_strapi"));
	}
	
	@Bean
	public ArticleDao articleDao() {
		return new GraphQLArticleDao(env.getProperty("url_strapi"));
	}
	
	@Bean
	public SnippetDao snippetDao() {
		return new GraphQLSnippetDao(env.getProperty("url_strapi"));
	}
	
	@Bean
	public FileDao fileDao() {
		return new GraphQLFileDao(env.getProperty("url_strapi"));
	}

	@Bean 
	public Md2HtmlConverter md2HtmlConverter() {
		return new FlexmarkMd2HtmlConverter();
	}
	
	@Bean 
	public RepositoryService repositoryService() {
		return new DSpaceRepositoryService(
			env.getProperty("url_dspace_api"), 
			env.getProperty("dspace_featured_collection_id")
		);
	}
	
}
