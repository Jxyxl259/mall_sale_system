package com.protal.sale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.mapper.UserMapper;
import com.protal.sale.service.UserService;
import com.protal.sale.utils.MyDataSourceSwitch;

import org.apache.log4j.Logger;


@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public T_MALL_USER_ACCOUNT get_login_user(T_MALL_USER_ACCOUNT login_user) {
		
		logger.info("UserServiceImpl表单提交用户信息login_user"+login_user);
		
		return userMapper.get_login_user(login_user);
	}

	@Override
	public int save_sign_in_acct(T_MALL_USER_ACCOUNT acct) {
		return userMapper.insert_sign_in_acct(acct);
	}

	@Override
	public T_MALL_USER_ACCOUNT get_userService_login_user(T_MALL_USER_ACCOUNT user) {
		MyDataSourceSwitch.setKey("2");
		
		T_MALL_USER_ACCOUNT login_user =  userMapper.get_login_user(user);
		
		MyDataSourceSwitch.clearKey();
		
		return login_user;
	}

}
