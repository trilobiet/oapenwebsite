package com.trilobiet.oapen.oapenwebsite.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import com.trilobiet.graphqlweb.implementations.aexpgraphql2.article.ArticleImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.file.FileImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.section.SectionImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.ArticleService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.FileService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.SectionService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.SnippetService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.service.TopicService;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.snippet.SnippetImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.topic.TopicImp;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryService;
import com.trilobiet.oapen.oapenwebsite.rss.RssService;

@Controller
public class BaseController {

	protected final Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	protected SectionService<SectionImp> sectionService;

	@Autowired
	protected TopicService<TopicImp> topicService;

	@Autowired
	protected ArticleService<ArticleImp> articleService;
	
	@Autowired
	protected SnippetService<SnippetImp> snippetService;

	@Autowired
	protected FileService<FileImp> fileService;

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected RssService rssService;	

	@Autowired
	protected RssService mailchimpService;	
	
	@Autowired
	protected Environment environment;	
	
	/**
	 * Use to prepend section to prevnext links
	 * 
	 * @param sectionslug
	 * @return
	 */
	protected String sectionPrefix(String sectionslug) {
		
		StringBuilder sb = new StringBuilder();
		
		if (sectionslug != null) {
			sb.append("/").append(sectionslug);
		}
		
		return sb.append("/article/").toString();
	}
    
}
