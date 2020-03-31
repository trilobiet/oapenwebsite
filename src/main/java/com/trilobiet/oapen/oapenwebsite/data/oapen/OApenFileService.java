package com.trilobiet.oapen.oapenwebsite.data.oapen;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.dao.FileDao;
import com.trilobiet.graphqlweb.datamodel.File;
import com.trilobiet.graphqlweb.implementations.aexpgraphql.GraphQLFieldValueQuery;
import com.trilobiet.oapen.oapenwebsite.data.FileService;

public class OApenFileService implements FileService {
	
	private final FileDao fileDao;
	
	public OApenFileService(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@Override
	@Cacheable(value="filesCache")
	public Optional<File> get(String id) throws DaoException {

		return fileDao.get(id);
	}

	@Override
	@Cacheable(value="filesCache")
	public List<File> getByName(String name) throws DaoException {
		return fileDao.getByName( name );
	}

	@Override
	@Cacheable(value="filesCache")
	public Optional<File> getFirstWithName(String name) throws DaoException {
		
		List<File> files = fileDao.getByName( name );
		if (!files.isEmpty()) return Optional.of(files.get(0));
		else return Optional.empty(); 
	}
	
	@Override
	@Cacheable(value="filesCache",key="#field+'-'+#value")
	public List<File> getByFieldValue(String field, String value) throws DaoException {
		
		GraphQLFieldValueQuery q = 
			new GraphQLFieldValueQuery.Builder(field,value)
			.build();
		
		return fileDao.find(q);
	}
	
	
}
