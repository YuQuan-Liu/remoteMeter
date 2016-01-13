package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class QYDetail {
	
	private String bpname;
	private int yl;
	private BigDecimal price;
	private BigDecimal demoney;
	public String getBpname() {
		return bpname;
	}
	public void setBpname(String bpname) {
		this.bpname = bpname;
	}
	public int getYl() {
		return yl;
	}
	public void setYl(int yl) {
		this.yl = yl;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	public QYDetail() {
		super();
	}
	
	

}
