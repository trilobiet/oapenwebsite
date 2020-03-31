package com.trilobiet.oapen.oapenwebsite.data.oapen;

import java.util.Optional;

import com.trilobiet.graphqlweb.dao.DaoException;
import com.trilobiet.graphqlweb.dao.SnippetDao;
import com.trilobiet.graphqlweb.datamodel.Snippet;
import com.trilobiet.oapen.oapenwebsite.data.SnippetService;
import com.trilobiet.oapen.oapenwebsite.markdown2html.Md2HtmlConverter;

public class OApenSnippetService implements SnippetService {
	
	private final SnippetDao snippetDao;
	private final Md2HtmlConverter md2HtmlConverter;
	
	public OApenSnippetService(SnippetDao snippetDao, Md2HtmlConverter md2HtmlConverter) {
		this.snippetDao = snippetDao;
		this.md2HtmlConverter = md2HtmlConverter;
	}

	@Override
	public Optional<Snippet> getSnippet(String name) throws DaoException {

		return snippetDao.getByName(name);
	}

	public Optional<Snippet> getMarkdownSnippet(String name) throws Exception {
		
		Optional<Snippet> osnip = snippetDao.getByName(name);
		if (osnip.isPresent()) md2HtmlConverter.convert(osnip.get());

		return osnip;
	}
}
