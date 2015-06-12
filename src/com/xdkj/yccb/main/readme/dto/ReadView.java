package com.xdkj.yccb.main.readme.dto;

import java.math.BigDecimal;


public class ReadView {
	
	private int c_id;
	private int m_id;
	private String m_apid;
	private String n_name;
	private int n_id;
	private int g_id;
	private String g_addr;
	private String c_num;
//	private String lou;
//	private String dy;
//	private String hu;
//	private String customerId;
	private String customerName;
	private String customerMobile;
	private BigDecimal customerBalance;
	private String collectorAddr;
	private String meterAddr;
	private Byte valveState;
	private Byte meterState;
	private int deread;
	private int readdata;
	private String readtime;
	
	private Byte prePaySign;
	private String meterTypeName;
	private int remote;
	
	
	public String getM_apid() {
		return m_apid;
	}
	public void setM_apid(String m_apid) {
		this.m_apid = m_apid;
	}
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
	public String getN_name() {
		return n_name;
	}
	public void setN_name(String n_name) {
		this.n_name = n_name;
	}
	public int getN_id() {
		return n_id;
	}
	public void setN_id(int n_id) {
		this.n_id = n_id;
	}
	public int getG_id() {
		return g_id;
	}
	public void setG_id(int g_id) {
		this.g_id = g_id;
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
	public Byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(Byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public String getMeterTypeName() {
		return meterTypeName;
	}
	public void setMeterTypeName(String meterTypeName) {
		this.meterTypeName = meterTypeName;
	}
	public int getRemote() {
		return remote;
	}
	public void setRemote(int remote) {
		this.remote = remote;
	}
	public ReadView() {
		super();
	}
	
	
}
