package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.protal.sale.bean.ConstantValue;
import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.bean.T_Mall_Shoppingcar;
import com.protal.sale.service.ShoppingCarSrevice;
import com.protal.sale.utils.MyJsonUtil;

@Controller
public class ShoppingCarController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(ShoppingCarController.class);

	@Autowired
	private ShoppingCarSrevice ShoppingCarSreviceImpl;
	
	
	//minicar请求显示购物车数据
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("mini_car_data_apply")
	public String mini_car_data_apply(
				HttpSession session,
				@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cookie_cars,
				Map map) {
		List<T_Mall_Shoppingcar> session_car_list = new ArrayList<T_Mall_Shoppingcar>();
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
			
			if(login_user == null) {//取cookie中的购物车数据进行转码
				String cookie_cars_json_list = "";
				if(StringUtils.isNotBlank(cookie_cars)) {
					try {
						cookie_cars_json_list = URLDecoder.decode(cookie_cars,"utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if(StringUtils.isNotBlank(cookie_cars_json_list) ) {
					session_car_list = MyJsonUtil.json_to_list(cookie_cars_json_list, T_Mall_Shoppingcar.class);
				}
			}else {
				session_car_list = (ArrayList<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
			}
			
		map.put("session_car_list", session_car_list);
		
		return "inner_page/mini_car_inner_data_page";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("front_add_shopping_car")
	//获取cookie对象//获取session对象//获取添加的购物车
	public String front_add_shopping_cart(
			HttpServletRequest req,
			HttpServletResponse res,
			@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cookie_cars,
			HttpSession session,		
			T_Mall_Shoppingcar car	,
			Map map) {
		
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
		Cookie cookie = new Cookie("cookie_front_shoppingcar_list", "");
		List<T_Mall_Shoppingcar> session_car_list = new ArrayList<>();
		if(login_user != null) {//用户已登录(操作数据库和更新session)
			//用户登陆后，应该将登录前浏览器使用者添加到cookie中的购物车数据与数据库中的数据进行一次同步，
			//也即用户登录前保存在cookie中的购物车数据，在用户登录后应该依然存在，而不是消失
			//用户登陆后操作的是数据库，而每次操作数据库之后，都要对session进行同步，
			//所以可以确保session中的数据是与DB同步的,所以此处是可以从session中取出购物车集合的
			logger.info("添加购物车时用户已登录已登录已登录已登录已登录");
			int login_user_id = login_user.getId();
			//上面解释这么多，就是说，下面一行代码中的db_shopping_car_list即使是从session中获取，也是可以的
			List<T_Mall_Shoppingcar> db_shopping_car_list = ShoppingCarSreviceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
			//查看数据库中有没有购物车数据
			//可以设置双重保险，即从session中做一次查询，再从数据库中做一次查询，如果两次查询数据都为空，
			//则确定用户确实是第一次添加购物车信息
			if(db_shopping_car_list == null || db_shopping_car_list.size() == 0) {
				logger.info("用户已登录，但是数据库中没有任何跟用户相关的购物车数据");
				//数据库中也没有该用户的任何购物车信息，直接添加
				car.setYh_id(login_user_id);
				int affectRows = ShoppingCarSreviceImpl.add_shoppingcar_to_db(car);
				logger.info("这是用户第一次添加购物车");
				//更新数据库之后，同步session
				session_car_list.add(car);
				logger.info("将该用户的第一条购物车数据添加到数据库返回受影响行数:"+affectRows+"\t插入的对象主键自动生成:"+car.getId());
				session.setAttribute("session_car_list", session_car_list);
				logger.info("同步session");
			}else {//数据库中有相关购物车记录
				logger.info("用户已登录，且数据库中数据");
				boolean flag = false;
				for(int i = 0; i<db_shopping_car_list.size();i++) {
					if(db_shopping_car_list.get(i).getSku_id() == car.getSku_id()) {
						flag = true;
						logger.info("该商品之前已经添加过用户的购物车");
						db_shopping_car_list.get(i).setTjshl(db_shopping_car_list.get(i).getTjshl().add(car.getTjshl()));
						db_shopping_car_list.get(i).setHj(db_shopping_car_list.get(i).getHj().add(car.getHj()));
						//更新数据库中，所选中商品的信息
						int affectRows = ShoppingCarSreviceImpl.modify_shoppingcar_in_db(db_shopping_car_list.get(i));
						logger.info("更新数据库中该商品的添加数量跟金额合计信息，返回受影响的行数："+affectRows);
					}
					session_car_list = db_shopping_car_list;
					session.setAttribute("session_car_list", session_car_list);
					logger.info("同步session");
				}
				if(!flag) {//说明数据库中的购物车还没有这类商品，通过用户ID添加该商品到数据库
					logger.info("用户已登录，数据库中的购物车还没有这类商品，通过用户ID添加该商品到数据库");
					car.setYh_id(login_user_id);
					int affectRows = ShoppingCarSreviceImpl.add_shoppingcar_to_db(car);
					logger.info("用户已登录，新增不同商品到数据库，执行插入返回受影响的行数："+affectRows);
					db_shopping_car_list = ShoppingCarSreviceImpl.get_T_Mall_Shoppingcar_List_By_login_user_id(login_user_id);
					logger.info("重新查询数据库，确保session同步");
					session_car_list = db_shopping_car_list;
					session.setAttribute("session_car_list", session_car_list);
				}
			}
		}else {//用户未登录（操作cookie）
			//老式方法获得浏览器端cookie的副本集合Cookie[] cookies = req.getCookies();
			//获取浏览器的Cookie集合的副本,新方法是使用注解获得，然后进行解码
			if(StringUtils.isNoneBlank(cookie_cars)) {//用户没有登录，但是cookie中有购物车数据，也即浏览者（未登录）不是第一次添加购物车
				//解码获取到的cookie_car集合
				String decode_cookie_front_cars = "";
				try {
					decode_cookie_front_cars = URLDecoder.decode(cookie_cars,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//判断原cookie_car中是否有该商品，如果有该商品，则更新数量，如果没有则添加
				List<T_Mall_Shoppingcar> cookie_front_cars_list = MyJsonUtil.json_to_list(decode_cookie_front_cars, T_Mall_Shoppingcar.class);
				boolean flag = false;//设置循环标记，如果该商品从未添加过cookie中的购物车则布尔值不改变
				for(int i = 0;i<cookie_front_cars_list.size();i++) {
					//原cookie_car中有该商品（之前已经添加过一次购物车）
					//更新数量和金额合计
					if(cookie_front_cars_list.get(i).getSku_id() == car.getSku_id()) {
						flag = true;
						cookie_front_cars_list.get(i).setTjshl(cookie_front_cars_list.get(i).getTjshl().add(car.getTjshl()));
						cookie_front_cars_list.get(i).setHj(cookie_front_cars_list.get(i).getHj().add(car.getHj()));
					}
				}
				if(!flag) {
					cookie_front_cars_list.add(car);
				}
				//for循环结束，将修改的cookie_car集合转json,重新编码，覆盖浏览器原有cookie_car集合
				String updated_cars_json_list = MyJsonUtil.list_to_json(cookie_front_cars_list);
				String updated_cookie_cars_list = "";
				try {
					updated_cookie_cars_list = URLEncoder.encode(updated_cars_json_list,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cookie.setValue(updated_cookie_cars_list);
				
				
			}else {//用户未登录，cookie中也没有任何关于购物车的信息，
				   //则直接创建java集合，添加一个购物车信息,
				   //转json,url编码，创建新的cookie传送给浏览器
				ArrayList<T_Mall_Shoppingcar> new_cookie_car_list = new ArrayList<>();
				new_cookie_car_list.add(car);
				String  new_cookie_car_list_json = MyJsonUtil.list_to_json(new_cookie_car_list);
				String new_cookie_car_list_created = "";
				try {
					new_cookie_car_list_created = URLEncoder.encode(new_cookie_car_list_json,"utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
				cookie.setValue(new_cookie_car_list_created);
			}
		}
		map.put("session_car_list", session_car_list);
		cookie.setMaxAge(60 * 60 * 24 * 7);
		res.addCookie(cookie);
		return "redirect:/front_my_shopping_cart.do";
	}
	
	
	@RequestMapping("front_add_shopping_cart_success")
	public String front_add_shopping_cart() {
		return "front_shopping_cart";
	}
	
	
	//跳转到购物车页面
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("front_my_shopping_cart")
	public String front_my_shopping_cart(
				HttpSession session, 
				HttpServletRequest req,
				@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cooki_cars,
				Map map) {
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
		BigDecimal cart_total_price = new BigDecimal(0.0);
		if(login_user == null) {
			List<T_Mall_Shoppingcar> cookie_cars_list = MyJsonUtil.json_to_list(cooki_cars, T_Mall_Shoppingcar.class);
			//当在商品详情页面点击添加购物车按钮之后，跳转front_shopping_cart页面之前，求出购物车中总价格，在购物车页面上显示
			for(int i = 0;i<cookie_cars_list.size();i++) {
				if("1".equals(cookie_cars_list.get(i).getShfxz() )) {
					cart_total_price = cart_total_price.add(cookie_cars_list.get(i).getHj());
				}
			}
			map.put("front_shopping_cars", cookie_cars_list);
		}else {
			List<T_Mall_Shoppingcar> session_car_list  = (List<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
			//当在商品详情页面点击添加购物车按钮之后，跳转front_shopping_cart页面之前，求出购物车中总价格，在购物车页面上显示
			for(int i = 0;i<session_car_list.size();i++) {
				if("1".equals(session_car_list.get(i).getShfxz() )) {
					cart_total_price = cart_total_price.add(session_car_list.get(i).getHj());
				}
			}
			map.put("front_shopping_cars", session_car_list);
		}
		
		map.put("cart_total_price", cart_total_price);
		return "front_shopping_cart";
	}
	
	
	
	//更新cookie或者session中的购物车中购物项的选中状态
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("front_change_car_item_checked")
	public String front_change_car_item_number(
			HttpSession session,
			HttpServletResponse res,
			@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cooki_cars,
			Map map,
			int sku_id,
			String shfxz) {
		//首先判断用户是否登录，若登录，操作session，更新db，若没有登录，操作cookie
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
		List<T_Mall_Shoppingcar> front_shopping_cars = new ArrayList<>();
		BigDecimal cart_total_price = new BigDecimal(0.0);
		if(login_user == null) {
			List<T_Mall_Shoppingcar> cookie_cars_list = MyJsonUtil.json_to_list(cooki_cars, T_Mall_Shoppingcar.class);
			//当在商品详情页面点击添加购物车按钮之后，跳转front_shopping_cart页面之前，求出购物车中总价格，在购物车页面上显示
			for(int i = 0; i<cookie_cars_list.size(); i++) {
				if(cookie_cars_list.get(i).getSku_id() == sku_id) {
					cookie_cars_list.get(i).setShfxz(shfxz);
				}
				if("1".equals(cookie_cars_list.get(i).getShfxz())) {
					cart_total_price = cart_total_price.add(cookie_cars_list.get(i).getHj());
				}
			}

			front_shopping_cars = cookie_cars_list;
			//重新转为json数组进行编码
			String cookie_cars_json_list = MyJsonUtil.list_to_json(cookie_cars_list);
			try {
				cookie_cars_json_list = URLEncoder.encode(cookie_cars_json_list,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//重置cookie
			Cookie cookie = new Cookie("cookie_front_shoppingcar_list", cookie_cars_json_list);
			res.addCookie(cookie);

		}else {//操作DB，同步session  session.setAttribute("session_car_list", session_car_list);
			List<T_Mall_Shoppingcar> session_cars_list = (ArrayList<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
			for(int i = 0; i<session_cars_list.size(); i++) {
				if(session_cars_list.get(i).getSku_id() == sku_id) {
					session_cars_list.get(i).setShfxz(shfxz);
					int affectRows = ShoppingCarSreviceImpl.modify_shoppingcar_chose_status_in_db(session_cars_list.get(i));
					logger.info("更新DB中购物车中购物项的选中状态返回受影响的行数：---->"+affectRows);
				}
				if("1".equals(session_cars_list.get(i).getShfxz())) {
					cart_total_price = cart_total_price.add(session_cars_list.get(i).getHj());
				}
			}
			front_shopping_cars = session_cars_list;
		}
		
		map.put("cart_total_price", cart_total_price);
		map.put("front_shopping_cars", front_shopping_cars);
		return "inner_page/front_shopping_car_inner_data_page";
	}
	
	//购物车操作，增加或减少购物项数量（通过加减号操作）
	@RequestMapping("front_car_item_num_change")
	public String front_modify_car_item_num_remove(
			@RequestParam(value="yh_id",required=false)int yh_id,
			@RequestParam(value="car_id",required=false)int car_id,
			int num,
			int sku_id,
			HttpSession session,
			HttpServletResponse res,
			@CookieValue(value="cookie_front_shoppingcar_list",required=false) String cooki_cars,
			Map map
			) {
		//首先判断用户是否登录，若登录，操作session，更新db，若没有登录，操作cookie
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
		List<T_Mall_Shoppingcar> front_shopping_cars = new ArrayList<>();
		BigDecimal cart_total_price = new BigDecimal(0.0);
		if(login_user == null) {
			List<T_Mall_Shoppingcar> cookie_cars_list = MyJsonUtil.json_to_list(cooki_cars, T_Mall_Shoppingcar.class);
			for(int i = 0; i<cookie_cars_list.size(); i++) {
				if(cookie_cars_list.get(i).getSku_id() == sku_id) {
					
						if(num == -1) {
							cookie_cars_list.get(i).setTjshl(cookie_cars_list.get(i).getTjshl().subtract(new BigDecimal("1")));
							cookie_cars_list.get(i).setHj(cookie_cars_list.get(i).getHj().subtract(cookie_cars_list.get(i).getSku_jg()));
						}else {
							cookie_cars_list.get(i).setTjshl(cookie_cars_list.get(i).getTjshl().add(new BigDecimal("1")));
							cookie_cars_list.get(i).setHj(cookie_cars_list.get(i).getHj().add(cookie_cars_list.get(i).getSku_jg()));
						}
					
				}
				if("1".equals(cookie_cars_list.get(i).getShfxz())) {
					cart_total_price = cart_total_price.add(cookie_cars_list.get(i).getHj());
				}
			}
			front_shopping_cars = cookie_cars_list;
			//重新转为json数组进行编码
			String cookie_cars_json_list = MyJsonUtil.list_to_json(cookie_cars_list);
			try {
				cookie_cars_json_list = URLEncoder.encode(cookie_cars_json_list,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//重置cookie
			Cookie cookie = new Cookie("cookie_front_shoppingcar_list", cookie_cars_json_list);
			res.addCookie(cookie);
			
		}else {//操作DB，同步session  session.setAttribute("session_car_list", session_car_list);
			List<T_Mall_Shoppingcar> session_cars_list = (ArrayList<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
			Iterator<T_Mall_Shoppingcar> session_cars_list_iterator = session_cars_list.iterator();
			while(session_cars_list_iterator.hasNext()) {
				T_Mall_Shoppingcar car = session_cars_list_iterator.next();
				if(car.getId() == car_id) {
					if(num == -1) {
						if(car.getTjshl().intValue() == 1) {
							session_cars_list_iterator.remove();
							ShoppingCarSreviceImpl.remove_shoppingcar_item_in_db(car);
						}else {
							car.setTjshl(car.getTjshl().subtract(new BigDecimal("1")));
							car.setHj(car.getHj().subtract(car.getSku_jg()));
							int affectRows = ShoppingCarSreviceImpl.modify_shoppingcar_item_num_in_db(car);
							logger.info("更新DB中购物车中购物项的添加数量跟合计返回受影响的行数：---->"+affectRows);
						}
					}else {
						car.setTjshl(car.getTjshl().add(new BigDecimal("1")));
						car.setHj(car.getHj().add(car.getSku_jg()));
						int affectRows = ShoppingCarSreviceImpl.modify_shoppingcar_item_num_in_db(car);
						logger.info("更新DB中购物车中购物项的添加数量跟合计回受影响的行数：---->"+affectRows);
					}
				}
					
			}
	
			cart_total_price = ShoppingCarSreviceImpl.get_shopping_car_total_price();
		
			front_shopping_cars = session_cars_list;
		}
		map.put("cart_total_price", cart_total_price);
		map.put("front_shopping_cars", front_shopping_cars);
		return "inner_page/front_shopping_car_inner_data_page";
	}
}
