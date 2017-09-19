package com.protal.sale.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource{

	
	//通过重写的lookupkey决定返回哪个数据源
	@Override
	protected Object determineCurrentLookupKey() {
		return MyDataSourceSwitch.getKey();
	}

		
	
}
