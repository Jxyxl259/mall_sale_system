package com.protal.sale.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyPropertyUtils {

	public static String getProperty(String prop_file_name,String explicit_property){
		
		Properties properties = new Properties();
		
		InputStream resourceAsStream 
		            = MyPropertyUtils.class
		            	.getClassLoader()
		            	.getResourceAsStream(prop_file_name);
	
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String property = properties.getProperty(explicit_property);
		
		return property;
	}
}
