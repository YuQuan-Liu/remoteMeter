package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class SettleView {

	private int c_id;
	private String g_addr;
	private String c_num;
	private String customerId;
	private String customerName;
	private String customerMobile;
	private String customerEmail;
	private BigDecimal customerBalance;
	private String customerAddr;
	private Byte prePaySign;
	private int warnThre;
	private int m_id;
	
	private String collectorAddr;
	private String meterAddr;
	private Byte valveState;
	private Byte meterState;
	private int isValve;
	
	private int deread;
	private int readdata;
	private String readtime;

	private int changeend;
	
	public int getChangeend() {
		return changeend;
	}
	public void setChangeend(int changeend) {
		this.changeend = changeend;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public String getG_addr() {
		return g_addr;
	}
	public void setG_addr(String g_addr) {
		this.g_addr = g_addr;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public Byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(Byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public int getWarnThre() {
		return warnThre;
	}
	public void setWarnThre(int warnThre) {
		this.warnThre = warnThre;
	}
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
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
	public int getIsValve() {
		return isValve;
	}
	public void setIsValve(int isValve) {
		this.isValve = isValve;
	}
	public int getDeread() {
		return deread;
	}
	public void setDeread(int deread) {
		this.deread = deread;
	}
	public int getReaddata() {
		return readdata;
	}
	public void setReaddata(int readdata) {
		this.readdata = readdata;
	}
	public String getReadtime() {
		return readtime;
	}
	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}
	public SettleView() {
		super();
	}
	@Override
	public String toString() {
		return "SettleView [c_id=" + c_id + ", g_addr=" + g_addr + ", c_num="
				+ c_num + ", customerId=" + customerId + ", customerName="
				+ customerName + ", customerMobile=" + customerMobile
				+ ", customerEmail=" + customerEmail + ", customerBalance="
				+ customerBalance + ", customerAddr=" + customerAddr
				+ ", prePaySign=" + prePaySign + ", warnThre=" + warnThre
				+ ", m_id=" + m_id + ", collectorAddr=" + collectorAddr
				+ ", meterAddr=" + meterAddr + ", valveState=" + valveState
				+ ", meterState=" + meterState + ", isValve=" + isValve
				+ ", deread=" + deread + ", readdata=" + readdata
				+ ", readtime=" + readtime + "]";
	}
	
	
}
