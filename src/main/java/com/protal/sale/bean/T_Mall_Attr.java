package com.protal.sale.bean;


public class T_Mall_Attr {

	private int Id;			//主键：属性id
	private String shxm_mch;//属性名名称
	private String shfqy;	//是否启用
	private int flbh2;		//分类编号2
	private String chjshj;	//创建时间
//	private List<T_Mall_Value> valueList;
	
//	public List<T_Mall_Value> getValueList() {
//		return valueList;
//	}
//	public void setValueList(List<T_Mall_Value> valueList) {
//		this.valueList = valueList;
//	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getShxm_mch() {
		return shxm_mch;
	}
	public void setShxm_mch(String shxm_mch) {
		this.shxm_mch = shxm_mch;
	}
	public String getShfqy() {
		return shfqy;
	}
	public void setShfqy(String shfqy) {
		this.shfqy = shfqy;
	}
	public int getFlbh2() {
		return flbh2;
	}
	public void setFlbh2(int flbh2) {
		this.flbh2 = flbh2;
	}
	public String getChjshj() {
		return chjshj;
	}
	public void setChjshj(String chjshj) {
		this.chjshj = chjshj;
	}
	
	

	
}
