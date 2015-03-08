package com.xdkj.yccb.main.adminor.dto;

import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Watercompany;

public class AdminInfoView {
	private Integer pid;
	/*private Watercompany watercompany;
	private Department department;*/
	private String adminName;
	private String loginName;
	private String loginKey;
	private String adminEmail;
	private String adminAddr;
	private String adminMobile;
	private String adminTel;
	private int noWc;
	private char valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/*public Watercompany getWatercompany() {
		return watercompany;
	}
	public void setWatercompany(Watercompany watercompany) {
		this.watercompany = watercompany;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}*/
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginKey() {
		return loginKey;
	}
	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminAddr() {
		return adminAddr;
	}
	public void setAdminAddr(String adminAddr) {
		this.adminAddr = adminAddr;
	}
	public String getAdminMobile() {
		return adminMobile;
	}
	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}
	public String getAdminTel() {
		return adminTel;
	}
	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}
	public int getNoWc() {
		return noWc;
	}
	public void setNoWc(int noWc) {
		this.noWc = noWc;
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
