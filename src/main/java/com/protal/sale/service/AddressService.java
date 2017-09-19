package com.protal.sale.service;

import java.util.List;

import javax.jws.WebService;

import com.protal.sale.bean.T_Mall_Address;

@WebService
public interface AddressService {
	public List<T_Mall_Address> get_user_address(int user_id);
	
	
} 
