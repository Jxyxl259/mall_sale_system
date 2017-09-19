package com.protal.sale.controller;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;
import com.protal.sale.bean.Object_T_Mall_Attr;
import com.protal.sale.service.AttrService;
import com.protal.sale.service.SearchService;
import com.protal.sale.service.impl.AttrServiceImpl;
import com.protal.sale.utils.MyJedisPoolUtils;
import com.protal.sale.utils.MyJsonUtil;
import com.protal.sale.utils.MyStringUtils;

import redis.clients.jedis.Jedis;

@Controller
public class SearchController {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(SearchController.class);

	@Autowired
	private SearchService searchServiceImpl;
	@Autowired
	private AttrService attrServiceImpl;
	
/*	@RequestMapping("")
	public String methodname() {
		return "";
	}
*/
	@RequestMapping("front_index_get_products_info_by_class_2_id")
	public String get_products_info_by_class_2_id( 
				int class_2_id,
				@RequestParam(value="flmch2",required=false) String flmch2 ,
				ModelMap map) {
		
		Jedis jedis = MyJedisPoolUtils.getJedis();
		
		Set<String> zrange = jedis.zrange("class_2_"+class_2_id, 0, -1);
		
		
		List<OBJECT_T_MALL_SKU> sku_list = new ArrayList<OBJECT_T_MALL_SKU>();
		
		Iterator<String> iterator = zrange.iterator();
		while(iterator.hasNext()) {
			
			String next_redis_sku_info = iterator.next();
			
			String get_next_redis_sku_info_str_decoded = MyStringUtils.get_str_decoded(next_redis_sku_info);
			
			OBJECT_T_MALL_SKU redis_sku_info = MyJsonUtil.json_to_obj(get_next_redis_sku_info_str_decoded, OBJECT_T_MALL_SKU.class);
			
			sku_list.add(redis_sku_info);
		}
		
		if(sku_list == null || sku_list.size() == 0) {
			//redis中没有，去MySQL中找数据
			sku_list = searchServiceImpl.get_products_info_by_class_2_id(class_2_id);
			//同步Redis
		}
		
		List<Object_T_Mall_Attr> attrList = attrServiceImpl.getProductAttrInfo(class_2_id);
		
		logger.info(sku_list);
		
		map.addAttribute("products_list", sku_list);
		map.addAttribute("attr_list", attrList);
		map.addAttribute("flmch2", flmch2);
		map.addAttribute("class_2_id", class_2_id);

		return "front_search_prodects_by_class_2_id";
		
	}

}
