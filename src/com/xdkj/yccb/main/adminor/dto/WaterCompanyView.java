package com.xdkj.yccb.main.adminor.dto;

public class WaterCompanyView {
	private Integer pid;
	private String companyName;
	private String companyAddr;
	private String mark;
	private String emailHost;
	private String emailUser;
//	private String emailPassword;
	private String remark;
	private String telephone;
	private String payAddr;
	
	
	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
//	public String getEmailPassword() {
//		return emailPassword;
//	}
//	public void setEmailPassword(String emailPassword) {
//		this.emailPassword = emailPassword;
//	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getPayAddr() {
		return payAddr;
	}
	public void setPayAddr(String payAddr) {
		this.payAddr = payAddr;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
