package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Admininfo generated by hbm2java
 */
@Entity
@Table(name = "admininfo", catalog = "remotemeter", uniqueConstraints = @UniqueConstraint(columnNames = "LoginName"))
public class Admininfo implements java.io.Serializable {

	private Integer pid;
	private Watercompany watercompany;
	private Department department;
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
	private Set<Valvelog> valvelogs = new HashSet<Valvelog>(0);
	private Set<Readlog> readlogs = new HashSet<Readlog>(0);
	private Set<Customerpaylog> customerpaylogs = new HashSet<Customerpaylog>(0);
	private Set<Useractionlog> useractionlogs = new HashSet<Useractionlog>(0);
	private Set<Settlelog> settlelogs = new HashSet<Settlelog>(0);
	private Set<AdminRole> adminRoles = new HashSet<AdminRole>(0);

	public Admininfo() {
	}

	public Admininfo(Watercompany watercompany, String adminName,
			String loginName, String loginKey, int noWc, char valid) {
		this.watercompany = watercompany;
		this.adminName = adminName;
		this.loginName = loginName;
		this.loginKey = loginKey;
		this.noWc = noWc;
		this.valid = valid;
	}

	public Admininfo(Watercompany watercompany, Department department,
			String adminName, String loginName, String loginKey,
			String adminEmail, String adminAddr, String adminMobile,
			String adminTel, int noWc, char valid, String remark,
			Set<Valvelog> valvelogs, Set<Readlog> readlogs,
			Set<Customerpaylog> customerpaylogs,
			Set<Useractionlog> useractionlogs, Set<Settlelog> settlelogs,
			Set<AdminRole> adminRoles) {
		this.watercompany = watercompany;
		this.department = department;
		this.adminName = adminName;
		this.loginName = loginName;
		this.loginKey = loginKey;
		this.adminEmail = adminEmail;
		this.adminAddr = adminAddr;
		this.adminMobile = adminMobile;
		this.adminTel = adminTel;
		this.noWc = noWc;
		this.valid = valid;
		this.remark = remark;
		this.valvelogs = valvelogs;
		this.readlogs = readlogs;
		this.customerpaylogs = customerpaylogs;
		this.useractionlogs = useractionlogs;
		this.settlelogs = settlelogs;
		this.adminRoles = adminRoles;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PID", unique = true, nullable = false)
	public Integer getPid() {
		return this.pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WCID", nullable = false)
	public Watercompany getWatercompany() {
		return this.watercompany;
	}

	public void setWatercompany(Watercompany watercompany) {
		this.watercompany = watercompany;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DepartmentID")
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Column(name = "AdminName", nullable = false, length = 20)
	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Column(name = "LoginName", unique = true, nullable = false, length = 10)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LoginKey", nullable = false, length = 32)
	public String getLoginKey() {
		return this.loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	@Column(name = "AdminEmail", length = 80)
	public String getAdminEmail() {
		return this.adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	@Column(name = "AdminAddr", length = 80)
	public String getAdminAddr() {
		return this.adminAddr;
	}

	public void setAdminAddr(String adminAddr) {
		this.adminAddr = adminAddr;
	}

	@Column(name = "AdminMobile", length = 20)
	public String getAdminMobile() {
		return this.adminMobile;
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}

	@Column(name = "AdminTel", length = 20)
	public String getAdminTel() {
		return this.adminTel;
	}

	public void setAdminTel(String adminTel) {
		this.adminTel = adminTel;
	}

	@Column(name = "NoWC", nullable = false)
	public int getNoWc() {
		return this.noWc;
	}

	public void setNoWc(int noWc) {
		this.noWc = noWc;
	}

	@Column(name = "Valid", nullable = false, length = 1)
	public char getValid() {
		return this.valid;
	}

	public void setValid(char valid) {
		this.valid = valid;
	}

	@Column(name = "Remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<Valvelog> getValvelogs() {
		return this.valvelogs;
	}

	public void setValvelogs(Set<Valvelog> valvelogs) {
		this.valvelogs = valvelogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<Readlog> getReadlogs() {
		return this.readlogs;
	}

	public void setReadlogs(Set<Readlog> readlogs) {
		this.readlogs = readlogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<Customerpaylog> getCustomerpaylogs() {
		return this.customerpaylogs;
	}

	public void setCustomerpaylogs(Set<Customerpaylog> customerpaylogs) {
		this.customerpaylogs = customerpaylogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<Useractionlog> getUseractionlogs() {
		return this.useractionlogs;
	}

	public void setUseractionlogs(Set<Useractionlog> useractionlogs) {
		this.useractionlogs = useractionlogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<Settlelog> getSettlelogs() {
		return this.settlelogs;
	}

	public void setSettlelogs(Set<Settlelog> settlelogs) {
		this.settlelogs = settlelogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admininfo")
	public Set<AdminRole> getAdminRoles() {
		return this.adminRoles;
	}

	public void setAdminRoles(Set<AdminRole> adminRoles) {
		this.adminRoles = adminRoles;
	}

}
