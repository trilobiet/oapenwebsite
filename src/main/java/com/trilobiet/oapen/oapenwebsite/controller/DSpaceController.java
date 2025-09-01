package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.graphqlweb.helpers.CmsUtils;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryItem;


@Controller
public class DSpaceController extends BaseController {
	
	
	@RequestMapping("/featuredtitles")
	public ModelAndView showFeatured() {
		
		ModelAndView mv = new ModelAndView("include/featuredtitles");  
		
		List<RepositoryItem> featuredItems = null;
		/*
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
		}*/
		
		try {
			featuredItems = repositoryService.getNewestItems(20);
			mv.addObject("featuredItems", featuredItems);
		} catch (Exception e) {
			log.error(e);
		}
		
		return mv;
	}

	
	@RequestMapping("/fundertitles/{slug}")
	public ModelAndView showFunderItems(
			@PathVariable( "slug" ) String slug
		) {
		
		ModelAndView mv = new ModelAndView("include/fundertitles");  
		
		try { // even when this fails, page must be shown
			Topic topic = topicService.getTopicBySlug(slug)
					.orElseThrow( () -> new ResourceNotFoundException() );
			String collection = CmsUtils.getParamValue(topic, "collection");
			List<RepositoryItem> featuredItems = repositoryService.getFunderItems(collection, 12);
			mv.addObject("topic", topic);
			mv.addObject("collection", collection);
			mv.addObject("featuredItems", featuredItems);
		}
		catch( Exception e) {
			log.error(e);
		}
		
		return mv;
	}

}
