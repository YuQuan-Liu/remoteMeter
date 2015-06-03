package com.xdkj.yccb.main.statistics.dto;

import java.math.BigDecimal;
/**
 * 时间段内  小区的全部的结算的用水量的和  和扣费的和    用在结算用水统计中   
 * @author Rocket
 *
 */
public class SettledWaterN {

	
	private int n_id;
	private String n_name;
	private int yl;
	private BigDecimal demoney;
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
	public SettledWaterN() {
		super();
	}
	
	
}
