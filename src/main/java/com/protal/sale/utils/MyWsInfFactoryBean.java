package com.protal.sale.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.FactoryBean;

public class MyWsInfFactoryBean<T> implements FactoryBean<T> {

	private Class<T> t;
	private String url;

	@Override
	public T getObject() throws Exception {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();

		jaxWsProxyFactoryBean.setAddress(this.url);

		jaxWsProxyFactoryBean.setServiceClass(this.t);

		@SuppressWarnings("unchecked")
		T inf = (T) jaxWsProxyFactoryBean.create();
		
		return inf;
	}

	@Override
	public Class<?> getObjectType() {
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

	// getter --- setter
	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
