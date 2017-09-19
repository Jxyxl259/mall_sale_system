package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;
import com.protal.sale.bean.T_Mall_Sku_Attr_Value;
import com.protal.sale.service.SearchByAttrValueService;
import com.protal.sale.utils.MyJedisPoolUtils;
import com.protal.sale.utils.MyJsonUtil;
import com.protal.sale.utils.MyStringUtils;

import redis.clients.jedis.Jedis;

@Controller
public class SearchByAttrValueController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(SearchByAttrValueController.class);

	
	
	@Autowired
	private SearchByAttrValueService searchByAttrValueService;

	/*
	 * @Autowired private SearchService searchServiceImpl;
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping("/search_products_by_attr_and_attr_value")
	public String search_products_by_attr_and_attr_value(int class_2_id,
			@RequestParam(value = "attr_list[]", required = false) String[] attr_list, String order_condition,
			@SuppressWarnings("rawtypes") Map map) {

		ArrayList<T_Mall_Sku_Attr_Value> query_param_arrayList = new ArrayList<T_Mall_Sku_Attr_Value>();

		// 先从redis中查再从数据库中查，最后赋值前台展示数组
		List<OBJECT_T_MALL_SKU> product_list = new ArrayList<OBJECT_T_MALL_SKU>();
		
		Jedis jedis = MyJedisPoolUtils.getJedis();
		
		Set<String> zrange = new HashSet<String>();
		
		
		if (attr_list != null) {
			
			String[] redis_keys = new String[attr_list.length];
			if(StringUtils.isEmpty(order_condition)) {
				for (int i = 0; i < attr_list.length; i++) {
					String[] split = attr_list[i].split("_");
					int shxm_id = Integer.parseInt(split[0]);
					int shxzh_id = Integer.parseInt(split[1]);
					redis_keys[i] = "class_2_" + class_2_id + "_" + shxm_id + "_" + shxzh_id;
				}
			}
			if(" order by jg ".equals(order_condition)) {
				for (int i = 0; i < attr_list.length; i++) {
					String[] split = attr_list[i].split("_");
					int shxm_id = Integer.parseInt(split[0]);
					int shxzh_id = Integer.parseInt(split[1]);
					redis_keys[i] = "class_2_" + class_2_id + "_" + shxm_id + "_" + shxzh_id;
				}
			}
			if(" order by kc ".equals(order_condition)) {
				for (int i = 0; i < attr_list.length; i++) {
					String[] split = attr_list[i].split("_");
					int shxm_id = Integer.parseInt(split[0]);
					int shxzh_id = Integer.parseInt(split[1]);
					redis_keys[i] = "class_2_" + class_2_id + "_" + shxm_id + "_" + shxzh_id+"_ob_kc";
				}
			}
			if(redis_keys[0] != null ) {
				jedis.zinterstore("result", redis_keys);
	
				zrange = jedis.zrange("result", 0, -1);// url乱码(商品)集合
				
				if (zrange.size() != 0) {
					Iterator<String> iterator = zrange.iterator();
					while (iterator.hasNext()) {
						String next = iterator.next();// 获取到redis zset键对应的值的乱码集合的遍历，每次指向一个乱码长串
	
						String search_by_attr_in_redis_decode = "";
						try {
							search_by_attr_in_redis_decode = URLDecoder.decode(next, "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
	
						product_list.add(
								MyJsonUtil.json_to_list(search_by_attr_in_redis_decode, OBJECT_T_MALL_SKU.class).get(0));
	
					}
				}
			}
		} else {
			
			if (" order by jg ".equals(order_condition)) {
				zrange = jedis.zrange("class_2_" + class_2_id, 0, -1);
			}
			if(" order by jg desc ".equals(order_condition) ) {
				zrange = jedis.zrevrange("class_2_" + class_2_id, 0, -1);
			}
			if(" order by kc ".equals(order_condition)) {
				zrange = jedis.zrange("class_2_" + class_2_id+"_ob_kc", 0, -1);
			}
			if(" order by kc desc ".equals(order_condition)) {
				zrange = jedis.zrevrange("class_2_" + class_2_id+"_ob_kc", 0, -1);
			}
			if(zrange.size() != 0) {
				Iterator<String> iterator = zrange.iterator();
				while (iterator.hasNext()) {

					String next_redis_sku_info = iterator.next();

					String get_next_redis_sku_info_str_decoded = MyStringUtils.get_str_decoded(next_redis_sku_info);

					OBJECT_T_MALL_SKU redis_sku_info = MyJsonUtil.json_to_obj(get_next_redis_sku_info_str_decoded,
							OBJECT_T_MALL_SKU.class);

					product_list.add(redis_sku_info);
				}
			}
		}

		// redis中没有，再从数据库中查
		if (product_list.size() == 0) {
			if (attr_list != null) {
				logger.info("商品裂变页面，redis中没有数据，需要从数据库中查询商品信息检索数据：");
				for (int i = 0; i < attr_list.length; i++) {
					String[] split = attr_list[i].split("_");
					T_Mall_Sku_Attr_Value t_Mall_Sku_Attr_Value = new T_Mall_Sku_Attr_Value();
					int shxm_id = Integer.parseInt(split[0]);
					int shxzh_id = Integer.parseInt(split[1]);
					t_Mall_Sku_Attr_Value.setShxm_id(shxm_id);
					t_Mall_Sku_Attr_Value.setShxzh_id(shxzh_id);

					query_param_arrayList.add(t_Mall_Sku_Attr_Value);
				}

			} /*
				 * else { List<OBJECT_T_MALL_SKU> sku_list =
				 * searchServiceImpl.get_products_info_by_class_2_id(class_2_id);
				 * 
				 * map.put("products_list", sku_list);
				 * 
				 * return "front_search_prodects_by_attr_inner_page"; }
				 */
			
			product_list = searchByAttrValueService.get_products_info_by_attr(class_2_id, query_param_arrayList, order_condition);
			
		}

		map.put("products_list", product_list);

		return "front_search_prodects_by_attr_inner_page";

		/*
		 * Model_T_Mall_Sku_Attr_Value model_T_Mall_Sku_Attr_Value = new
		 * Model_T_Mall_Sku_Attr_Value();
		 * 
		 * model_T_Mall_Sku_Attr_Value.setSku_attr_value_list(arrayList);
		 */

	}
}
