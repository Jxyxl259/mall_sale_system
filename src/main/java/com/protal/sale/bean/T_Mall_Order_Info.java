package com.protal.sale.bean;

import java.math.BigDecimal;

public class T_Mall_Order_Info {
	
	 private int id;				//订单信息ID
	 private int dd_id;				//订单id
	 private int sku_id;			//商品库存id
	 private BigDecimal chjshj;		//创建时间
	 private String sku_mch;		//库存名称
	 private String shp_tp;			//商品图片
	 private BigDecimal sku_jg;		//商品价格
	 private int sku_shl;			//商品销量
	 private String sku_kcdz;		//商品库存地址
	 private int gwch_id;			//购物车id
	 
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDd_id() {
		return dd_id;
	}
	public void setDd_id(int dd_id) {
		this.dd_id = dd_id;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public BigDecimal getChjshj() {
		return chjshj;
	}
	public void setChjshj(BigDecimal chjshj) {
		this.chjshj = chjshj;
	}
	public String getSku_mch() {
		return sku_mch;
	}
	public void setSku_mch(String sku_mch) {
		this.sku_mch = sku_mch;
	}
	public String getShp_tp() {
		return shp_tp;
	}
	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}
	public BigDecimal getSku_jg() {
		return sku_jg;
	}
	public void setSku_jg(BigDecimal sku_jg) {
		this.sku_jg = sku_jg;
	}
	public int getSku_shl() {
		return sku_shl;
	}
	public void setSku_shl(int sku_shl) {
		this.sku_shl = sku_shl;
	}
	public String getSku_kcdz() {
		return sku_kcdz;
	}
	public void setSku_kcdz(String sku_kcdz) {
		this.sku_kcdz = sku_kcdz;
	}
	public int getGwch_id() {
		return gwch_id;
	}
	public void setGwch_id(int gwch_id) {
		this.gwch_id = gwch_id;
	}

	
	 
}
