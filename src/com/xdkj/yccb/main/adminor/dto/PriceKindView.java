package com.xdkj.yccb.main.adminor.dto;


public class PriceKindView {
	
	private Integer pid;
//	private String watercompany;
	private String priceKindName;
	private double priceKindFine;
	private String valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
//	public String getWatercompany() {
//		return watercompany;
//	}
//	public void setWatercompany(String watercompany) {
//		this.watercompany = watercompany;
//	}
	public String getPriceKindName() {
		return priceKindName;
	}
	public void setPriceKindName(String priceKindName) {
		this.priceKindName = priceKindName;
	}
	public double getPriceKindFine() {
		return priceKindFine;
	}
	public void setPriceKindFine(double priceKindFine) {
		this.priceKindFine = priceKindFine;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
