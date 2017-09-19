package com.protal.sale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;

public interface SearchBySkuAttrValueMapper {

	List<OBJECT_T_MALL_SKU> select_products_by_attr(@Param("flbh2")int flbh2, @Param("SQL")String SQL, @Param("order_condition")String order_condition);
	
}
