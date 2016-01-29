package com.xdkj.yccb.main.infoin.dto;

public class MeterViewSimple {

	private int pid;
	private String collectorAddr;
	private String meterAddr;
	
	
	public MeterViewSimple() {
		super();
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
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
	
}
