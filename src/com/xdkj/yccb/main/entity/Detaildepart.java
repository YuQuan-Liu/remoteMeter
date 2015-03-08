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
 * Detaildepart generated by hbm2java
 */
@Entity
@Table(name = "detaildepart", catalog = "remotemeter")
public class Detaildepart implements java.io.Serializable {

	private Integer pid;
	private Department department;
	private Neighbor neighbor;
	private char valid;

	public Detaildepart() {
	}

	public Detaildepart(Department department, Neighbor neighbor, char valid) {
		this.department = department;
		this.neighbor = neighbor;
		this.valid = valid;
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
	@JoinColumn(name = "DID", nullable = false)
	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NID", nullable = false)
	public Neighbor getNeighbor() {
		return this.neighbor;
	}

	public void setNeighbor(Neighbor neighbor) {
		this.neighbor = neighbor;
	}

	@Column(name = "Valid", nullable = false, length = 1)
	public char getValid() {
		return this.valid;
	}

	public void setValid(char valid) {
		this.valid = valid;
	}

}
