package com.trilobiet.oapen.oapenwebsite.data.oapen;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.dao.FieldValueQuery.MatchType;
import com.trilobiet.graphqlweb.dao.TopicDao;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLFieldValueQuery;
import com.trilobiet.oapen.oapenwebsite.data.TopicService;
import com.trilobiet.oapen.oapenwebsite.markdown2html.Md2HtmlConverter;

public class OApenTopicService implements TopicService {
	
	private final TopicDao topicDao;
	private final Md2HtmlConverter md2HtmlConverter;
	
	public OApenTopicService(TopicDao topicDao, Md2HtmlConverter md2HtmlConverter) {
		this.topicDao = topicDao;
		this.md2HtmlConverter = md2HtmlConverter;
	}

	@Override
	@Cacheable(value="sectionsCache",key="#root.methodName")
	public List<Section> getSections() throws DaoException {

		List<Section> sections = topicDao.listSections();
		md2HtmlConverter.convertSections(sections);
		return sections;
	}

	@Override
	@Cacheable(value="sectionsCache",key="#slug")
	public Optional<Section> getSectionBySlug(String slug) throws DaoException {

		Optional<Section> osection = topicDao.getSectionBySlug(slug);
		if (osection.isPresent())	md2HtmlConverter.convert(osection.get());
		return osection;
	}

	@Override
	@Cacheable(value="topicsCache",key="#slug")
	public Optional<Topic> getTopicBySlug(String slug) throws DaoException {
		
		Optional<Topic> otopic = topicDao.getBySlug(slug);
		if (otopic.isPresent())	md2HtmlConverter.convert(otopic.get());
		return otopic;
	}

	@Override
	public List<Topic> getByFieldValue(String field, String value) throws DaoException {

		GraphQLFieldValueQuery q = 
				new GraphQLFieldValueQuery.Builder(field,value)
				.setSort("index:asc")
				.build();
		
		List<Topic> topics = topicDao.find(q);
		md2HtmlConverter.convertTopics(topics);
		
		return topics;
	}

	@Override
	public List<Topic> getByFieldContainsValue(String field, String value) throws DaoException {

		GraphQLFieldValueQuery q = 
				new GraphQLFieldValueQuery.Builder(field,value)
				.setSort("index:asc")
				.setMatchType(MatchType.CONTAINS)
				.build();
		
		List<Topic> topics = topicDao.find(q);
		md2HtmlConverter.convertTopics(topics);
		
		return topics;
	}
	
}
