package com.protal.sale.bean;

public class OBJECT_T_MALL_SKU extends T_Mall_Sku{

	private T_Mall_Product spu;
	
	private T_MALL_TRADE_MARK tm;

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
	
	
	
}
