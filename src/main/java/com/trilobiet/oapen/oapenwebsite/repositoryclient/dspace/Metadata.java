package com.trilobiet.oapen.oapenwebsite.repositoryclient.dspace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metadata {

	private String key, value, qualifier;

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getQualifier() {
		return qualifier;
	}

	@Override
	public String toString() {
		return "Metadata [key=" + key + ", value=" + value + "]";
	}
	
}
