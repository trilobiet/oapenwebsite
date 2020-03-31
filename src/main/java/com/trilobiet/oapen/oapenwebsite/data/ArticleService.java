package com.trilobiet.oapen.oapenwebsite.data;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.ArticleOutline;
import com.trilobiet.graphqlweb.datamodel.Category;
import com.trilobiet.graphqlweb.datamodel.Topic;

public interface ArticleService {

	List<Category> getCategories() throws DaoException;
	List<Article> getArticlesByTopic(Topic topic) throws DaoException;
	Optional<Article> getArticleBySlug(String slug) throws DaoException;
	List<Article> getByFieldValue(String field, String value) throws DaoException;
	List<Article> getByFieldContainsValue(String field, String value) throws DaoException;
	Set<ArticleOutline> getLinked(Article article) throws DaoException;
}