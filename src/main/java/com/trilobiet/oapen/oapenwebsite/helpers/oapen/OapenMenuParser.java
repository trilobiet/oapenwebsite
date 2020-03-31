package com.trilobiet.oapen.oapenwebsite.helpers.oapen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.trilobiet.graphqlweb.datamodel.Section;

public class OapenMenuParser {

	final List<Section> sections;

	public OapenMenuParser(List<Section> sections) {
		this.sections = sections;
	}
	
	public List<Section> getSectionsForHeader() {
		
		return getMenu(3);
	}

	public List<Section> getSectionsForMainLeft() {
		
		return getMenu(1);
	}
	
	public List<Section> getSectionsForMainRight() {
		
		return getMenu(2);
	}
	
	public List<Section> getSectionsForFooter() {
		
		List<Section> f = new ArrayList<>(getMenu(1));
		f.addAll(getMenu(2));
		return f;
	}
	

	private List<Section> getMenu(final int group) {

		List<Section> list = sections.stream()
			.filter( section -> section.getGroupNumber() == group )
			.filter( section -> section.isHasMenuItem() == true )
			.collect(Collectors.toList());
		
		// list.stream().forEach(section -> new OapenTopicGrouper( section.getTopics() )  ) ;
		
		return list;
		
	}
	
	
}
