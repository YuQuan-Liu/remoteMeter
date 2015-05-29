package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfo {
	
	private String customerId;
	private String customerName;
	private String customerMobile;
	private String c_num;
	private String customerAddr;
	private BigDecimal customerBalance;
	private byte prePaySign;
	
	private BigDecimal amount;
	private String actionTime;
	private byte payPre;
	private String adminName;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	public byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public byte getPayPre() {
		return payPre;
	}
	public void setPayPre(byte payPre) {
		this.payPre = payPre;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public PayInfo() {
		super();
	}
	
	
	
}
