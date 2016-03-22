package com.xdkj.yccb.main.charge.dto;

public class PostCharge {
	
	private String adminName;
	private String customerAddr;
	private String customerName;
	private String readTime;
	private double demoney;
	private String yl;
	private String c_num;
	private String customerID;
	private int hkid;
	private int pkid;
	private int settlelogid;
	private String lastread;
	private String thisread;
	private String changeend;
	private String cnDemoney;
	private String pkName;
	private String steelNum;
	private String formula;
	
	
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public int getPkid() {
		return pkid;
	}
	public void setPkid(int pkid) {
		this.pkid = pkid;
	}
	public int getSettlelogid() {
		return settlelogid;
	}
	public void setSettlelogid(int settlelogid) {
		this.settlelogid = settlelogid;
	}
	public int getHkid() {
		return hkid;
	}
	public void setHkid(int hkid) {
		this.hkid = hkid;
	}
	public double getDemoney() {
		return demoney;
	}
	public void setDemoney(double demoney) {
		this.demoney = demoney;
	}
	public String getCnDemoney() {
		return cnDemoney;
	}
	public void setCnDemoney(String cnDemoney) {
		this.cnDemoney = cnDemoney;
	}
	public String getSteelNum() {
		return steelNum;
	}
	public void setSteelNum(String steelNum) {
		this.steelNum = steelNum;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getReadTime() {
		return readTime;
	}
	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}
	public String getYl() {
		return yl;
	}
	public void setYl(String yl) {
		this.yl = yl;
	}
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getLastread() {
		return lastread;
	}
	public void setLastread(String lastread) {
		this.lastread = lastread;
	}
	public String getThisread() {
		return thisread;
	}
	public void setThisread(String thisread) {
		this.thisread = thisread;
	}
	public String getChangeend() {
		return changeend;
	}
	public void setChangeend(String changeend) {
		this.changeend = changeend;
	}
	public PostCharge() {
		super();
	}
	
	
}
