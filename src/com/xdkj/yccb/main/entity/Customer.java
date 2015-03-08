package com.xdkj.yccb.main.entity;

// Generated 2015-3-6 17:30:45 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
 * Customer generated by hbm2java
 */
@Entity
@Table(name = "customer", catalog = "remotemeter")
public class Customer implements java.io.Serializable {

	private Integer pid;
	private Neighbor neighbor;
	private Housekind housekind;
	private String apid;
	private String customerId;
	private String customerName;
	private String loginName;
	private String loginKey;
	private String customerMobile;
	private String customerEmail;
	private String nationalId;
	private String customerAddr;
	private String louNum;
	private String dynum;
	private String huNum;
	private BigDecimal customerBalance;
	private byte prePaySign;
	private int warnSwitch;
	private int warnStyle;
	private int warnThre;
	private char valid;
	private String remark;
	private Set<Meter> meters = new HashSet<Meter>(0);
	private Set<Customerpaylog> customerpaylogs = new HashSet<Customerpaylog>(0);
	private Set<Warnlog> warnlogs = new HashSet<Warnlog>(0);

	public Customer() {
	}

	public Customer(Neighbor neighbor, Housekind housekind, String customerId,
			String loginKey, String customerAddr, String louNum, String dynum,
			String huNum, byte prePaySign, int warnSwitch, int warnStyle,
			int warnThre, char valid) {
		this.neighbor = neighbor;
		this.housekind = housekind;
		this.customerId = customerId;
		this.loginKey = loginKey;
		this.customerAddr = customerAddr;
		this.louNum = louNum;
		this.dynum = dynum;
		this.huNum = huNum;
		this.prePaySign = prePaySign;
		this.warnSwitch = warnSwitch;
		this.warnStyle = warnStyle;
		this.warnThre = warnThre;
		this.valid = valid;
	}

	public Customer(Neighbor neighbor, Housekind housekind, String apid,
			String customerId, String customerName, String loginName,
			String loginKey, String customerMobile, String customerEmail,
			String nationalId, String customerAddr, String louNum,
			String dynum, String huNum, BigDecimal customerBalance,
			byte prePaySign, int warnSwitch, int warnStyle, int warnThre,
			char valid, String remark, Set<Meter> meters,
			Set<Customerpaylog> customerpaylogs, Set<Warnlog> warnlogs) {
		this.neighbor = neighbor;
		this.housekind = housekind;
		this.apid = apid;
		this.customerId = customerId;
		this.customerName = customerName;
		this.loginName = loginName;
		this.loginKey = loginKey;
		this.customerMobile = customerMobile;
		this.customerEmail = customerEmail;
		this.nationalId = nationalId;
		this.customerAddr = customerAddr;
		this.louNum = louNum;
		this.dynum = dynum;
		this.huNum = huNum;
		this.customerBalance = customerBalance;
		this.prePaySign = prePaySign;
		this.warnSwitch = warnSwitch;
		this.warnStyle = warnStyle;
		this.warnThre = warnThre;
		this.valid = valid;
		this.remark = remark;
		this.meters = meters;
		this.customerpaylogs = customerpaylogs;
		this.warnlogs = warnlogs;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HouseKindID", nullable = false)
	public Housekind getHousekind() {
		return this.housekind;
	}

	public void setHousekind(Housekind housekind) {
		this.housekind = housekind;
	}

	@Column(name = "APID", length = 20)
	public String getApid() {
		return this.apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
	}

	@Column(name = "CustomerID", nullable = false, length = 10)
	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CustomerName", length = 40)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "LoginName", length = 20)
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

	@Column(name = "CustomerMobile", length = 20)
	public String getCustomerMobile() {
		return this.customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	@Column(name = "CustomerEmail", length = 80)
	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Column(name = "NationalID", length = 20)
	public String getNationalId() {
		return this.nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	@Column(name = "CustomerAddr", nullable = false, length = 80)
	public String getCustomerAddr() {
		return this.customerAddr;
	}

	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}

	@Column(name = "LouNum", nullable = false, length = 10)
	public String getLouNum() {
		return this.louNum;
	}

	public void setLouNum(String louNum) {
		this.louNum = louNum;
	}

	@Column(name = "DYNum", nullable = false, length = 10)
	public String getDynum() {
		return this.dynum;
	}

	public void setDynum(String dynum) {
		this.dynum = dynum;
	}

	@Column(name = "HuNum", nullable = false, length = 10)
	public String getHuNum() {
		return this.huNum;
	}

	public void setHuNum(String huNum) {
		this.huNum = huNum;
	}

	@Column(name = "CustomerBalance", scale = 4)
	public BigDecimal getCustomerBalance() {
		return this.customerBalance;
	}

	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}

	@Column(name = "PrePaySign", nullable = false)
	public byte getPrePaySign() {
		return this.prePaySign;
	}

	public void setPrePaySign(byte prePaySign) {
		this.prePaySign = prePaySign;
	}

	@Column(name = "WarnSwitch", nullable = false)
	public int getWarnSwitch() {
		return this.warnSwitch;
	}

	public void setWarnSwitch(int warnSwitch) {
		this.warnSwitch = warnSwitch;
	}

	@Column(name = "WarnStyle", nullable = false)
	public int getWarnStyle() {
		return this.warnStyle;
	}

	public void setWarnStyle(int warnStyle) {
		this.warnStyle = warnStyle;
	}

	@Column(name = "WarnThre", nullable = false)
	public int getWarnThre() {
		return this.warnThre;
	}

	public void setWarnThre(int warnThre) {
		this.warnThre = warnThre;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Meter> getMeters() {
		return this.meters;
	}

	public void setMeters(Set<Meter> meters) {
		this.meters = meters;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Customerpaylog> getCustomerpaylogs() {
		return this.customerpaylogs;
	}

	public void setCustomerpaylogs(Set<Customerpaylog> customerpaylogs) {
		this.customerpaylogs = customerpaylogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	public Set<Warnlog> getWarnlogs() {
		return this.warnlogs;
	}

	public void setWarnlogs(Set<Warnlog> warnlogs) {
		this.warnlogs = warnlogs;
	}

}
