package com.protal.sale.mapper;

import java.util.List;

import com.protal.sale.bean.T_Mall_Address;

public interface AddressMapper {

	List<T_Mall_Address> select_user_address_by_user_id(int user_id);
	
}
