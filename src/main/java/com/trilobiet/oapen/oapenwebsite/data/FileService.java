package com.trilobiet.oapen.oapenwebsite.data;

import java.util.List;
import java.util.Optional;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.datamodel.File;

public interface FileService {
	
	Optional<File> get(String id) throws DaoException;
	// Files may share names
	List<File> getByName(String name) throws DaoException;
	Optional<File> getFirstWithName(String name) throws DaoException;
	List<File> getByFieldValue(String field, String value) throws DaoException;
}