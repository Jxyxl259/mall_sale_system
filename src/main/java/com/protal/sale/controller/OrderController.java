package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.protal.sale.bean.ConstantValue;
import com.protal.sale.bean.Model_T_Mall_Order;
import com.protal.sale.bean.Object_T_Mall_Order;
import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.bean.T_Mall_Address;
import com.protal.sale.bean.T_Mall_Order;
import com.protal.sale.bean.T_Mall_Order_Info;
import com.protal.sale.bean.T_Mall_Shoppingcar;
import com.protal.sale.exception.StockDeficientException;
import com.protal.sale.service.AddressService;
import com.protal.sale.service.OrderService;

@Controller
@SessionAttributes("order_list")
public class OrderController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private AddressService addressServiceImp;
	
	@Autowired
	private OrderService orderServiceImp;
	
	@RequestMapping("front_order_page")
	public String goto_front_order_page() {
		//查询出用户的所有订单信息，展示在订单页面
		return "front_order_page";
	}
	
	/**
	 * 付款成功，
	 * 修改库存，增加销量，更新物流信息，订单状态
	 * @param order_list
	 * @return
	 */
	@RequestMapping("front_pay")
	public String go_to_pay_success_page(@ModelAttribute("order_list") List<Object_T_Mall_Order> order_list) {
		
		try {
			orderServiceImp.check_stock(order_list);
		} catch (StockDeficientException e) {
			return "redirect:/deal_failure.do";
		}
		return "redirect:/pay_success_page.do";
	}
	
	@RequestMapping("deal_failure")
	public String go_to_deal_failure_page() {
		return "error_page/deal_failure";
	}
	
	@RequestMapping("pay_success_page")
	public String go_to_order_info_page(@ModelAttribute("order_list") List<Object_T_Mall_Order> order_list) {
		//清空内存中order_list中的订单数据
		order_list.clear();
		return "front_pay_success_page";
	}
	
	
	/**                   传入     List里面有   new list里面有   传入           传入
	 * 1.保存订单信息，生成id(收货人/订单总金额/进度号/用户id/用户地址id/用户地址名称)
	 * 2.保存订单表信息，使用保存订单信息返回的主键
	 * 3.生成物流信息：只包含配送方式（韵达/申通/顺丰？），目的地（用户地址），订单id（插入订单表返回的主键）
	 * 		其他字段在支付完成后生成
	 * 4.删除购物车数据，同步session中的购物车
	 * 5.重定向到支付服务的支付页面
	 * @return
	 */
	@RequestMapping("submit_order")//save_order
	//@ModelAttribute("order_list") List<Object_T_Mall_Order> order_list
	//配合
	//@SessionAttributes("order_list")，
	//将order_list订单集合对象保存于session域中一份（重定向调用完支付服务之后还需要对商品库存进行相应的操作）
	public String submit_order(@ModelAttribute("order_list") List<Object_T_Mall_Order> order_list,
					int user_address_id,
					String user_address_info,
					String shhr,
					String lxfsh,
					Map map,
					HttpSession session
					) {
		List<T_Mall_Shoppingcar> session_car_list =(ArrayList<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
		//1.2.3.保存订单/保存订单信息/生成物流信息
		List<Integer> order_info_sku_id_list_ready_to_remove = orderServiceImp.save_order_into_db(shhr,user_address_id,user_address_info,lxfsh,order_list);
		
		
		//4.删除购物车数据，同步session中的购物车
		Iterator<T_Mall_Shoppingcar> iterator = session_car_list.iterator();
		while(iterator.hasNext()) {
			if(order_info_sku_id_list_ready_to_remove.contains(iterator.next().getId())) {
				iterator.remove();
			}
		}
		
		//5.重定向到支付服务的支付页面 
		return "redirect:/front_pay_for_order.do";
		//跳转支付服务页面
		//企业有能力的话，就在此页面完成与银行金融机构服务接口的对接，
		//或者调用第三方支付平台的接口（继续重定向）
	}		
	
	
	@RequestMapping("front_pay_for_order")
	public String front_pay_for_order(@ModelAttribute("order_list") List<Object_T_Mall_Order> order_list) {
		return "front_pay_for_order_page";
	}	
	
	/**
	 * check_order
	 * 将session中保存的购物车中的购物项（选中的————用户将要付款购买的）分解成为一条条的订单信息项，
	 * 按照商品的库存地址进行分类并创建相应的订单项
	 * 		————（将购物车中相同库存地址的购物项组转换为订单信息项
	 * 			并由多个订单信息项组建成为一个订单，或者说，一条订单包含多条订单信息项二者是一对多的关系）
	 * 用户在购物车页面点击去结算的时候，经过此方法跳转到结算页面（确认购物车页面）
	 * 在用户未点击提交订单之前，订单项为游离状态
	 * 		（在内存中order_list集合中保存了游离状态的所有订单），
	 * 并没有保存数据库，session和DB中的购物车项依然存在，
	 * 此时用户关闭浏览器，之前点选的购物车中的购物项不会消失，也不会在后台生成任何订单
	 * 仅仅是将购物车中的购物项经过此方法的拆单重组操作后以订单的形式作为前台页面展示（展示order_list集合）
	 * 
	 * 
	 * 
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("front_goto_checkout_page")
	public String front_goto_checkout_page(HttpSession session,ModelMap map) {
		T_MALL_USER_ACCOUNT login_user = (T_MALL_USER_ACCOUNT)session.getAttribute(ConstantValue.LOGIN_USER);
		
		List<Object_T_Mall_Order> order_list = new ArrayList<Object_T_Mall_Order>();
		
		//用户未登录
		if(login_user == null) {
			return "temp_login_page";
			//这里应该是临时登录页，注意是临时登录页
			//然后从临时登录页验证用户的方法中中判断cookie中的购物车信息和Session中的购物车信息如果是同一个商品的话，
			//那依cookie中购物车的购物项的数量为准
			//更新DB同步session
			//这就略了，直接登录回到主页面
		}else {
			//获取用户id
			List<T_Mall_Address> user_address_list = addressServiceImp.get_user_address(login_user.getId());
//拆单
			//按照库存地址进行拆单
			//首先要有Model_T_Mall_Order一个类，这个类中包含订单集合的属性List<Object_T_Mall_Order>
			//Object_T_Mall_Order这个类继承了T_Mall_Order，另外又多了一个List<T_Mall_Order_Info> order_info_list集合属性
			Set <String> distinct_sku_address = new HashSet<>();
			
			List<T_Mall_Shoppingcar> session_car_list = (List<T_Mall_Shoppingcar>)session.getAttribute("session_car_list");
			
			//封装订单项  Order
			//由于是按照地址进行拆单的，所以有几个库存地址，就有几个订单
			for(int i = 0;i<session_car_list.size();i++) {
				if("1".equals(session_car_list.get(i).getShfxz())) {
					distinct_sku_address.add(session_car_list.get(i).getKcdz());
				}
			}
			//循环库存地址,创建订单项
			Iterator<String> iterator_kcdz = distinct_sku_address.iterator();
			while(iterator_kcdz.hasNext()) {
				Object_T_Mall_Order object_T_Mall_Order = new Object_T_Mall_Order();
				String next_sku_address = iterator_kcdz.next();
				BigDecimal zje = new BigDecimal("0");
				object_T_Mall_Order.setYh_id(login_user.getId());
				object_T_Mall_Order.setJdh(1);//设置订单的阶段号
											  //（1：订单已提交/2：订单已支付/3：订单运输中/4：单已签收/5：交易完成）
				
				//循环购物车，封装订单对象
				List<T_Mall_Order_Info> order_info_list = new ArrayList<T_Mall_Order_Info>();
				for(int i = 0;i<session_car_list.size();i++) {
					if(next_sku_address.equals(session_car_list.get(i).getKcdz())) {
						if("1".equals(session_car_list.get(i).getShfxz())) {
							T_Mall_Order_Info t_Mall_Order_Info = new T_Mall_Order_Info();
							t_Mall_Order_Info.setSku_id(session_car_list.get(i).getSku_id());
							t_Mall_Order_Info.setShp_tp(session_car_list.get(i).getShp_tp());
							t_Mall_Order_Info.setSku_jg(session_car_list.get(i).getSku_jg());
							t_Mall_Order_Info.setSku_kcdz(session_car_list.get(i).getKcdz());
							t_Mall_Order_Info.setSku_mch(session_car_list.get(i).getSku_mch());
							t_Mall_Order_Info.setSku_shl(session_car_list.get(i).getTjshl().intValue());
							//给Order_Info对象赋值购物车id,方便将订单信息插入后删除购物车
							t_Mall_Order_Info.setGwch_id(session_car_list.get(i).getId());
							zje = zje.add(session_car_list.get(i).getHj());
							order_info_list.add(t_Mall_Order_Info);
						}
					}
				}
				//将订单项与其对应的多条订单信息项进行整合
				object_T_Mall_Order.setOrder_info_list(order_info_list);
				object_T_Mall_Order.setZje(zje);
				//将订单项加入前台页面展示集合
				order_list.add(object_T_Mall_Order);
			}
			
			map.put("order_list", order_list);
			map.put("user_address_list", user_address_list);
			return "front_checkout_page";
		}
	}
	
}
