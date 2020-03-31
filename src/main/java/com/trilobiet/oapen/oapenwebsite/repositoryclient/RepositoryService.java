package com.trilobiet.oapen.oapenwebsite.repositoryclient;

import java.util.List;

public interface RepositoryService {
	
	List<RepositoryItem> getFeaturedItems(int count) throws Exception;
	List<RepositoryItem> getFunderItems(String funder, int count) throws Exception;
	List<RepositoryItem> getNewestItems(int count) throws RepositoryException;
}
