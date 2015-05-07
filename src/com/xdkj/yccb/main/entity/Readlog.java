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
 * Readlog generated by hbm2java
 */
@Entity
@Table(name = "readlog", catalog = "remotemeter")
public class Readlog implements java.io.Serializable {

	private Integer pid;
	private Admininfo admininfo;
	private Integer objectId;
	private int readType;
	private int remote;
	private int readObject;
	private String ip;
	private int readStatus;
	private String failReason;
	private Date startTime;
	private Date completeTime;
	private int settle;
	private String result;
	private Set<Readmeterlog> readmeterlogs = new HashSet<Readmeterlog>(0);
	private Set<Settlelog> settlelogs = new HashSet<Settlelog>(0);

	public Readlog() {
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

	@Column(name = "ObjectID")
	public Integer getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	@Column(name = "ReadType", nullable = false)
	public int getReadType() {
		return this.readType;
	}

	public void setReadType(int readType) {
		this.readType = readType;
	}

	@Column(name = "Remote", nullable = false)
	public int getRemote() {
		return this.remote;
	}

	public void setRemote(int remote) {
		this.remote = remote;
	}

	@Column(name = "ReadObject", nullable = false)
	public int getReadObject() {
		return this.readObject;
	}

	public void setReadObject(int readObject) {
		this.readObject = readObject;
	}

	@Column(name = "IP", nullable = false, length = 30)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "ReadStatus", nullable = false)
	public int getReadStatus() {
		return this.readStatus;
	}

	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}

	@Column(name = "FailReason", length = 65535)
	public String getFailReason() {
		return this.failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "StartTime", nullable = false, length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CompleteTime", length = 19)
	public Date getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	@Column(name = "Settle", nullable = false)
	public int getSettle() {
		return this.settle;
	}

	public void setSettle(int settle) {
		this.settle = settle;
	}

	@Column(name = "Result", length = 65535)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "readlog")
	public Set<Readmeterlog> getReadmeterlogs() {
		return this.readmeterlogs;
	}

	public void setReadmeterlogs(Set<Readmeterlog> readmeterlogs) {
		this.readmeterlogs = readmeterlogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "readlog")
	public Set<Settlelog> getSettlelogs() {
		return this.settlelogs;
	}

	public void setSettlelogs(Set<Settlelog> settlelogs) {
		this.settlelogs = settlelogs;
	}

}
