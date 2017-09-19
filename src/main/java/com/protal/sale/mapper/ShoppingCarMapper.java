package com.protal.sale.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.protal.sale.bean.T_Mall_Shoppingcar;

public interface ShoppingCarMapper {

	List<T_Mall_Shoppingcar> select_T_Mall_Shoppingcar_List_By_login_user_id(int login_user_id);

	int add_shoppingcar_to_db(T_Mall_Shoppingcar car);

	int update_shoppingcar_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	int update_shoppingcar_chose_status_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	int update_shoppingcar_item_num_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	void delete_shoppingcar_item_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar);

	Double get_shopping_car_total_price();
	
}
