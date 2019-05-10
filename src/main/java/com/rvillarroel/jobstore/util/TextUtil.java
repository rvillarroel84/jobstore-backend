package com.rvillarroel.jobstore.util;

public class TextUtil {
	
	public String sanitize(String textToSanetize) {
		return textToSanetize.replaceAll("\\s+", " ");
	}

}
