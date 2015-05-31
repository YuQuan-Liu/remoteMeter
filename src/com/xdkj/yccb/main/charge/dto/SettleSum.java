package com.xdkj.yccb.main.charge.dto;


public class SettleSum {
	
	private String pricekindname;
	private int yl;
	private double demoney;
	public String getPricekindname() {
		return pricekindname;
	}
	public void setPricekindname(String pricekindname) {
		this.pricekindname = pricekindname;
	}
	public int getYl() {
		return yl;
	}
	public void setYl(int yl) {
		this.yl = yl;
	}
	public double getDemoney() {
		return demoney;
	}
	public void setDemoney(double demoney) {
		this.demoney = demoney;
	}
	public SettleSum() {
		super();
	}
	
	
}
