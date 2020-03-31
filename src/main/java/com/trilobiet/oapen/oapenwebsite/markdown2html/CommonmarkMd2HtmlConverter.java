package com.trilobiet.oapen.oapenwebsite.markdown2html;


import java.util.Arrays;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.graphqlweb.datamodel.Topic;

public class CommonmarkMd2HtmlConverter implements Md2HtmlConverter {
	
	private Parser parser;
	private HtmlRenderer renderer;
	
	public CommonmarkMd2HtmlConverter() {
		
		List<Extension> extensions = Arrays.asList(TablesExtension.create());
		parser = Parser.builder().extensions(extensions).build();
		renderer = HtmlRenderer.builder().extensions(extensions).build();
	}

	@Override
	public void convert(Section section) {
		
		if (section.getDescription() != null) {
			Node node = parser.parse( section.getDescription() );
			section.setDescription( renderer.render(node) );
		}	
	}
	
	@Override
	public void convert(Topic topic) {
		
		if (topic.getDescription() != null) {
			Node node = parser.parse( topic.getDescription() );
			topic.setDescription( renderer.render(node) );
		}	
	}
	
	@Override
	public void convert(Article article) {
		
		if (article.getSummary() != null) {
			Node node = parser.parse( article.getSummary() );
			article.setSummary( renderer.render(node) );
		}	
		
		if (article.getContent() != null) {
			Node node = parser.parse( article.getContent() );
			article.setContent( renderer.render(node) );
		}	
	}

	@Override
	public void convert(Snippet snippet) {
		
		if(snippet.getCode() != null) {
			Node node = parser.parse( snippet.getCode() );
			snippet.setCode( renderer.render(node) );
		}
		
	}
	
}

