package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;
import java.util.Date;
public class CustompaylogView {
	private Integer pid;
	private String custNo;//用户号
	private String CustId;//用户id
	private String custName;//用户名
	private String custAddr;//地址
	private String adminName;//操作员
	private BigDecimal amount;
	private Date actionTime;
	private byte prePaySign;
	private String valid;
	private String remark;
	public CustompaylogView() {
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustId() {
		return CustId;
	}
	public void setCustId(String custId) {
		CustId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getActionTime() {
		return actionTime;
	}
	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
	public byte getPrePaySign() {
		return prePaySign;
	}
	public void setPrePaySign(byte prePaySign) {
		this.prePaySign = prePaySign;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCustAddr() {
		return custAddr;
	}
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	
}
