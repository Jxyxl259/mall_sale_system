package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MyDispatcherServlet {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(MyDispatcherServlet.class);

	
	
/*	@RequestMapping("")
	public String methodname() {
		return "";
	}
*/
	
	//服务器端操作Cookie实现用户昵称信息保存操作
	@RequestMapping("/front_login_page")
	public ModelAndView methodname(HttpServletRequest req) {
		
		ModelAndView mav = new ModelAndView("front_login_page");
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping("/front_page_top_show_yh_nch")
	public String front_page_top_show_yh_nch(HttpServletRequest req) {
		
		Cookie[] cookies = req.getCookies();
		
		String yh_nch = "";
		if(ArrayUtils.isNotEmpty(cookies)) {
			
			for(int i = 0; i <cookies.length ;i++) {
				
				if("yh_nch".equals(cookies[i].getName())) {
					
					yh_nch = cookies[i].getValue();
					
					if(StringUtils.isNotBlank(yh_nch)) {
						//对获取到的Cookie中的用户昵称信息，进行URL解码
						try {
							yh_nch = URLDecoder.decode(yh_nch,"utf-8");
							
							logger.info("当用户再次登录时，解码cookie中的用户昵称，得到："+yh_nch);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					
					logger.info("用户再次访问首页时，服务器端操作Cookie所获取的用户昵称"+yh_nch);
					
				}
			}
		}
		//URLencoding一下
		try {
			yh_nch = URLEncoder.encode(yh_nch,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return yh_nch;
	}
	
	@RequestMapping("/front_sale_index")
	public String front_sale_index() {
		return "front_sale_index";
	}
	
}
