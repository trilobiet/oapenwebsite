package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.oapen.oapenwebsite.rss.RssItem;

@Controller
public class BlogController extends BaseController {

	@RequestMapping({"/blog"})
	public ModelAndView showBlog(
			@RequestParam( value="link", required=false ) String link
		) throws Exception {

		ModelAndView mv = new ModelAndView("blog");
		
		try {
			List<RssItem> blogPosts = rssService.getItems(10);
			
			/* display post by link or first post */
			RssItem featuredPost = null;
			
			if (link != null) 
				featuredPost = rssService.getItemByLink(blogPosts, link).orElse(featuredPost);
			else if ( !blogPosts.isEmpty() )
				featuredPost = blogPosts.get(0);
	
			blogPosts.remove(featuredPost);
			
			mv.addObject("blogPosts", blogPosts);
			mv.addObject("featuredPost", featuredPost);	

		}
		catch (Exception e) {
			log.error(e);
		}
		
		return mv;
	}
	
}
