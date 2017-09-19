package com.protal.sale.mapper;

import com.protal.sale.bean.T_MALL_USER_ACCOUNT;

public interface UserMapper {

	T_MALL_USER_ACCOUNT get_login_user(T_MALL_USER_ACCOUNT login_user);

	int insert_sign_in_acct(T_MALL_USER_ACCOUNT acct);

}
