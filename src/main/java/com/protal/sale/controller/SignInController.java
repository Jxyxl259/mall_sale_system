package com.protal.sale.controller;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.service.UserService;

@Controller
public class SignInController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(SignInController.class);

	
	
	@Autowired
	private UserService userServiceImpl;
	
	
	//跳转用户注册页面
	@RequestMapping("front_sign_in")
	public String front_sign_in() {
		return "front_sign_in_page";
	}
	
	
	//处理用户注册请求
	@RequestMapping("front_sign_in_new_user")
	public String handle_new_user_sign_in_request(T_MALL_USER_ACCOUNT acct) {
		
		int affectRows = userServiceImpl.save_sign_in_acct(acct);
		
		if(affectRows == 1) {
			logger.info("新用户注册成功~");
			return "front_sign_in_page";
		}
		
		return "front_sign_in_page";
	}
	
}
