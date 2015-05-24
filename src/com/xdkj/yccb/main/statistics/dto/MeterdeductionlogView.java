package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;
import java.util.Date;


public class MeterdeductionlogView {

	private Integer pid;
	private Integer priceKindID;
	private Integer meterID;
	private Integer settleLogID;
	private int meterRead;
	private Date meterReadTime;
	private int lastDeRead;
	private Date lastDeTime;
	private BigDecimal deMoney;
	private Integer settleSingleId;
	private Date actionTime;
	private char valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getPriceKindID() {
		return priceKindID;
	}
	public void setPriceKindID(Integer priceKindID) {
		this.priceKindID = priceKindID;
	}
	public Integer getMeterID() {
		return meterID;
	}
	public void setMeterID(Integer meterID) {
		this.meterID = meterID;
	}
	public Integer getSettleLogID() {
		return settleLogID;
	}
	public void setSettleLogID(Integer settleLogID) {
		this.settleLogID = settleLogID;
	}
	public int getMeterRead() {
		return meterRead;
	}
	public void setMeterRead(int meterRead) {
		this.meterRead = meterRead;
	}
	public Date getMeterReadTime() {
		return meterReadTime;
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
	public Date getLastDeTime() {
		return lastDeTime;
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
	public Integer getSettleSingleId() {
		return settleSingleId;
	}
	public void setSettleSingleId(Integer settleSingleId) {
		this.settleSingleId = settleSingleId;
	}
	public Date getActionTime() {
		return actionTime;
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public char getValid() {
		return valid;
	}
	public void setValid(char valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
