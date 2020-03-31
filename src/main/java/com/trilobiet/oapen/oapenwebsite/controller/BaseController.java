package com.trilobiet.oapen.oapenwebsite.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import com.trilobiet.oapen.oapenwebsite.data.ArticleService;
import com.trilobiet.oapen.oapenwebsite.data.FileService;
import com.trilobiet.oapen.oapenwebsite.data.SnippetService;
import com.trilobiet.oapen.oapenwebsite.data.TopicService;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryService;
import com.trilobiet.oapen.oapenwebsite.rss.RssService;

@Controller
public class BaseController {

	protected final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	protected TopicService topicService;

	@Autowired
	protected ArticleService articleService;
	
	@Autowired
	protected SnippetService snippetService;

	@Autowired
	protected FileService fileService;

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected RssService rssService;	

	@Autowired
	protected Environment environment;	
    
}
