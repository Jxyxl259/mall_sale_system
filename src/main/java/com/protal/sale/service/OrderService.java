package com.protal.sale.service;

import java.util.List;

import com.protal.sale.bean.Object_T_Mall_Order;
import com.protal.sale.exception.StockDeficientException;

public interface OrderService {

	List<Integer> save_order_into_db(String shhr, int user_address_id, String user_address_info,
			String lxfsh,List<Object_T_Mall_Order> order_list);

	void check_stock(List<Object_T_Mall_Order> order_list) throws StockDeficientException;

}
