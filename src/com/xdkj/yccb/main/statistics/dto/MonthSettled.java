package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;

public class MonthSettled {

	private int month;
	private int yl;
	private BigDecimal demoney;
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
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
	public MonthSettled() {
		super();
	}
	
	
}
