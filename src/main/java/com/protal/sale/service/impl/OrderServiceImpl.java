package com.protal.sale.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.Object_T_Mall_Order;
import com.protal.sale.bean.T_Mall_Flow;
import com.protal.sale.bean.T_Mall_Order_Info;
import com.protal.sale.exception.StockDeficientException;
import com.protal.sale.mapper.OrderMapper;
import com.protal.sale.service.OrderService;
import com.protal.sale.utils.MyDateUtil;


@Service
public class OrderServiceImpl implements OrderService{
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Autowired
	public OrderMapper orderMapper;
	
	@Override
	public List<Integer> save_order_into_db(
			String shhr, 
			int user_address_id, 
			String user_address_info,
			String lxfsh,
			List<Object_T_Mall_Order> order_list) {
		//List<Integer> dd_id_list = new ArrayList<Integer>();
		//为了在Controller里面将session中的购物项清除（已经垢面了，Session中的生成订单购物项应该消失）
		List<Integer> list_cart_id = new ArrayList<Integer>();
		int affectRows = 0;
		//循环插入订单信息
		for(int i = 0;i<order_list.size() ;i++) {
			order_list.get(i).setShhr(shhr);
			order_list.get(i).setDzh_id(user_address_id);
			order_list.get(i).setDzh_mch(user_address_info);
			order_list.get(i).setJdh(1);//游离态时已经设置过了进度号
			int affectRow = orderMapper.insert_order_into_db(order_list.get(i));
			affectRows += affectRow;
			
			//循环插入订单信息项
			for(int j = 0;j<order_list.get(i).getOrder_info_list().size();j++) {
				//将order表返回的主键封装到order_info对象中
				order_list.get(i).getOrder_info_list().get(j).setDd_id(order_list.get(i).getId());
				list_cart_id.add(order_list.get(i).getOrder_info_list().get(j).getGwch_id());
			}
			orderMapper.insert_order_infos_into_db(order_list.get(i).getOrder_info_list());
			//一个order对应一个物流信息
			//所以有多少条订单就需要更新多少条物流信息
			//由于物流信息不只一次性更新，这里只是提交订单后的更新，所更新的内容如下
			//包含配送方式（韵达/申通/顺丰？），目的地（用户地址），订单id
			T_Mall_Flow flow = new T_Mall_Flow();
			String psfsh = "顺丰速递";
			flow.setPsfsh(psfsh);
			flow.setYh_id(order_list.get(i).getYh_id());
			flow.setDd_id(order_list.get(i).getId());
			flow.setMqdd("尚未出库");
			flow.setMdd(user_address_info);
			//插入物流信息到数据库
			orderMapper.insert_flow_into_db(flow);			
			
//			String ywy = "jiangBUG";
//			flow.setPsfsh(psfsh);
//			flow.setYh_id(order_list.get(i).getYh_id());
//			flow.setPsshj(MyDateUtil.getFlowDate(1));// 计算配送时间
//			//flow.setMdd(user_address_info);//下一个目的地
//			flow.setLxfsh(lxfsh);
//			flow.setYwy(ywy);
//			flow.setDd_id(order_list.get(i).getId());
		}
		logger.info("总共几条订单，插入后返回受影响的行数："+affectRows);
		//删除购物车信息
		orderMapper.delete_shopping_cars(list_cart_id);
		
		return list_cart_id;
	}

	@Override
	public void check_stock(List<Object_T_Mall_Order> order_list) throws StockDeficientException {

		ArrayList<Object_T_Mall_Order> orders = (ArrayList<Object_T_Mall_Order>)order_list;
		Iterator<Object_T_Mall_Order> iterator = orders.iterator();
		while(iterator.hasNext()) {
			Object_T_Mall_Order order = iterator.next();
			//更新订单项信息
			order.setYjsdshj(MyDateUtil.getFlowDate(3));
			order.setJdh(2);
			orderMapper.update_t_mall_order(order);
			//在没有配置事务之前，程序执行到第98行数据库的数据发生变化
			//在配置事务之后，程序执行到第98行，数据库中的数据是不会发生变化的，
			//因为service层的方法有事务，要么一起提交要么一起混滚
			
			ArrayList<T_Mall_Order_Info> order_infos = (ArrayList<T_Mall_Order_Info>)order.getOrder_info_list();
			Iterator<T_Mall_Order_Info> infos = order_infos.iterator();
			while(infos.hasNext()) {
				T_Mall_Order_Info order_info = infos.next();
				int stock_remain = orderMapper.select_stock_info(order_info.getSku_id());
				if(stock_remain > order_info.getSku_shl()) {
					//库存充裕，减库存，增加销量
					orderMapper.update_sku_stock_info(order_info.getSku_id(),order_info.getSku_shl());
					//更新物流和订单项
				}else {
					throw new StockDeficientException("商品库存不足，交易失败");
				}
			}
			T_Mall_Flow flow = new T_Mall_Flow();
			flow.setPsshj(MyDateUtil.getFlowDate(1));//更新派送时间
			String deliever_tell = "18805486555";
			flow.setLxfsh(deliever_tell);//更新配送业务员联系方式
			String current_position = "正在打包出库";
			flow.setMqdd(current_position);//更新物流目前地点信息
			String deliever = "老郑";
			flow.setYwy(deliever);
			flow.setDd_id(order.getId());//更新哪个订单的id
			orderMapper.update_t_mall_flow(flow);
			
		}
		
	}

}
