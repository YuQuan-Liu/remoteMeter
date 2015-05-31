package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MeterdeductionlogView {

	private Integer pid;
	private String gprsAddr;//集中器地址
	private String collectaddr;//采集器地址
	private String meterAddr;//表地址
	private int meterRead;//扣费读数
	private Date meterReadTime;//扣费时间
	private int lastDeRead;//上次扣费读数
	private Date lastDeTime;//上次抄表时间
	private BigDecimal deMoney;//扣钱数
	private Date actionTime;//扣费时间
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getGprsAddr() {
		return gprsAddr;
	}
	public void setGprsAddr(String gprsAddr) {
		this.gprsAddr = gprsAddr;
	}
	public String getCollectaddr() {
		return collectaddr;
	}
	public void setCollectaddr(String collectaddr) {
		this.collectaddr = collectaddr;
	}
	public String getMeterAddr() {
		return meterAddr;
	}
	public void setMeterAddr(String meterAddr) {
		this.meterAddr = meterAddr;
	}
	public int getMeterRead() {
		return meterRead;
	}
	public void setMeterRead(int meterRead) {
		this.meterRead = meterRead;
	}
	public String getMeterReadTime() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return s.format(meterReadTime);
	}
	public void setMeterReadTime(Date meterReadTime) {
		this.meterReadTime = meterReadTime;
	}
	public int getLastDeRead() {
		return lastDeRead;
	}
	public void setLastDeRead(int lastDeRead) {
		this.lastDeRead = lastDeRead;
	}
	public String getLastDeTime() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return s.format(lastDeTime);
	}
	public void setLastDeTime(Date lastDeTime) {
		this.lastDeTime = lastDeTime;
	}
	public BigDecimal getDeMoney() {
		return deMoney;
	}
	public void setDeMoney(BigDecimal deMoney) {
		this.deMoney = deMoney;
	}
	public String getActionTime() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return s.format(actionTime);
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	
}
