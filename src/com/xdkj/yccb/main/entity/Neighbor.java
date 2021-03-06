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
 * Neighbor generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "neighbor", catalog = "remotemeter")
public class Neighbor implements java.io.Serializable {

	private Integer pid;
	private Watercompany watercompany;
	private String neighborName;
	private String neighborAddr;
	private int mainMeter;
	private Integer timerSwitch;
	private String timer;
	private String ip;
	private String valid;
	private String remark;
	private Set<Gprs> gprses = new HashSet<Gprs>(0);
	private Set<Customer> customers = new HashSet<Customer>(0);
	private Set<Propertyresp> propertyresps = new HashSet<Propertyresp>(0);
	private Set<Detaildepart> detaildeparts = new HashSet<Detaildepart>(0);
	private Set<Wastelog> wastelogs = new HashSet<Wastelog>(0);
	public Neighbor() {
	}
	
	public Neighbor(Integer pid) {
		this.pid = pid;
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

	@Column(name = "NeighborName", nullable = false, length = 20)
	public String getNeighborName() {
		return this.neighborName;
	}

	public void setNeighborName(String neighborName) {
		this.neighborName = neighborName;
	}

	@Column(name = "NeighborAddr", length = 80)
	public String getNeighborAddr() {
		return this.neighborAddr;
	}

	public void setNeighborAddr(String neighborAddr) {
		this.neighborAddr = neighborAddr;
	}

	@Column(name = "MainMeter", nullable = false)
	public int getMainMeter() {
		return this.mainMeter;
	}

	public void setMainMeter(int mainMeter) {
		this.mainMeter = mainMeter;
	}

	@Column(name = "TimerSwitch")
	public Integer getTimerSwitch() {
		return this.timerSwitch;
	}

	public void setTimerSwitch(Integer timerSwitch) {
		this.timerSwitch = timerSwitch;
	}

	@Column(name = "Timer", length = 20)
	public String getTimer() {
		return this.timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	@Column(name = "IP", length = 30)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbor")
	public Set<Gprs> getGprses() {
		return this.gprses;
	}

	public void setGprses(Set<Gprs> gprses) {
		this.gprses = gprses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbor")
	public Set<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbor")
	public Set<Propertyresp> getPropertyresps() {
		return this.propertyresps;
	}

	public void setPropertyresps(Set<Propertyresp> propertyresps) {
		this.propertyresps = propertyresps;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbor")
	public Set<Detaildepart> getDetaildeparts() {
		return this.detaildeparts;
	}

	public void setDetaildeparts(Set<Detaildepart> detaildeparts) {
		this.detaildeparts = detaildeparts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "neighbor")
	public Set<Wastelog> getWastelogs() {
		return this.wastelogs;
	}

	public void setWastelogs(Set<Wastelog> wastelogs) {
		this.wastelogs = wastelogs;
	}

}
