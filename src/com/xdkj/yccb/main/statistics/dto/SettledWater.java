package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;

public class SettledWater {

	private int settleid;
	private int n_id;
	private String n_name;
	private int yl;
	private BigDecimal demoney;
	private String startTime;
	public int getN_id() {
		return n_id;
	}
	public void setN_id(int n_id) {
		this.n_id = n_id;
	}
	public String getN_name() {
		return n_name;
	}
	public void setN_name(String n_name) {
		this.n_name = n_name;
	}
	public int getYl() {
		return yl;
	}
	public void setYl(int yl) {
		this.yl = yl;
	}
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	public int getSettleid() {
		return settleid;
	}
	public void setSettleid(int settleid) {
		this.settleid = settleid;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public SettledWater() {
		super();
	}
	
	
}
