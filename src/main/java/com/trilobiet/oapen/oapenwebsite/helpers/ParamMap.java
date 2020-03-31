package com.trilobiet.oapen.oapenwebsite.helpers;

import java.util.Arrays;
import java.util.HashMap;

public final class ParamMap {
	
	private final HashMap<String,String> map;
	private final String input;
	private String entrySeparator = ";";
	private String assignmentCharacter = "=";
	
	public ParamMap(String input) {
		this.input = input;
		map = initMap();
	}
	
	public ParamMap(String input, String entrySeparator, String assignmentCharacter) {
		this.input = input;
		this.entrySeparator = entrySeparator;
		this.assignmentCharacter = assignmentCharacter;
		map = initMap();
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public String get(String key) {
		return map.get(key);
	}
	
	private HashMap<String,String> initMap() {
		
		HashMap<String,String> hm = new HashMap<String,String>();
		
		if (input != null) {
		
			Arrays.asList( input.split(entrySeparator) )
				.stream()
				.forEach( kv -> {
					String[] pair = kv.split(assignmentCharacter,2);
					if (pair.length == 2) hm.put(pair[0],pair[1]);  
				});
		}
		
		return hm;
	}

	@Override
	public String toString() {
		return "ParamMap [map=" + map + ", entrySeparator=" + entrySeparator + ", assignmentCharacter="
				+ assignmentCharacter + "]";
	}
	
	public static void main(String[] args) {
		
		ParamMap s = new ParamMap("collection=How do you do;nothing;Key=somevalue=some-other-thing;");
		//ParamMap s = new ParamMap(null);
		//ParamMap s = new ParamMap("");
		
		System.out.println(s);
		
		s.map.entrySet().stream().forEach(
			e -> {
				System.out.print(e.getKey() + ": ");
				System.out.println(e.getValue());
			}
		);
	}

	

}
