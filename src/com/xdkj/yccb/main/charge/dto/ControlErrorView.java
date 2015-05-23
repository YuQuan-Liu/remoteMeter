package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class ControlErrorView {
		
	private String g_addr;
	private String c_num;
//	private String lou;
//	private String dy;
//	private String hu;
	private String customerId;
	private String customerName;
	private String customerAddr;
	private String customerMobile;
	private BigDecimal customerBalance;
	
	private String collectorAddr;
	private String meterAddr;
	private Byte valveState;
	private Byte meterState;
	private int switch_;
	private String errorReason;
	private String completeTime;
	private int conf_id;
	public String getG_addr() {
		return g_addr;
	}
	public void setG_addr(String g_addr) {
		this.g_addr = g_addr;
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
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	public String getCollectorAddr() {
		return collectorAddr;
	}
	public void setCollectorAddr(String collectorAddr) {
		this.collectorAddr = collectorAddr;
	}
	public String getMeterAddr() {
		return meterAddr;
	}
	public void setMeterAddr(String meterAddr) {
		this.meterAddr = meterAddr;
	}
	public Byte getValveState() {
		return valveState;
	}
	public void setValveState(Byte valveState) {
		this.valveState = valveState;
	}
	public Byte getMeterState() {
		return meterState;
	}
	public void setMeterState(Byte meterState) {
		this.meterState = meterState;
	}
	public int getSwitch_() {
		return switch_;
	}
	public void setSwitch_(int switch_) {
		this.switch_ = switch_;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public int getConf_id() {
		return conf_id;
	}
	public void setConf_id(int conf_id) {
		this.conf_id = conf_id;
	}
	public ControlErrorView() {
		super();
	}
	
	
}
