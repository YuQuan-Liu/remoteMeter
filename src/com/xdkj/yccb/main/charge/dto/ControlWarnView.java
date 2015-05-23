package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class ControlWarnView {

	private int c_id;
	private int m_id;
	private String g_addr;
	private String c_num;
//	private String lou;
//	private String dy;
//	private String hu;
	private String customerId;
	private String customerName;
	private String customerAddr;
	private String customerMobile;
	private String customerEmail;
	private Byte prePaySign;
	private BigDecimal customerBalance;
	private int warnThre;
	private int warnStyle;
	
	private String collectorAddr;
	private String meterAddr;
	private int isValve;
	private Byte valveState;
	private Byte meterState;
	private String warnCount;
	
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
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
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(Byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	public int getWarnThre() {
		return warnThre;
	}
	public void setWarnThre(int warnThre) {
		this.warnThre = warnThre;
	}
	public int getWarnStyle() {
		return warnStyle;
	}
	public void setWarnStyle(int warnStyle) {
		this.warnStyle = warnStyle;
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
	public int getIsValve() {
		return isValve;
	}
	public void setIsValve(int isValve) {
		this.isValve = isValve;
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
	public String getWarnCount() {
		return warnCount;
	}
	public void setWarnCount(String warnCount) {
		this.warnCount = warnCount;
	}
	public ControlWarnView() {
		super();
	}
	
	
	
}
