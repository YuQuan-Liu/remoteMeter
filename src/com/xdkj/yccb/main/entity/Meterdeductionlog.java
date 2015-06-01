package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Meterdeductionlog generated by hbm2java
 */
@Entity
@Table(name = "meterdeductionlog", catalog = "remotemeter")
public class Meterdeductionlog implements java.io.Serializable {

	private Integer pid;
	private Settlelog settlelog;
	private Pricekind pricekind;
	private Meter meter;
	private int meterRead;
	private Date meterReadTime;
	private int lastDeRead;
	private Date lastDeTime;
	private BigDecimal deMoney;
	private Integer settleSingleId;
	private Date actionTime;
	private char valid;
	private String remark;
	
	private char paytype;
	private char payed;
	private char printed;
	private Integer changend;
	
	public Meterdeductionlog() {
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
	@JoinColumn(name = "SettleLogID", nullable = false)
	public Settlelog getSettlelog() {
		return this.settlelog;
	}

	public void setSettlelog(Settlelog settlelog) {
		this.settlelog = settlelog;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PriceKindID", nullable = false)
	public Pricekind getPricekind() {
		return this.pricekind;
	}

	public void setPricekind(Pricekind pricekind) {
		this.pricekind = pricekind;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MeterID", nullable = false)
	public Meter getMeter() {
		return this.meter;
	}

	public void setMeter(Meter meter) {
		this.meter = meter;
	}

	@Column(name = "MeterRead", nullable = false)
	public int getMeterRead() {
		return this.meterRead;
	}

	public void setMeterRead(int meterRead) {
		this.meterRead = meterRead;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MeterReadTime", nullable = false, length = 19)
	public Date getMeterReadTime() {
		return this.meterReadTime;
	}

	public void setMeterReadTime(Date meterReadTime) {
		this.meterReadTime = meterReadTime;
	}

	@Column(name = "LastDeRead", nullable = false)
	public int getLastDeRead() {
		return this.lastDeRead;
	}

	public void setLastDeRead(int lastDeRead) {
		this.lastDeRead = lastDeRead;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LastDeTime", nullable = false, length = 19)
	public Date getLastDeTime() {
		return this.lastDeTime;
	}

	public void setLastDeTime(Date lastDeTime) {
		this.lastDeTime = lastDeTime;
	}

	@Column(name = "DeMoney", nullable = false, scale = 4)
	public BigDecimal getDeMoney() {
		return this.deMoney;
	}

	public void setDeMoney(BigDecimal deMoney) {
		this.deMoney = deMoney;
	}

	@Column(name = "SettleSingleID")
	public Integer getSettleSingleId() {
		return this.settleSingleId;
	}

	public void setSettleSingleId(Integer settleSingleId) {
		this.settleSingleId = settleSingleId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ActionTime", nullable = false, length = 19)
	public Date getActionTime() {
		return this.actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	@Column(name = "Valid", nullable = false, length = 1)
	public char getValid() {
		return this.valid;
	}

	public void setValid(char valid) {
		this.valid = valid;
	}

	@Column(name = "paytype", length = 1)
	public char getPaytype() {
		return paytype;
	}

	public void setPaytype(char paytype) {
		this.paytype = paytype;
	}

	@Column(name = "payed", length = 1)
	public char getPayed() {
		return payed;
	}

	public void setPayed(char payed) {
		this.payed = payed;
	}

	@Column(name = "printed", length = 1)
	public char getPrinted() {
		return printed;
	}

	public void setPrinted(char printed) {
		this.printed = printed;
	}

	@Column(name = "changend")
	public Integer getChangend() {
		return changend;
	}

	public void setChangend(Integer changend) {
		this.changend = changend;
	}
	
	@Column(name = "Remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
