package com.protal.sale.bean;

import java.util.List;

public class Object_T_Mall_Order extends T_Mall_Order{

	List<T_Mall_Order_Info> order_info_list ;

	public List<T_Mall_Order_Info> getOrder_info_list() {
		return order_info_list;
	}

	public void setOrder_info_list(List<T_Mall_Order_Info> order_info_list) {
		this.order_info_list = order_info_list;
	}

	
	
}
