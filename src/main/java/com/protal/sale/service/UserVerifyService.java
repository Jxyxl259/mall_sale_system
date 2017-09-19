package com.protal.sale.service;

import javax.jws.WebService;
import com.protal.sale.bean.T_MALL_USER_ACCOUNT;

@WebService
public interface UserVerifyService {

	public T_MALL_USER_ACCOUNT get_login_user(T_MALL_USER_ACCOUNT user);
	
}
