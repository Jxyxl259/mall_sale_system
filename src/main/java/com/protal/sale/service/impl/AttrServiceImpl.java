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
import com.protal.sale.mapper.AttrMapper;
import com.protal.sale.service.AttrService;



@Service
public class AttrServiceImpl implements AttrService {
	/**
	* Logger for this class
	*/
	private static final Logger logger = Logger.getLogger(AttrServiceImpl.class);

	@Autowired
	private AttrMapper attrMapper;
	
	@Override
	public List<Object_T_Mall_Attr> getProductAttrInfo(int flbh2) {
		return attrMapper.selectProductAttrInfo(flbh2);
	}



}
