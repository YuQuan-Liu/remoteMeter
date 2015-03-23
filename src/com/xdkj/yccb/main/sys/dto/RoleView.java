package com.xdkj.yccb.main.sys.dto;

public class RoleView {
	private Integer pid;
	//private Watercompany watercompany;
	private String roleName;
	private char systemRole;
	private char valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public char getSystemRole() {
		return systemRole;
	}
	public void setSystemRole(char systemRole) {
		this.systemRole = systemRole;
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
