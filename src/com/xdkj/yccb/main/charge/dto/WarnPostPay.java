package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class WarnPostPay {

	private int c_id;
	private String c_num;
	private String customerName;
	private String customerMobile;
	private BigDecimal demoney;
	private int hk;
	
	public int getHk() {
		return hk;
	}
	public void setHk(int hk) {
		this.hk = hk;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	
}
