package com.protal.sale.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.protal.sale.bean.Model_T_Mall_Sku_Attr_Value;
import com.protal.sale.bean.OBJECT_T_MALL_SKU;
import com.protal.sale.bean.T_Mall_Sku_Attr_Value;
import com.protal.sale.mapper.SearchBySkuAttrValueMapper;
import com.protal.sale.service.SearchByAttrValueService;
import com.protal.sale.utils.MyJedisPoolUtils;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


@Service
public class SearchByAttrValueServiceImpl implements SearchByAttrValueService{
	
	private static final Logger logger = Logger.getLogger(SearchByAttrValueServiceImpl.class);

	@Autowired
	private SearchBySkuAttrValueMapper searchBySkuAttrValueMapper;
	
	@Override
	public List<OBJECT_T_MALL_SKU> get_products_info_by_attr(int class_2_id,ArrayList<T_Mall_Sku_Attr_Value> arrayList,String order_condition) {
		
/*
	SELECT 
		distinct sku.id as sku_id ,spu.id as spu_id, tm.id as tm_id,sku.*,spu.*,tm.* 
	from 
		t_mall_sku sku,
		t_mall_product spu,
		t_mall_sku_attr_value sku_av,
		t_mall_trade_mark tm
	where 
		sku.shp_id = spu.id 
	and 
		spu.pp_id = tm.Id 
	AND
		sku.id = sku_av.sku_id 
	and 
		spu.flbh2 = 16 
	AND
		sku_id in (
			SELECT 
				sku_0.sku_id 
			from
				(SELECT sku_id FROM t_mall_sku_attr_value where shxm_id = 35 and shxzh_id =41) sku_0,
				(SELECT sku_id FROM t_mall_sku_attr_value where shxm_id = 35 and shxzh_id =41) sku_1
			where 
				sku_0.sku_id = sku_1.sku_id
  		)

*/
		StringBuffer sql = new StringBuffer(" ");
		if(arrayList != null && arrayList.size() != 0) {
			 sql.append(" AND" + 
					"		sku_id in ( " + 
					"			SELECT " + 
					"				sku_0.sku_id " + 
					"			from ");
			
			if(arrayList.size() == 1) {
				
				sql.append(" (SELECT sku_id FROM t_mall_sku_attr_value where shxm_id = "+arrayList.get(0).getShxm_id()+" and shxzh_id ="+arrayList.get(0).getShxzh_id()+") sku_0 ");
				sql.append(" ) ");
				
			} else {
				for(int i = 0;i<arrayList.size();i++) {
					sql.append(" (SELECT sku_id FROM t_mall_sku_attr_value where shxm_id = "+arrayList.get(i).getShxm_id()+" and shxzh_id ="+arrayList.get(i).getShxzh_id()+") sku_"+i);
					if(i != arrayList.size()-1) {
						sql.append(" , ");
					}
				}
				sql.append(" where ");
				
				for(int i = 0;i<arrayList.size()-1;i++) {
					sql.append("sku_"+i+".sku_id = sku_"+(i+1)+".sku_id");
					if(i != arrayList.size()-2) {
						sql.append(" and ");
					}
				}
				sql.append(" ) ");
			}
		}
		System.out.println(sql.toString());
		List<OBJECT_T_MALL_SKU> select_products_by_attr = searchBySkuAttrValueMapper.select_products_by_attr(class_2_id, sql.toString(),order_condition);
		 
		return select_products_by_attr;
	}
}
