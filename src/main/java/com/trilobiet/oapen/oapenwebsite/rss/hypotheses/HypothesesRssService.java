package com.trilobiet.oapen.oapenwebsite.rss.hypotheses;

import java.util.List;

import com.trilobiet.oapen.oapenwebsite.rss.RssException;
import com.trilobiet.oapen.oapenwebsite.rss.RssItem;
import com.trilobiet.oapen.oapenwebsite.rss.RssServiceImp;

public class HypothesesRssService extends RssServiceImp {
	
	/**
	 * Constructor 
	 * @param feedUrl
	 */
	public HypothesesRssService(String feedUrl) {
		super(feedUrl);
	}
	
	// Test
	public static void main(String[] args) {

		String url = "https://dariahopen.hypotheses.org/feed";
		HypothesesRssService service = new HypothesesRssService(url);
		
		try {
			List<RssItem> items = service.getItems(12);
			items.forEach(System.out::println);
			
		} catch (RssException e) {
			System.out.println(e);
		}
	}

}
