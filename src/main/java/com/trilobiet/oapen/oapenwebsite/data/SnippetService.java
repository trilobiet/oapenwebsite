package com.trilobiet.oapen.oapenwebsite.data;

import java.util.Optional;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.datamodel.Snippet;

public interface SnippetService {
	
	Optional<Snippet> getSnippet(String name) throws DaoException;
	
}