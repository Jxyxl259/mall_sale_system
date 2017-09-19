package com.protal.sale.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.lang3.StringUtils;

public class MyStringUtils {

	public static String get_str_decoded(String str) {
		
		String decoded_str = "";
		
		if(StringUtils.isNotBlank(str)) {
			
			try {decoded_str = URLDecoder.decode(str,"UTF-8");} 
			catch (UnsupportedEncodingException e) {e.printStackTrace();}
			
		}
		return decoded_str;
	}
	
}
