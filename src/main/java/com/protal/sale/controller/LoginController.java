package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.protal.sale.bean.ConstantValue;
import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.bean.T_Mall_Shoppingcar;
import com.protal.sale.service.ShoppingCarSrevice;
import com.protal.sale.service.UserService;
import com.protal.sale.service.UserVerifyService;
import com.protal.sale.utils.MyJaxWSClientUtil;
import com.protal.sale.utils.MyJsonUtil;
import com.protal.sale.utils.MyPropertyUtils;

@Controller
public class LoginController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private UserService UserServiceImpl;
	
	
	@Autowired
	private UserVerifyService UserVerifyServiceImpl;
	//private UserVerifyService UserVerifyServiceImpl = MyJaxWSClientUtil.getJaxWSClientProxyBean(MyPropertyUtils.getProperty("wsdlURL.properties", "userService_WSDL_url"), UserVerifyService.class);
	
	
	@Autowired
	private ShoppingCarSrevice shoppingCarServiceImpl;
	
	
	@RequestMapping("front_user_login")
	public ModelAndView login(
			    String datasourceswitch,
				HttpServletResponse res ,
				HttpServletRequest req,
				T_MALL_USER_ACCOUNT user,
				@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cookie_cars) {
		
		HttpSession session = req.getSession();
		
		ModelAndView mav = new ModelAndView();
		
		T_MALL_USER_ACCOUNT login_user = new T_MALL_USER_ACCOUNT();
		
		if(datasourceswitch.equals("1")) {
			login_user = UserServiceImpl.get_login_user(user);
		}
		else if(datasourceswitch.equals("2")){
			
			login_user =  UserServiceImpl.get_userService_login_user(user);
		}
		if(login_user !=null) {
			//服务器操作Cookie实现用户昵称保存操作，增加用户体验
			//当用户第一次访问首页的时候，如果购物，或者查看购物车，则必然会登录，
			//当用户登录时，在服务器端向cookie中添加数据，返回给客户端浏览器
			String yh_nch = login_user.getYh_nch();
			
			//因为查询出的登录用户昵称会存在中文
			//对查询出的登录用户昵称进行URL编码，否页面则报
			//ava.lang.IllegalArgumentException: Control character in cookie value or attribute.
			//异常
			try {
				yh_nch = URLEncoder.encode(yh_nch,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			Cookie cookie = new Cookie("yh_nch",yh_nch);
			//设置Cookie失效时间为一周
			cookie.setMaxAge(60 * 60 * 24 * 7);
			
			res.addCookie(cookie);
			
			mav.setViewName("front_sale_index");
			
			session.setAttribute(ConstantValue.LOGIN_USER, login_user);
			
			//合并cookie中的购物车信息，以及 db 中的购物车信息
			merge_cookie_cars_and_db_cars(cookie_cars,session,res,login_user.getId());
			
			return mav;
		}else {
			
			mav.addObject("massage", "用户名或密码错误！~请重新输入");
			
			mav.setViewName("front_login_page");
			
			return mav;
		}
	}
	
	//用户登出front_logout.do
	@RequestMapping("front_logout")
	public String methodname(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/front_sale_index.do";
	}
	
	
	
	//用户登录时，判断Cookie中有没有购物车信息，
	//case1：如果cookie中有
	//  			|___db中有没有？
	//						|______db中有（判断是否是重复的商品，）
	//						|					|_____如果不重复，将cookie中的购物车数据添加到DB
	//						|					|_____如果重复，修改数量，以及金额合计
	//						|
	//						|______db中没有————直接添加(添加之前给shoppingCar对象赋值用户的id)
	//case2:如果cookie中没有
	//  			|___db中有没有？
	//						|______db中有{同步session}
	//						|
	//						|
	//						|
	//						|______db中没有{不用做任何工作}	
	@SuppressWarnings("null")
	private void merge_cookie_cars_and_db_cars(
			String cookie_cars_list,
			HttpSession session,
			HttpServletResponse res,
			int login_user_id) {
			
		List<T_Mall_Shoppingcar> cookie_car_list = MyJsonUtil.json_to_list(cookie_cars_list, T_Mall_Shoppingcar.class);
		
		@SuppressWarnings("unchecked")
		List<T_Mall_Shoppingcar> session_car_list = (List<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
		
		//如果不刻意的跑去后台修改数据，session中的购物车信息应该是跟DB中的购物车信息是同步的。
		//用户未登录之前会有查看迷你购物车的操作，当登录之后再查看迷你购物车，就会将DB与Session做同步
		List<T_Mall_Shoppingcar> db_cars_list = shoppingCarServiceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
		//做一次session同步，之后再查询，就不在调用数据库？
		session_car_list = db_cars_list;
		
		if(cookie_car_list != null && cookie_car_list.size() != 0) {//cookie中有
			
			
			if(session_car_list != null && session_car_list.size() != 0 ) {//db中也有
				
				for(int i = 0 ; i<cookie_car_list.size() ; i++) {
					
					boolean is_new_car = is_new_car(session_car_list,cookie_car_list.get(i));
					
					if(is_new_car) {//如果cookie中的购物车数据是新数据的话，将cookie中的购物车数据添加到DB中
						
						cookie_car_list.get(i).setYh_id(login_user_id);
						
						int affectRows = shoppingCarServiceImpl.add_shoppingcar_to_db(cookie_car_list.get(i));
					
						logger.info("cookie中有，且DB中也有，且为非重复数据，插入新数据到数据库，返回受影响行数"+affectRows);
					}
				}
				
				for(int i = 0 ; i<cookie_car_list.size() ; i++) {
					
					for(int j = 0 ; j<session_car_list.size() ; j++) {
						if(session_car_list.get(j).getSku_id() == cookie_car_list.get(i).getSku_id()) {
							session_car_list.get(j).setTjshl(session_car_list.get(j).getTjshl().add(cookie_car_list.get(i).getTjshl()));
							session_car_list.get(j).setHj(session_car_list.get(j).getHj().add(cookie_car_list.get(i).getHj()));
							int affectRows = shoppingCarServiceImpl.modify_shoppingcar_in_db(session_car_list.get(j));
							
							logger.info("cookie中有，且DB中也有，重复数据，修改原数据库中的购物车信息，返回受影响行数"+affectRows);
						
						}
					}
				}
				//同步session
				session_car_list = (ArrayList<T_Mall_Shoppingcar>) shoppingCarServiceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
				session.setAttribute("session_car_list", session_car_list); 
			}else {//cookie中有DB中没有（用户第一次使用网站的情况）
				
				for(int i = 0 ; i<cookie_car_list.size() ; i++) {
					
					cookie_car_list.get(i).setYh_id(login_user_id);
					
					shoppingCarServiceImpl.add_shoppingcar_to_db(cookie_car_list.get(i));
					
				}
				//同步session
				session_car_list = (ArrayList<T_Mall_Shoppingcar>) shoppingCarServiceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
				session.setAttribute("session_car_list", session_car_list); 
			}
		}else {//cookie中没有
			if(session_car_list == null && session_car_list.size() == 0) {//db中也没有
				
				//什么都不用做
				
			}else{//db中有
				
				//同步session
				session_car_list = (ArrayList<T_Mall_Shoppingcar>) shoppingCarServiceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
				session.setAttribute("session_car_list", session_car_list);
			}
		}
		
		//清理cookie
		Cookie cookie = new Cookie("cookie_front_shoppingcar_list", "");
		res.addCookie(cookie);
	}
	
	//判断是否为重复数据
	/**
	 * 如果是新数据，返回true
	 * 如果是重复数据，返回false
	 * @param cookie_car_list
	 * @param session_car
	 * @return
	 */
	public static boolean is_new_car(
				List<T_Mall_Shoppingcar> session_car_list,
				T_Mall_Shoppingcar cookie_car) {
		boolean flag = true;
		for(int i = 0 ; i<session_car_list.size() ; i++) {
			if(session_car_list.get(i).getSku_id() == cookie_car.getSku_id()) {
				flag = false;
			}
		}
		return flag;
	}
}
