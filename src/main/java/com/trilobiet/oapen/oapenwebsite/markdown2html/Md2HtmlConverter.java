package com.trilobiet.oapen.oapenwebsite.markdown2html;

import java.util.List;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.graphqlweb.datamodel.Topic;

public interface Md2HtmlConverter {
	
	void convert(Section section);
	void convert(Topic topic);
	void convert(Article article);
	void convert(Snippet snippet);

	default void convertSections(List<Section> sections) {
		
		sections.stream().forEach(s -> convert(s));
	}
	
	default void convertTopics(List<Topic> topics) {
		
		topics.stream().forEach(t -> convert(t));
	}
	
	default void convertArticles(List<Article> articles) {
		
		articles.stream().forEach(a -> convert(a));
	}

}
