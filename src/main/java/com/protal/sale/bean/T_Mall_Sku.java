package com.protal.sale.bean;

public class T_Mall_Sku {
	private int Id;
	private int shp_id;
	private int kc;
	private double jg;
	private String chjshj;
	private String sku_mch;
	private int sku_xl;//销量默认是0
	private String kcdz;//库存地址
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getShp_id() {
		return shp_id;
	}
	public void setShp_id(int shp_id) {
		this.shp_id = shp_id;
	}
	public int getKc() {
		return kc;
	}
	public void setKc(int kc) {
		this.kc = kc;
	}
	public double getJg() {
		return jg;
	}
	public void setJg(double jg) {
		this.jg = jg;
	}
	public String getChjshj() {
		return chjshj;
	}
	public void setChjshj(String chjshj) {
		this.chjshj = chjshj;
	}
	public String getSku_mch() {
		return sku_mch;
	}
	public void setSku_mch(String sku_mch) {
		this.sku_mch = sku_mch;
	}
	public int getSku_xl() {
		return sku_xl;
	}
	public void setSku_xl(int sku_xl) {
		this.sku_xl = sku_xl;
	}
	public String getKcdz() {
		return kcdz;
	}
	public void setKcdz(String kcdz) {
		this.kcdz = kcdz;
	}

	
	
}
