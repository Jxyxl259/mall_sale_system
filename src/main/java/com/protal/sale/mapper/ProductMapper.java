package com.protal.sale.mapper;

import org.apache.ibatis.annotations.Param;

import com.protal.sale.bean.Object_T_Mall_Product_detail;

public interface ProductMapper {

	Object_T_Mall_Product_detail select_product_info_details(@Param("sku_id")int sku_id);

}
