package com.protal.sale.bean;

import java.math.BigDecimal;

public class T_Mall_Shoppingcar {

	private int  Id;			//购物车id
	private String  sku_mch;	//库存名称
	private BigDecimal  sku_jg;		//库存价格
	private BigDecimal  tjshl;		//添加数量
	private BigDecimal  hj;			//金额合计
	private int  yh_id;			//用户id
	private int  shp_id;		//商品id
	private String  chjshj;		//创建时间	
	private int  sku_id;		//库存id
	private String  shp_tp;		//商品图片
	private String  shfxz; 	//是否选择
	private String  kcdz;		//库存地址
	
	public String getShfxz() {
		return shfxz;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getSku_mch() {
		return sku_mch;
	}
	public void setSku_mch(String sku_mch) {
		this.sku_mch = sku_mch;
	}
	public BigDecimal getSku_jg() {
		return sku_jg;
	}
	public void setSku_jg(BigDecimal sku_jg) {
		this.sku_jg = sku_jg;
	}
	public BigDecimal getTjshl() {
		return tjshl;
	}
	public void setTjshl(BigDecimal tjshl) {
		this.tjshl = tjshl;
	}
	public BigDecimal getHj() {
		return hj;
	}
	public void setHj(BigDecimal hj) {
		this.hj = hj;
	}
	public int getYh_id() {
		return yh_id;
	}
	public void setYh_id(int yh_id) {
		this.yh_id = yh_id;
	}
	public int getShp_id() {
		return shp_id;
	}
	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}
	public String getChjshj() {
		return chjshj;
	}
	public void setChjshj(String chjshj) {
		this.chjshj = chjshj;
	}
	public int getSku_id() {
		return sku_id;
	}
	public void setSku_id(int sku_id) {
		this.sku_id = sku_id;
	}
	public String getShp_tp() {
		return shp_tp;
	}
	public void setShp_tp(String shp_tp) {
		this.shp_tp = shp_tp;
	}

	public void setShfxz(String shfxz) {
		this.shfxz = shfxz;
	}
	public String getKcdz() {
		return kcdz;
	}
	public void setKcdz(String kcdz) {
		this.kcdz = kcdz;
	}
	

}
