package com.xdkj.yccb.main.sys.dto;

public class RoleView {
	private Integer pid;
	private String roleName;
	private String systemRole;
	private String valid;
	private String remark;
	private String watercompany;//自来水公司
	private String wcid;//自来水公司id
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
	public String getSystemRole() {
		return systemRole;
	}
	public void setSystemRole(String systemRole) {
		this.systemRole = systemRole;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getWatercompany() {
		return watercompany;
	}
	public void setWatercompany(String watercompany) {
		this.watercompany = watercompany;
	}
	public String getWcid() {
		return wcid;
	}
	public void setWcid(String wcid) {
		this.wcid = wcid;
	}
	
}
