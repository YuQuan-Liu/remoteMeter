package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;

public class ChargeRate {

	private int n_id;
	private String n_name;
	private int owecount;
	private int allcount;
	private double owerate;
	private BigDecimal owebalance;
	private BigDecimal balance;
	private BigDecimal demoney;
	private double chargerate;
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
	public int getOwecount() {
		return owecount;
	}
	public void setOwecount(int owecount) {
		this.owecount = owecount;
	}
	public int getAllcount() {
		return allcount;
	}
	public void setAllcount(int allcount) {
		this.allcount = allcount;
	}
	public double getOwerate() {
		return owerate;
	}
	public void setOwerate(double owerate) {
		this.owerate = owerate;
	}
	public BigDecimal getOwebalance() {
		return owebalance;
	}
	public void setOwebalance(BigDecimal owebalance) {
		this.owebalance = owebalance;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getDemoney() {
		return demoney;
	}
	public void setDemoney(BigDecimal demoney) {
		this.demoney = demoney;
	}
	public double getChargerate() {
		return chargerate;
	}
	public void setChargerate(double chargerate) {
		this.chargerate = chargerate;
	}
	public ChargeRate() {
		super();
	}
	
	
}
