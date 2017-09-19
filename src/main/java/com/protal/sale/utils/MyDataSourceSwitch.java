package com.protal.sale.utils;


//多数据源切换，将Key绑定到当前线程中一个工具类
public class MyDataSourceSwitch {

	private static ThreadLocal<String> key = new ThreadLocal<String>();
	
	public static String getKey () {
		return key.get();
	}
	
	public static void setKey(String key_in) {
		key.set(key_in);
	}
	
	public static void clearKey() {
		key.remove();
	}
	
}
