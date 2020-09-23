package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.graphqlweb.datamodel.File;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.graphqlweb.helpers.CmsUtils;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.article.ArticleImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.section.SectionImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.snippet.SnippetImp;
import com.trilobiet.graphqlweb.implementations.aexpgraphql2.topic.TopicImp;
import com.trilobiet.oapen.oapenwebsite.rss.RssItem;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/home")
	public ModelAndView showHome(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("home"); 

		mv.addObject("ip",request.getRemoteAddr());
		
		// Catch all errors here: home page must always be rendered, erroneous sections can be empty
		
		try {
			Optional<SnippetImp> introText = snippetService.getSnippet("home-intro");
			if (introText.isPresent() ) mv.addObject("home_intro",introText.get().getCode());
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<ArticleImp> showcases = articleService.getByFieldContainsValue("params", "showcase=true");
			mv.addObject("showcases",showcases);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<TopicImp> topics = topicService.getByFieldContainsValue("params", "topic=toolkit");
			if(topics.isEmpty()) mv.addObject("topictoolkit",null);
			else {
				Topic topic = topics.get(0);
				mv.addObject("topictoolkit",topic);
				String toolkitUrl = CmsUtils.getParamValue(topic, "href");
				mv.addObject("toolkitUrl",toolkitUrl);
			}
		} catch (Exception e) {
			log.error(e);
		}

		try {
			List<ArticleImp> spotlights = articleService.getByFieldValue("spotlight", "true");
			mv.addObject("spotlights",spotlights);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			// Get latest blog post(s)
			List<RssItem> rssItems = rssService.getItems(2);
			mv.addObject("rssItems",rssItems);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			Optional<SnippetImp> otwitter = snippetService.getSnippet("twitter-timeline");
			Snippet ttl = otwitter.orElseGet(()->new SnippetImp());
			mv.addObject("twittertimeline",ttl);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<TopicImp> funders = topicService.getByFieldValue("type", "funder");
			mv.addObject("funders",funders);

			// Get associated logo url (digital ocean) for all funders
			Map<String,String> funderlogos = new HashMap<>();
			funders.forEach( topic -> {
				String logoname = CmsUtils.getParamValue(topic, "logo");
				try { 
					File f = fileService.getFirstWithName(logoname).orElseThrow();
					funderlogos.put(topic.getId(), f.getUrl());
				} catch (Exception e) {}
			});
			
			mv.addObject("funderlogos",funderlogos);
			
		} catch (Exception e) {
			log.error(e);
		}

		return mv;
	}
	
	@RequestMapping("/sitemap") 
	public ModelAndView showSitemap() throws Exception {
		
		ModelAndView mv = new ModelAndView("sitemap"); 
		List<SectionImp> sections = sectionService.getSections();
		mv.addObject("sections", sections);

		return mv;
	}
	
}
