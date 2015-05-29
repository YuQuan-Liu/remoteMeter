package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;

public class AdminSum {

	private BigDecimal amount;
	private String adminName;

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public AdminSum() {
		super();
	}
	
	
}
