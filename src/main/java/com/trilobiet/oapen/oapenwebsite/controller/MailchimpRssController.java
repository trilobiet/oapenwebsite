package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.oapen.oapenwebsite.rss.RssItem;

@Controller
public class MailchimpRssController extends BaseController {

	@RequestMapping({"/newsletter"})
	public ModelAndView showBlog(
			@RequestParam( value="link", required=false ) String link
		) throws Exception {

		ModelAndView mv = new ModelAndView("mailchimp");
		List<RssItem> newsletters = mailchimpService.getItems(10);
		
		/* display post by link or first post */
		RssItem featuredNewsletter = null;
		if (link != null) {
			featuredNewsletter = mailchimpService.getItemByLink(newsletters, link).orElse(featuredNewsletter);
		}	
		else if ( !newsletters.isEmpty() ){
			featuredNewsletter = newsletters.get(0);
		}

		newsletters.remove(featuredNewsletter);
		
		mv.addObject("newsletters", newsletters);
		mv.addObject("featuredNewsletter", featuredNewsletter);	
		
		return mv;
	}
	
}
