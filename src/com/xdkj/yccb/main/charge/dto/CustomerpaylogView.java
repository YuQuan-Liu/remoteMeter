package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CustomerpaylogView {
	private Integer pid;
	private String c_num;//用户号
	private String customerId;//用户id
	private String customerName;//用户名
	private String customerAddr;//地址
	private String adminName;//操作员
	private BigDecimal amount;
	private String actionTime;
	private byte prePaySign;
	private BigDecimal customerBalance;
	
	public CustomerpaylogView() {
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

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

	public String getCustomerAddr() {
		return customerAddr;
	}

	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
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

	public byte getPrePaySign() {
		return prePaySign;
	}

	public void setPrePaySign(byte prePaySign) {
		this.prePaySign = prePaySign;
	}

	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	
	
}
