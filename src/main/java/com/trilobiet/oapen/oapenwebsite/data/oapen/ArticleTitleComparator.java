package com.trilobiet.oapen.oapenwebsite.data.oapen;

import com.trilobiet.graphqlweb.datamodel.NormalizedComparator;
import com.trilobiet.graphqlweb.datamodel.SortableArticle;

public class ArticleTitleComparator implements NormalizedComparator<SortableArticle> {

	@Override
	public int compare(SortableArticle a1, SortableArticle a2) {
		
		return normalized(a1.getTitle().trim())
				.compareToIgnoreCase(normalized(a2.getTitle().trim()));
	}
}
