package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.File;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.oapen.oapenwebsite.helpers.CmsUtils;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryItem;
import com.trilobiet.oapen.oapenwebsite.rss.RssItem;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/home")
	public ModelAndView showHome(HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("home"); 

		mv.addObject("ip",request.getRemoteAddr());
		
		// Catch all errors here: home page must always be rendered, erroneous sections can be empty
		
		List<RepositoryItem> featuredItems = null;
		try {
			featuredItems = repositoryService.getFeaturedItems(10);
			mv.addObject("featuredItems", featuredItems);
		} catch (Exception e) {
			log.warn(e);
			log.warn("Will fall back to list of newest items.");
		}

		if (featuredItems == null || featuredItems.isEmpty()) {
			try {
				featuredItems = repositoryService.getNewestItems(10);
				mv.addObject("featuredItems", featuredItems);
			} catch (Exception e) {
				log.error(e);
			}
		}
		
		try {
			Optional<Snippet> introText = snippetService.getSnippet("home-intro");
			if (introText.isPresent() ) mv.addObject("home_intro",introText.get().getCode());
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<Article> showcases = articleService.getByFieldContainsValue("params", "showcase=true");
			mv.addObject("showcases",showcases);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<Topic> topics = topicService.getByFieldContainsValue("params", "topic=toolkit");
			if(topics.isEmpty()) mv.addObject("topictoolkit",null);
			else {
				Topic topic = topics.get(0);
				mv.addObject("topictoolkit",topic);
				String toolkitdownload = CmsUtils.getParamValue(topic, "download");
				mv.addObject("toolkitdownload",toolkitdownload);
			}
		} catch (Exception e) {
			log.error(e);
		}

		try {
			List<Article> spotlights = articleService.getByFieldValue("spotlight", "true");
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
			Optional<Snippet> otwitter = snippetService.getSnippet("twitter-timeline");
			Snippet ttl = otwitter.orElseGet(()->new Snippet());
			mv.addObject("twittertimeline",ttl);
		} catch (Exception e) {
			log.error(e);
		}
		
		try {
			List<Topic> funders = topicService.getByFieldValue("type", "funder");
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
		List<Section> sections = topicService.getSections();
		mv.addObject("sections", sections);

		return mv;
	}
	
}
