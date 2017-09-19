package com.protal.sale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;
import com.protal.sale.bean.T_MALL_USER_ACCOUNT;
import com.protal.sale.mapper.SearchMapper;
import com.protal.sale.mapper.UserMapper;
import com.protal.sale.service.SearchService;
import com.protal.sale.service.UserService;

import java.util.List;

import org.apache.log4j.Logger;


@Service
public class SearchServiceImpl implements SearchService{
	
	private static final Logger logger = Logger.getLogger(SearchServiceImpl.class);

	@Autowired
	private SearchMapper searchMapper;
	
	@Override
	public List<OBJECT_T_MALL_SKU> get_products_info_by_class_2_id(int class_2_id) {
		return searchMapper.select_product_by_class_2_id(class_2_id);
	}


	
	
}
