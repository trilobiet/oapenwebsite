package com.trilobiet.oapen.oapenwebsite.markdown2html;

import java.util.Arrays;

import com.trilobiet.graphqlweb.datamodel.Article;
import com.trilobiet.graphqlweb.datamodel.Section;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.graphqlweb.datamodel.Topic;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class FlexmarkMd2HtmlConverter implements Md2HtmlConverter {
	
	private Parser parser;
	private HtmlRenderer renderer;
	

	public FlexmarkMd2HtmlConverter() {
		
		MutableDataSet options = new MutableDataSet();
		
		options.setFrom(ParserEmulationProfile.COMMONMARK);
		options.set(Parser.EXTENSIONS, Arrays.asList(
			FootnoteExtension.create(),
			TablesExtension.create(),
			TypographicExtension.create()
		));
		
		//options.set(Parser.HEADING_NO_LEAD_SPACE, true);
		options.set(Parser.HEADING_NO_ATX_SPACE, true);
		options.set(HtmlRenderer.GENERATE_HEADER_ID, true);
		
		parser = Parser.builder(options).build();
		renderer = HtmlRenderer.builder(options).build();
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
