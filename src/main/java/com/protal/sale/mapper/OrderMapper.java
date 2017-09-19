package com.protal.sale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.protal.sale.bean.Object_T_Mall_Order;
import com.protal.sale.bean.T_Mall_Flow;
import com.protal.sale.bean.T_Mall_Order_Info;

public interface OrderMapper {

	int insert_order_into_db(Object_T_Mall_Order object_T_Mall_Order);

	void insert_order_infos_into_db(@Param("order_info_list")List<T_Mall_Order_Info> order_info_list);

	void insert_flow_into_db(T_Mall_Flow flow);

	void delete_shopping_cars(@Param("list_cart_id")List<Integer> list_cart_id);

	int select_stock_info(int sku_id);

	void update_sku_stock_info(@Param("sku_id")int sku_id, @Param("sku_shl")int sku_shl);

	void update_t_mall_flow(T_Mall_Flow flow);

	void update_t_mall_order(Object_T_Mall_Order order);

}
