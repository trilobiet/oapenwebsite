package com.trilobiet.oapen.oapenwebsite.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.ArticleOutline;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.trilobiet.oapen.oapenwebsite.data.ResourceNotFoundException;
import com.trilobiet.oapen.oapenwebsite.helpers.CmsUtils;

@Controller
public class ArticleController extends BaseController {

	@RequestMapping({
		"{sectionslug}/{topicslug}/article/{slug}",
		"{sectionslug}/article/{slug}",
		"/article/{slug}"
	})
	public ModelAndView showArticle(
			@PathVariable( "slug" ) String slug,
			@PathVariable(required=false, name="topicslug" ) String topicslug,
			@PathVariable(required=false, name="sectionslug" ) String sectionslug
		) throws Exception {
		
		ModelAndView mv = new ModelAndView("article");
		
		Article article = articleService.getArticleBySlug(slug)
				.orElseThrow( () -> new ResourceNotFoundException() );
		mv.addObject("article", article);
		mv.addObject( "bodyClass", CmsUtils.getCssClass(article) );
		
		Set<ArticleOutline> linked = articleService.getLinked(article);
		mv.addObject("linked", linked);
		// Set<ArticleOutline> related = articleService.getRelated(article);
		// related.removeAll(linked); // no doubles
		// mv.addObject("related", related);
		
		if(topicslug != null) {
			Optional<Topic> otopic = topicService.getTopicBySlug(topicslug);
			mv.addObject("topic",otopic.orElse(new Topic()));
		}

		if(sectionslug != null) {
			Optional<Section> osection = topicService.getSectionBySlug(sectionslug);
			mv.addObject("section",osection.orElse(new Section()));
		}
		
		return mv;
	}
	
}
