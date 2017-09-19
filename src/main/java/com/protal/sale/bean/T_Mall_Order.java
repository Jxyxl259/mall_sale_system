package com.protal.sale.bean;

import java.math.BigDecimal;
import java.util.Date;

public class T_Mall_Order {

	private int Id;				//订单id
	private String shhr;		//收货人
	private BigDecimal zje;			//总金额
	private int jdh;			//进度号
	private int yh_id;		//用户id
	private Date chjshj;		//创建时间
	private Date yjsdshj;		//预计送达时间
	private int dzh_id;		//地址id
	private String dzh_mch;		//地址名称
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getShhr() {
		return shhr;
	}
	public void setShhr(String shhr) {
		this.shhr = shhr;
	}
	public BigDecimal getZje() {
		return zje;
	}
	public void setZje(BigDecimal zje) {
		this.zje = zje;
	}
	public int getJdh() {
		return jdh;
	}
	public void setJdh(int i) {
		this.jdh = i;
	}
	public int getYh_id() {
		return yh_id;
	}
	public void setYh_id(int yh_id) {
		this.yh_id = yh_id;
	}
	public Date getChjshj() {
		return chjshj;
	}
	public void setChjshj(Date chjshj) {
		this.chjshj = chjshj;
	}
	public Date getYjsdshj() {
		return yjsdshj;
	}
	public void setYjsdshj(Date yjsdshj) {
		this.yjsdshj = yjsdshj;
	}
	public int getDzh_id() {
		return dzh_id;
	}
	public void setDzh_id(int dzh_id) {
		this.dzh_id = dzh_id;
	}
	public String getDzh_mch() {
		return dzh_mch;
	}
	public void setDzh_mch(String dzh_mch) {
		this.dzh_mch = dzh_mch;
	}
	
	

	
}
