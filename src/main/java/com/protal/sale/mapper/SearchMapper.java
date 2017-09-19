package com.protal.sale.mapper;

import java.util.List;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;

public interface SearchMapper {

	List<OBJECT_T_MALL_SKU> select_product_by_class_2_id(int flbh2);
}
