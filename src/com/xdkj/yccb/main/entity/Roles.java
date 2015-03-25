package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name = "roles", catalog = "remotemeter")
public class Roles implements java.io.Serializable {

	private Integer pid;
	private Watercompany watercompany;
	private String roleName;
	private String systemRole;
	private String valid;
	private String remark;
	private Set<AdminRole> adminRoles = new HashSet<AdminRole>(0);
	private Set<RoleAuthority> roleAuthorities = new HashSet<RoleAuthority>(0);

	public Roles() {
	}

	public Roles(String roleName, String systemRole, String valid) {
		this.roleName = roleName;
		this.systemRole = systemRole;
		this.valid = valid;
	}

	public Roles(Watercompany watercompany, String roleName, String systemRole,
			String valid, String remark, Set<AdminRole> adminRoles,
			Set<RoleAuthority> roleAuthorities) {
		this.watercompany = watercompany;
		this.roleName = roleName;
		this.systemRole = systemRole;
		this.valid = valid;
		this.remark = remark;
		this.adminRoles = adminRoles;
		this.roleAuthorities = roleAuthorities;
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
	@JoinColumn(name = "WCID")
	public Watercompany getWatercompany() {
		return this.watercompany;
	}

	public void setWatercompany(Watercompany watercompany) {
		this.watercompany = watercompany;
	}

	@Column(name = "RoleName", nullable = false, length = 20)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "SystemRole", nullable = false, length = 1)
	public String getSystemRole() {
		return this.systemRole;
	}

	public void setSystemRole(String systemRole) {
		this.systemRole = systemRole;
	}

	@Column(name = "Valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "Remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	public Set<AdminRole> getAdminRoles() {
		return this.adminRoles;
	}

	public void setAdminRoles(Set<AdminRole> adminRoles) {
		this.adminRoles = adminRoles;
	}

	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="roles")
	@JoinColumn(name="RoleID",referencedColumnName="pid")
	public Set<RoleAuthority> getRoleAuthorities() {
		return this.roleAuthorities;
	}

	public void setRoleAuthorities(Set<RoleAuthority> roleAuthorities) {
		this.roleAuthorities = roleAuthorities;
	}

}
