package com.trilobiet.oapen.oapenwebsite.data;

import java.util.List;
import java.util.Optional;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Topic;

public interface TopicService {

	List<Section> getSections() throws DaoException;
	Optional<Section> getSectionBySlug(String slug) throws DaoException;
	Optional<Topic> getTopicBySlug(String slug) throws DaoException;
	List<Topic> getByFieldValue(String field, String value) throws DaoException;
	List<Topic> getByFieldContainsValue(String field, String value) throws DaoException;
	
}