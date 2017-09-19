package com.protal.sale.service;

import java.math.BigDecimal;
import java.util.List;

import com.protal.sale.bean.T_Mall_Shoppingcar;

public interface ShoppingCarSrevice {

	List<T_Mall_Shoppingcar> get_T_Mall_Shoppingcar_List_By_login_user_id(int login_user_id);

	int add_shoppingcar_to_db(T_Mall_Shoppingcar car);

	int modify_shoppingcar_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	int modify_shoppingcar_chose_status_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	int modify_shoppingcar_item_num_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	void remove_shoppingcar_item_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	BigDecimal get_shopping_car_total_price();
	
}
