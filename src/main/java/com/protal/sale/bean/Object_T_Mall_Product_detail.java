package com.protal.sale.bean;

import java.util.List;

public class Object_T_Mall_Product_detail extends T_Mall_Sku{

	private T_Mall_Product spu;
	
	private T_MALL_TRADE_MARK tm;

	private List<T_Mall_Product_Image> images;
	
	//由于要在页面展示商品的属性（属性名：属性名名称/属性值名称）
	//如果要在此处直接封装一个T_Mall_Sku_Attr_Value的集合，
	//则会导致Mapper.xml文件中查询SQL对应的resultMap过于复杂
	//考虑到性能问题，再封装一个类专门用来保存商品属性信息
	private List<Object_T_Mall_Product_Detail_Attr_Value> sku_av_list;
	public T_Mall_Product getSpu() {
		return spu;
	}
	public void setSpu(T_Mall_Product spu) {
		this.spu = spu;
	}
	public T_MALL_TRADE_MARK getTm() {
		return tm;
	}
	public void setTm(T_MALL_TRADE_MARK tm) {
		this.tm = tm;
	}
	public List<T_Mall_Product_Image> getImages() {
		return images;
	}
	public void setImages(List<T_Mall_Product_Image> images) {
		this.images = images;
	}
	public List<Object_T_Mall_Product_Detail_Attr_Value> getSku_av_list() {
		return sku_av_list;
	}
	public void setSku_av_list(List<Object_T_Mall_Product_Detail_Attr_Value> sku_av_list) {
		this.sku_av_list = sku_av_list;
	}
}
