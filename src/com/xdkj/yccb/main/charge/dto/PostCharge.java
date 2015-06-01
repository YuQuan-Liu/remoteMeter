package com.xdkj.yccb.main.charge.dto;

public class PostCharge {
	
	private String adminName;
	private String customerAddr;
	private String customerName;
	private String readTime;
	private double amount;
	private String yl;
	private String c_num;
	private String customerID;
	private String lastread;
	private String thisread;
	private String changeend;
	private String cnAmount;
	private String pkName;
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getCnAmount() {
		return cnAmount;
	}
	public void setCnAmount(String cnAmount) {
		this.cnAmount = cnAmount;
	}
	public PostCharge() {
		super();
	}
	
	
}
