package com.protal.sale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.protal.sale.bean.Object_T_Mall_Attr;
import com.protal.sale.bean.T_Mall_Value;

public interface AttrMapper {

	List<Object_T_Mall_Attr> selectProductAttrInfo(int flbh2);
 
/*	int insert_new_attr_into_t_mall_attr(
			@Param("attr_name_list")List<String> attr_name_list, 
			@Param("class_2_id")int flbh2);*/

	int insert_new_attr_into_t_mall_attr(Object_T_Mall_Attr otma);

	int insert_into_t_mall_value(@Param("attr_info_list")List<T_Mall_Value> attr_info_list);
	

	
}
