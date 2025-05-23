package com.trilobiet.oapen.oapenwebsite.repositoryclient.dspace;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryException;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryItem;
import com.trilobiet.oapen.oapenwebsite.repositoryclient.RepositoryService;

public class DSpaceRepositoryService implements RepositoryService {
	
	private final String baseUrl;
	private final String featuredCollectionId;
	private final int daysBackNewest;
	
	protected final Logger log = LogManager.getLogger(this.getClass());
	
	public DSpaceRepositoryService(String baseUrl, Map<String,String> config) {
		this.baseUrl = baseUrl;
		this.featuredCollectionId = config.get("featuredCollectionId");
		this.daysBackNewest = Integer.parseInt(config.get("daysBackNewest"));
	}
	
	@Override
	@Cacheable(value="dspaceCache",key="#root.methodName") // key="'somefancyname'" or any SpEL expression
	public List<RepositoryItem> getFeaturedItems(int count) throws RepositoryException {
		
		String url = featuredItemsUrl(count);
		return getItems(url);
	}

	@Override
	@Cacheable(value="dspaceCache",key="#funder")
	public List<RepositoryItem> getFunderItems(String funder, int count) throws RepositoryException {
		
		String url = funderItemsUrl(funder, count);
		return getItems(url);
	}
	
	@Override
	@Cacheable(value="dspaceCache",key="#root.methodName")
	public List<RepositoryItem> getNewestItems(int count) throws RepositoryException {
		
		String url = newestItemsUrl(count);
		return getItems(url);
	}
	
	private List<RepositoryItem> getItems(String url) throws RepositoryException {
		
		ObjectMapper mapper = new ObjectMapper();
		DSpaceItem[] items;
		
		// TODO set a timeout by using an InputStream, then pass that to the mapper
		
		try {
			items = mapper.readValue(new URL(url), DSpaceItem[].class);
		} catch (IOException e) {
			throw new RepositoryException(e);
		}
		return Arrays.asList(items);
		
	}
	
	private String featuredItemsUrl(int count) {
		
		StringBuilder sb = new StringBuilder(baseUrl);
		
		sb.append("/rest/collections/")
		  .append(this.featuredCollectionId)
		  .append("/items")
		  .append("?expand=bitstreams,metadata")
		  .append("&limit=")
		  .append(count);
		
		return sb.toString();
	}

	private String newestItemsUrl(int count) {
		
		StringBuilder sb = new StringBuilder(baseUrl);
		
		sb.append("/rest/search/")
		
		  .append("?query=dc.date.accessioned_dt:")
		  .append(urlEncodeUTF8("[* TO NOW-" + daysBackNewest + "DAY]"))
		  .append("+AND+dc.type:book")
		  .append("&sort=dc.date.accessioned_dt")
		  .append("&limit=")
		  .append(count)
		  .append("&expand=bitstreams,metadata");
		
		return sb.toString();
	}
	
	private String funderItemsUrl(String funder, int count) {
		
		StringBuilder sb = new StringBuilder(baseUrl);
		
		sb.append("/rest/search?expand=bitstreams,metadata")
		  .append("&limit=")
		  .append(count)
		  .append("&query=oapen.collection:")
		  .append(urlEncodeUTF8("\"" + funder + "\""))
		  .append("+AND+dc.type:book");
		
		return sb.toString();
	}
	
	
	private String urlEncodeUTF8(String s) {
		
		try {
			s = URLEncoder.encode(s,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// This will not happen unless UTF-8 is not supported, which is rare
			log.error("Could not encode url! " + e.getMessage());
		}
		
		return s;
	}
	
}
