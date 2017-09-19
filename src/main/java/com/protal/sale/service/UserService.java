package com.protal.sale.service;

import com.protal.sale.bean.T_MALL_USER_ACCOUNT;

public interface UserService {

	T_MALL_USER_ACCOUNT get_login_user(T_MALL_USER_ACCOUNT login_user);

	int save_sign_in_acct(T_MALL_USER_ACCOUNT acct);

	T_MALL_USER_ACCOUNT get_userService_login_user(T_MALL_USER_ACCOUNT user);

}
