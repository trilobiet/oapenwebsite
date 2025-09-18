package com.trilobiet.oapen.oapenwebsite.rss.mailchimp;

import java.util.List;

import com.trilobiet.oapen.oapenwebsite.rss.RssException;
import com.trilobiet.oapen.oapenwebsite.rss.RssItem;
import com.trilobiet.oapen.oapenwebsite.rss.RssServiceImp;

public class MailchimpRssService extends RssServiceImp {
	
	/**
	 * Constructor 
	 * @param feedUrl
	 */
	public MailchimpRssService(String feedUrl) {
		super(feedUrl);
	}

	// Test
	public static void main(String[] args) {

		String url = "https://us4.campaign-archive.com/feed?u=314fa411ba5eaaee7244c95e1&id=e31e6f80e1";
		MailchimpRssService service = new MailchimpRssService(url);
		
		try {
			List<RssItem> items = service.getItems(20);
			//items.forEach(System.out::println);
			items.forEach(i -> System.out.println(i.getContents()));
			
			
		} catch (RssException e) {
			System.out.println(e);
		}
	}

}
