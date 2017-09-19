package com.protal.sale.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.Object_T_Mall_Attr;
import com.protal.sale.bean.Object_T_Mall_Product_detail;
import com.protal.sale.mapper.AttrMapper;
import com.protal.sale.mapper.ProductMapper;
import com.protal.sale.service.AttrService;
import com.protal.sale.service.ProductService;



@Service
public class ProductServiceImpl implements ProductService {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductMapper productMapper;

	@Override
	public Object_T_Mall_Product_detail get_product_info_details(int sku_id, int spu_id) {
		
		return productMapper.select_product_info_details(sku_id);
	}
	




}
