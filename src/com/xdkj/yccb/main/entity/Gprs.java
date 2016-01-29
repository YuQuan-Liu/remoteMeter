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
 * Gprs generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "gprs", catalog = "remotemeter")
public class Gprs implements java.io.Serializable {

	private Integer pid;
	private Neighbor neighbor;
	private String gprstel;
	private String gprsaddr;
	private String installAddr;
	private Date installTime;
	private String installPerson;
	private int gprsprotocol;
	private String ip;
	private int port;
	private int cleanSwitch;
	private String valid;
	private String remark;
	private Set<Meter> meters = new HashSet<Meter>(0);

	public Gprs() {
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
	@JoinColumn(name = "NeighborID", nullable = false)
	public Neighbor getNeighbor() {
		return this.neighbor;
	}

	public void setNeighbor(Neighbor neighbor) {
		this.neighbor = neighbor;
	}

	@Column(name = "GPRSTel", nullable = false, length = 20)
	public String getGprstel() {
		return this.gprstel;
	}

	public void setGprstel(String gprstel) {
		this.gprstel = gprstel;
	}

	@Column(name = "GPRSAddr", nullable = false, length = 20)
	public String getGprsaddr() {
		return this.gprsaddr;
	}

	public void setGprsaddr(String gprsaddr) {
		this.gprsaddr = gprsaddr;
	}

	@Column(name = "InstallAddr", length = 80)
	public String getInstallAddr() {
		return this.installAddr;
	}

	public void setInstallAddr(String installAddr) {
		this.installAddr = installAddr;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "InstallTime", length = 19)
	public Date getInstallTime() {
		return this.installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	@Column(name = "InstallPerson", length = 20)
	public String getInstallPerson() {
		return this.installPerson;
	}

	public void setInstallPerson(String installPerson) {
		this.installPerson = installPerson;
	}

	@Column(name = "GPRSProtocol", nullable = false)
	public int getGprsprotocol() {
		return this.gprsprotocol;
	}

	public void setGprsprotocol(int gprsprotocol) {
		this.gprsprotocol = gprsprotocol;
	}

	@Column(name = "IP", nullable = false, length = 30)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "Port", nullable = false)
	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Column(name = "CleanSwitch", nullable = false)
	public int getCleanSwitch() {
		return this.cleanSwitch;
	}

	public void setCleanSwitch(int cleanSwitch) {
		this.cleanSwitch = cleanSwitch;
	}

	@Column(name = "Valid", nullable = false, length = 1)
	public String getValid() {
		return this.valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	@Column(name = "Remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gprs")
	public Set<Meter> getMeters() {
		return this.meters;
	}

	public void setMeters(Set<Meter> meters) {
		this.meters = meters;
	}

}
