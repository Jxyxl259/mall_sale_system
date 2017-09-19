package com.protal.sale.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.protal.sale.bean.Object_T_Mall_Product_detail;
import com.protal.sale.service.ProductService;

@Controller
public class ProductDetailController {

	@Autowired
	private ProductService productServiceImpl;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("front_show_product_detail")
	public String front_show_product_detail(int sku_id, int spu_id,@SuppressWarnings("rawtypes") Map map) {
		
		Object_T_Mall_Product_detail product_detail =  productServiceImpl.get_product_info_details(sku_id,spu_id);
		
		map.put("sku_id", sku_id);
		map.put("product_detail", product_detail);
		
		return "front_product_detail_page";
	}
	
}
