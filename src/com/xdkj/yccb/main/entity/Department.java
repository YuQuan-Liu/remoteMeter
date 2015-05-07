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

/**
 * Department generated by hbm2java
 */
@Entity
@Table(name = "department", catalog = "remotemeter")
public class Department implements java.io.Serializable {

	private Integer pid;
	private Watercompany watercompany;
	private String departmentName;
	private String valid;
	private String remark;
	private Set<Detaildepart> detaildeparts = new HashSet<Detaildepart>(0);
	private Set<Admininfo> admininfos = new HashSet<Admininfo>(0);

	public Department() {
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

	@Column(name = "DepartmentName", nullable = false, length = 20)
	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	public Set<Detaildepart> getDetaildeparts() {
		return this.detaildeparts;
	}

	public void setDetaildeparts(Set<Detaildepart> detaildeparts) {
		this.detaildeparts = detaildeparts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	public Set<Admininfo> getAdmininfos() {
		return this.admininfos;
	}

	public void setAdmininfos(Set<Admininfo> admininfos) {
		this.admininfos = admininfos;
	}

}
