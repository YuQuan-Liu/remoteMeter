package com.xdkj.yccb.main.infoin.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Housekind;
import com.xdkj.yccb.main.entity.Meterkind;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Pricekind;

public class CustomerMeter {

	private int hkid;
	private String c_apid;
	private String customerName;
	private String customerMobile;
	private String customerEmail;
	private String nationalId;
	private String customerAddr;
	private String louNum;
	private String dyNum;
	private String huNum;
	private BigDecimal customerBalance;
	private byte prePaySign;
	private int warnSwitch;
	private int warnStyle;
	private int warnThre;
	private int pkid;
	private int mkid;
	private String m_apid;
	private String steelNum;
	private String qfh;
	private String collectorAddr;
	private String meterAddr;
	private byte meterSolid;
	private char lihu;
	private int mainMeter;
	private int suppleMode;
	private int isValve;
	private Integer deductionStyle;
	private Integer valveOffthre;
	private int timerSwitch;
	private String timer;
	private Integer overflow;
	public int getHkid() {
		return hkid;
	}
	public void setHkid(int hkid) {
		this.hkid = hkid;
	}
	public String getC_apid() {
		return c_apid;
	}
	public void setC_apid(String c_apid) {
		this.c_apid = c_apid;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getNationalId() {
		return nationalId;
	}
	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}
	public String getCustomerAddr() {
		return customerAddr;
	}
	public void setCustomerAddr(String customerAddr) {
		this.customerAddr = customerAddr;
	}
	public String getLouNum() {
		return louNum;
	}
	public void setLouNum(String louNum) {
		this.louNum = louNum;
	}
	public String getDyNum() {
		return dyNum;
	}
	public void setDyNum(String dyNum) {
		this.dyNum = dyNum;
	}
	public String getHuNum() {
		return huNum;
	}
	public void setHuNum(String huNum) {
		this.huNum = huNum;
	}
	public BigDecimal getCustomerBalance() {
		return customerBalance;
	}
	public void setCustomerBalance(BigDecimal customerBalance) {
		this.customerBalance = customerBalance;
	}
	public byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public int getWarnSwitch() {
		return warnSwitch;
	}
	public void setWarnSwitch(int warnSwitch) {
		this.warnSwitch = warnSwitch;
	}
	public int getWarnStyle() {
		return warnStyle;
	}
	public void setWarnStyle(int warnStyle) {
		this.warnStyle = warnStyle;
	}
	public int getWarnThre() {
		return warnThre;
	}
	public void setWarnThre(int warnThre) {
		this.warnThre = warnThre;
	}
	public int getPkid() {
		return pkid;
	}
	public void setPkid(int pkid) {
		this.pkid = pkid;
	}
	public int getMkid() {
		return mkid;
	}
	public void setMkid(int mkid) {
		this.mkid = mkid;
	}
	public String getM_apid() {
		return m_apid;
	}
	public void setM_apid(String m_apid) {
		this.m_apid = m_apid;
	}
	public String getSteelNum() {
		return steelNum;
	}
	public void setSteelNum(String steelNum) {
		this.steelNum = steelNum;
	}
	public String getQfh() {
		return qfh;
	}
	public void setQfh(String qfh) {
		this.qfh = qfh;
	}
	public String getCollectorAddr() {
		return collectorAddr;
	}
	public void setCollectorAddr(String collectorAddr) {
		this.collectorAddr = collectorAddr;
	}
	public String getMeterAddr() {
		return meterAddr;
	}
	public void setMeterAddr(String meterAddr) {
		this.meterAddr = meterAddr;
	}
	public byte getMeterSolid() {
		return meterSolid;
	}
	public void setMeterSolid(byte meterSolid) {
		this.meterSolid = meterSolid;
	}
	public char getLihu() {
		return lihu;
	}
	public void setLihu(char lihu) {
		this.lihu = lihu;
	}
	public int getMainMeter() {
		return mainMeter;
	}
	public void setMainMeter(int mainMeter) {
		this.mainMeter = mainMeter;
	}
	public int getSuppleMode() {
		return suppleMode;
	}
	public void setSuppleMode(int suppleMode) {
		this.suppleMode = suppleMode;
	}
	public int getIsValve() {
		return isValve;
	}
	public void setIsValve(int isValve) {
		this.isValve = isValve;
	}
	public Integer getDeductionStyle() {
		return deductionStyle;
	}
	public void setDeductionStyle(Integer deductionStyle) {
		this.deductionStyle = deductionStyle;
	}
	public Integer getValveOffthre() {
		return valveOffthre;
	}
	public void setValveOffthre(Integer valveOffthre) {
		this.valveOffthre = valveOffthre;
	}
	public int getTimerSwitch() {
		return timerSwitch;
	}
	public void setTimerSwitch(int timerSwitch) {
		this.timerSwitch = timerSwitch;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public Integer getOverflow() {
		return overflow;
	}
	public void setOverflow(Integer overflow) {
		this.overflow = overflow;
	}
	
}
