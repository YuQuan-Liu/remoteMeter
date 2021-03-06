package com.xdkj.yccb.security;

import java.util.Map;

import com.xdkj.yccb.main.entity.Department;

public class UserForSession {
	private Integer pid;
	private String adminName;
	private String loginName;
	private String adminEmail;
	private String adminMobile;
	private int depart_id;
	/**
	 * 自来水公司id
	 */
	private int waterComId;
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
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public Map<String, String> getMenus() {
		return menus;
	}
	public void setMenus(Map<String, String> menus) {
		this.menus = menus;
	}
	public int getWaterComId() {
		return waterComId;
	}
	public void setWaterComId(int waterComId) {
		this.waterComId = waterComId;
	}
}
