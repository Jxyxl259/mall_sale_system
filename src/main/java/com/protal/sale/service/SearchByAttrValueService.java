package com.protal.sale.service;

import java.util.ArrayList;
import java.util.List;

import com.protal.sale.bean.OBJECT_T_MALL_SKU;
import com.protal.sale.bean.T_Mall_Sku_Attr_Value;

public interface SearchByAttrValueService {

	List<OBJECT_T_MALL_SKU> get_products_info_by_attr(int class_2_id, ArrayList<T_Mall_Sku_Attr_Value> arrayList,String order_condition);

}
