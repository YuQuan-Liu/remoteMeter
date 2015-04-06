package com.xdkj.yccb.main.adminor.dto;

public class DepartmentView {
	private Integer pid;
	private String watercompany;
	private String departmentName;
	private char valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getWatercompany() {
		return watercompany;
	}
	public void setWatercompany(String watercompany) {
		this.watercompany = watercompany;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
