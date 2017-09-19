package com.protal.sale.bean;

import java.util.Date;

public class T_Mall_Flow {

	private int Id;
	private String psfsh;//配送方式
	private Date psshj;//配送时间
	private String psmsh;//配送描述
	private int yh_id;//用户id
	private Date chjshj;//创建时间
	private int dd_id;//订单id
	private String mqdd;//目前地点
	private String mdd;//目的地
	private String ywy;//业务员
	private String lxfsh;//联系方式
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getPsfsh() {
		return psfsh;
	}
	public void setPsfsh(String psfsh) {
		this.psfsh = psfsh;
	}
	public Date getPsshj() {
		return psshj;
	}
	public void setPsshj(Date psshj) {
		this.psshj = psshj;
	}
	public String getPsmsh() {
		return psmsh;
	}
	public void setPsmsh(String psmsh) {
		this.psmsh = psmsh;
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
	public int getDd_id() {
		return dd_id;
	}
	public void setDd_id(int i) {
		this.dd_id = i;
	}
	public String getMqdd() {
		return mqdd;
	}
	public void setMqdd(String mqdd) {
		this.mqdd = mqdd;
	}
	public String getMdd() {
		return mdd;
	}
	public void setMdd(String mdd) {
		this.mdd = mdd;
	}
	public String getYwy() {
		return ywy;
	}
	public void setYwy(String ywy) {
		this.ywy = ywy;
	}
	public String getLxfsh() {
		return lxfsh;
	}
	public void setLxfsh(String lxfsh) {
		this.lxfsh = lxfsh;
	}

	
}
