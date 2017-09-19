package com.protal.sale.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.T_Mall_Shoppingcar;
import com.protal.sale.mapper.ShoppingCarMapper;
import com.protal.sale.service.ShoppingCarSrevice;

@Service
public class ShoppingCarSreviceImpl implements ShoppingCarSrevice {

	@Autowired
	private ShoppingCarMapper shoppingCartMapper;
	
	
	@Override
	public List<T_Mall_Shoppingcar> get_T_Mall_Shoppingcar_List_By_login_user_id(int login_user_id) {
		return shoppingCartMapper.select_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
	}


	@Override
	public int add_shoppingcar_to_db(T_Mall_Shoppingcar car) {
		return shoppingCartMapper.add_shoppingcar_to_db(car);
	}


	@Override
	public int modify_shoppingcar_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar) {
		return shoppingCartMapper.update_shoppingcar_in_db(t_Mall_Shoppingcar);
	}


	@Override
	public int modify_shoppingcar_chose_status_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar) {
		return shoppingCartMapper.update_shoppingcar_chose_status_in_db(t_Mall_Shoppingcar);
	}


	@Override
	public int modify_shoppingcar_item_num_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar) {
		return shoppingCartMapper.update_shoppingcar_item_num_in_db(t_Mall_Shoppingcar);
	}


	@Override
	public void remove_shoppingcar_item_in_db(T_Mall_Shoppingcar t_Mall_Shoppingcar) {
		shoppingCartMapper.delete_shoppingcar_item_in_db(t_Mall_Shoppingcar);
	}


	@Override
	public BigDecimal get_shopping_car_total_price() {
		BigDecimal total_money = new BigDecimal(0);
		 if(shoppingCartMapper.get_shopping_car_total_price() == null) {
			 return total_money;
		 }else {
			 return new BigDecimal(shoppingCartMapper.get_shopping_car_total_price());
		 }
	}

}
