package com.trilobiet.oapen.oapenwebsite.helpers;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Topic;

public class CmsUtils {
	
	public static String getCssClass(Topic topic) {
		
		StringBuilder sb = new StringBuilder();
		if ( hasValue(topic.getCssClass() ) ) sb.append(topic.getCssClass()).append(" ");
		sb.append( "topic-" ).append( topic.getSlug() );
		return sb.toString();
	}
	
	public static String getCssClass(Section section) {
		
		StringBuilder sb = new StringBuilder();
		sb.append( "section-" ).append( section.getSlug() );
		return sb.toString();
	}

	public static String getCssClass(Article article) {
		
		StringBuilder sb = new StringBuilder();
		if ( hasValue(article.getCssClass() ) ) sb.append(article.getCssClass()).append(" ");
		sb.append( "article-" ).append( article.getSlug() );
		return sb.toString();
	}
	
	public static String getParamValue(Topic topic, String key) {
		
		String s = "";
		ParamMap p = new ParamMap( topic.getParams() );
		if ( p.containsKey(key) ) s = p.get(key);
		return s;
	}
	
	
	static private boolean hasValue(String s) {
		
		return s != null && !s.trim().equals("");
	}

}

