package com.protal.sale.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
//low
public class MyJaxWSClientUtil {

	public static <T> T getJaxWSClientProxyBean(String wsdl_url,Class<T> t) {
		
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		
		jaxWsProxyFactoryBean.setAddress(wsdl_url);
		
		jaxWsProxyFactoryBean.setServiceClass(t);
		
		@SuppressWarnings("unchecked")
		T inf = (T)jaxWsProxyFactoryBean.create();
		
		return inf;
		
	}
	
}
