package com.trilobiet.oapen.oapenwebsite.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.trilobiet.graphqlweb.datamodel.File;

@Component
public class ViewHelpers {

	@Bean(name = "viewhelpers")
	public ViewHelpers thymeleafHelpers() {
	    return new ViewHelpers();
	}	
	
	public static List<File> images(List<File> input) {
		
		List<File> images = new ArrayList<>(input);
		images.removeIf(f -> !isImage(f));
		return images;
	}
	
	public static List<File> attachments(List<File> input) {
		
		List<File> attachments = new ArrayList<>(input);
		attachments.removeIf(f -> isImage(f));
		return attachments;
	}
	
	public static boolean isImage(File file) {
		
		String imgExts = ".png .jpg .jpeg .webp .gif";
		boolean isImage = imgExts.contains(file.getExt());
		return isImage;		
	}
	
	public static String urlEncode(String input) {
		
		String r = input;
		
		try {
			r = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			r = "could_not_encode_input";
		}
		
		return r;
	}
	
	/**
	 * https://stackoverflow.com/questions/31868404/how-to-abbreviate-string-at-the-middle-without-cutting-words
	 * 
	 * @param input
	 * @param middle
	 * @param length
	 * @return Abbreviated String
	 */
	public static String abbreviateMiddle(String input, String middle, int length) {
		
	    if (input != null && input.length() > length) {
	        int half = (length - middle.length()) / 2;

	        Pattern pattern = Pattern.compile(
	                "^(.{" + half + ",}?)" + "\\b.*\\b" + "(.{" + half + ",}?)$");
	        Matcher matcher = pattern.matcher(input);

	        if (matcher.matches()) {
	            return matcher.group(1) + middle + matcher.group(2);
	        }
	    }

	    return input;
	}	
	
	
	
}
