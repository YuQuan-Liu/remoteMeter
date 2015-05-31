package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

public class SettledView {

	private int c_id;
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
	
	private int lastderead;
	private int meterread;
	private String meterreadtime;
	
	private int mdl_id;
	private BigDecimal demoney;
	private int paytype;
	private int printed;
	private int payed;
	private String pricekindname;
	
	public int getMdl_id() {
		return mdl_id;
	}
	public void setMdl_id(int mdl_id) {
		this.mdl_id = mdl_id;
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
	public int getLastderead() {
		return lastderead;
	}
	public void setLastderead(int lastderead) {
		this.lastderead = lastderead;
	}
	public int getMeterread() {
		return meterread;
	}
	public void setMeterread(int meterread) {
		this.meterread = meterread;
	}
	public String getMeterreadtime() {
		return meterreadtime;
	}
	public void setMeterreadtime(String meterreadtime) {
		this.meterreadtime = meterreadtime;
	}
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	public int getPaytype() {
		return paytype;
	}
	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}
	public int getPrinted() {
		return printed;
	}
	public void setPrinted(int printed) {
		this.printed = printed;
	}
	public int getPayed() {
		return payed;
	}
	public void setPayed(int payed) {
		this.payed = payed;
	}
	public String getPricekindname() {
		return pricekindname;
	}
	public void setPricekindname(String pricekindname) {
		this.pricekindname = pricekindname;
	}
	public SettledView() {
		super();
	}
	
	
	
}
