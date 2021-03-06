package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Settlelog generated by hbm2java
 */
@Entity
@Table(name = "settlelog", catalog = "remotemeter")
public class Settlelog implements java.io.Serializable {

	private Integer pid;
	private Admininfo admininfo;
	private Readlog readlog;
	private int objectId;
	private int objectType;
	private int settleStatus;
	private Date startTime;
	private int auto;
	private String remark;
	private Set<Meterdeductionlog> meterdeductionlogs = new HashSet<Meterdeductionlog>(
			0);

	public Settlelog() {
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
	@JoinColumn(name = "AdminID", nullable = false)
	public Admininfo getAdmininfo() {
		return this.admininfo;
	}

	public void setAdmininfo(Admininfo admininfo) {
		this.admininfo = admininfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ReadLogID", nullable = false)
	public Readlog getReadlog() {
		return this.readlog;
	}

	public void setReadlog(Readlog readlog) {
		this.readlog = readlog;
	}

	@Column(name = "ObjectID", nullable = false)
	public int getObjectId() {
		return this.objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	@Column(name = "ObjectType", nullable = false)
	public int getObjectType() {
		return this.objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	@Column(name = "SettleStatus", nullable = false)
	public int getSettleStatus() {
		return this.settleStatus;
	}

	public void setSettleStatus(int settleStatus) {
		this.settleStatus = settleStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "StartTime", nullable = false, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@Column(name = "auto", nullable = false)
	public int getAuto() {
		return auto;
	}

	public void setAuto(int auto) {
		this.auto = auto;
	}

	@Column(name = "Remark", length = 65535)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "settlelog")
	public Set<Meterdeductionlog> getMeterdeductionlogs() {
		return this.meterdeductionlogs;
	}

	public void setMeterdeductionlogs(Set<Meterdeductionlog> meterdeductionlogs) {
		this.meterdeductionlogs = meterdeductionlogs;
	}

}
