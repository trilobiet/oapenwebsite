package com.trilobiet.oapen.oapenwebsite.repositoryclient;

import java.util.List;

public interface RepositoryItem {
	
	String getTitle();
	List<String> getAuthors();
	List<String> getEditors();
	String getUrl();
	String getDescription();
	String getThumbnailPath();

}
