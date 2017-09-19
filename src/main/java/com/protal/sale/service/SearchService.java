package com.protal.sale.service;

import java.util.List;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;

public interface SearchService {

	List<OBJECT_T_MALL_SKU> get_products_info_by_class_2_id(int class_2_id);


}
