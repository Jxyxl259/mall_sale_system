package com.protal.sale.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyJsonUtil {
	
	//使用jsonLib生成一个将java复杂数组转换成json字符串的方法
	//可以使用Gson来生成任何类型的json字符串	
	public static<T> String list_to_json(T t) {
		JSONArray fromObject = JSONArray.fromObject(t);
		
		String json_string = fromObject.toString();
		
//		在将java对象转换成json对象的时候，Gosn（上面的两行代码）或者jsonLib（下面的两行代码）都可以
		
//		Gson g = new Gson();
//		String json_string = g.toJson(t);
		
		return json_string;
	}
	
	public static<T> String obj_to_json (T t) {
		JSONObject fromObject = JSONObject.fromObject(t);
		
		String obj_json_string = fromObject.toString();
		
		String encoded_obj_json_string= "";
		try {
			encoded_obj_json_string = URLEncoder.encode(obj_json_string,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoded_obj_json_string;
	}
	
	public static<T> T json_to_obj (String json_str ,Class<T> clazz) {
		
		JSONObject fromObject = JSONObject.fromObject(json_str);
		
		T t = (T)fromObject.toBean(fromObject, clazz);
		
		return t;
	}
	
	
	//使用jsonLib将json字符串转换为复杂的java集合对象的通用方法
	@SuppressWarnings({ "static-access", "unchecked" })
	public static <T> List<T> json_to_list(String json_list,Class<T> clazz){
		
		if(StringUtils.isNotBlank(json_list)) {
		
			JSONArray fromObject = JSONArray.fromObject(json_list);
			//toCollection应该是JSONArray的一个静态方法如下两种方式都可以
			//List<T> list = (List<T>)JSONArray.toCollection(fromObject, clazz);
			List<T> list = (List<T>)fromObject.toCollection(fromObject, clazz);
			
			return list;
		}else {
			return null;
		}
	} 
	
//	public static void main(String[] args) {
//		ArrayList<Student> stu_List = new ArrayList<Student>();
//
//		for (int i = 0; i < 4; i++) {
//			Student s = new Student();
//			s.setStuId(i);
//			s.setStuGrade(i * 5 + "");
//			s.setStuEmail(i + "@.com");
//			s.setStuName(i + "小宇");
//			stu_List.add(s);
//		}
//		
//		String list_to_json = list_to_json(stu_List);
//		System.out.println(list_to_json);
//		
//		List<Student> json_to_list = json_to_list(list_to_json,Student.class);
//		for (Student student : json_to_list) {
//			System.out.println(student);
//		}
//		
//	}
}
