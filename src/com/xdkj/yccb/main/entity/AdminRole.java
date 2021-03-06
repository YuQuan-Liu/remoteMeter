package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * AdminRole generated by hbm2java
 */
@Entity
@Table(name = "admin_role", catalog = "remotemeter")
public class AdminRole implements java.io.Serializable {

	private Integer pid;
	private Roles roles;
	private Admininfo admininfo;

	public AdminRole() {
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
	@JoinColumn(name = "RoleID", nullable = false)
	public Roles getRoles() {
		return this.roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AdminID", nullable = false)
	public Admininfo getAdmininfo() {
		return this.admininfo;
	}

	public void setAdmininfo(Admininfo admininfo) {
		this.admininfo = admininfo;
	}

}
