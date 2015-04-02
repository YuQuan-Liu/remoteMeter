package com.xdkj.yccb.security;

import java.util.Map;

public class UserForSession {
	private Integer pid;
	private String adminName;
	private String loginName;
	private String adminEmail;
	private String adminMobile;
	private Map<String, String> menus;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
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
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAdminMobile() {
		return adminMobile;
	}
	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}
	public Map<String, String> getMenus() {
		return menus;
	}
	public void setMenus(Map<String, String> menus) {
		this.menus = menus;
	}
}
