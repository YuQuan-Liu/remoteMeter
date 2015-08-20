package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;


public class SettleSum {
	
	private String pricekindname;
	private int yl;
	private BigDecimal demoney;
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
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	public SettleSum() {
		super();
	}
	
	
}
