package com.xdkj.yccb.main.statistics.dto;

public class VIPMonitor {

	private int m_id;
	private String readtime;
	private int readdata;
	private int day;
	private String meterAddr;
	public int getM_id() {
		return m_id;
	}
	public void setM_id(int m_id) {
		this.m_id = m_id;
	}
	public String getReadtime() {
		return readtime;
	}
	public void setReadtime(String readtime) {
		this.readtime = readtime;
	}
	public int getReaddata() {
		return readdata;
	}
	public void setReaddata(int readdata) {
		this.readdata = readdata;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getMeterAddr() {
		return meterAddr;
	}
	public void setMeterAddr(String meterAddr) {
		this.meterAddr = meterAddr;
	}
	public VIPMonitor() {
		super();
	}
	
	
}
