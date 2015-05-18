package com.xdkj.yccb.main.infoin.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CustomerView {
	private Integer pid;
	private String n_name;
	private String n_id;
	private String hk;
	private String hk_id;
	private String apid;
	private String customerId;
	private String customerName;
	private String customerMobile;
	private String customerEmail;
	private String nationalId;
	private String customerAddr;
	private String c_num;
	private BigDecimal customerBalance;
	private byte prePaySign;
	private int warnSwitch;
	private int warnStyle;
	private int warnThre;
//	private String valid;
//	private String remark;
	
	private String louNum;
	private String dyNum;
	private String huNum;
	
	
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
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getHk() {
		return hk;
	}
	public void setHk(String hk) {
		this.hk = hk;
	}
	public String getHk_id() {
		return hk_id;
	}
	public void setHk_id(String hk_id) {
		this.hk_id = hk_id;
	}
	public String getC_num() {
		return c_num;
	}
	public void setC_num(String c_num) {
		this.c_num = c_num;
	}
	public String getN_id() {
		return n_id;
	}
	public void setN_id(String n_id) {
		this.n_id = n_id;
	}
	public String getN_name() {
		return n_name;
	}
	public void setN_name(String n_name) {
		this.n_name = n_name;
	}
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
//	public String getValid() {
//		return valid;
//	}
//	public void setValid(String valid) {
//		this.valid = valid;
//	}
//	public String getRemark() {
//		return remark;
//	}
//	public void setRemark(String remark) {
//		this.remark = remark;
//	}
	public CustomerView() {
		super();
	}
	@Override
	public String toString() {
		return "[pid=" + pid + ", n_name=" + n_name + ", n_id="
				+ n_id + ", hk=" + hk + ", hk_id=" + hk_id + ", apid=" + apid
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", customerMobile=" + customerMobile
				+ ", customerEmail=" + customerEmail + ", nationalId="
				+ nationalId + ", customerAddr=" + customerAddr + ", c_num="
				+ c_num + ", customerBalance=" + customerBalance
				+ ", prePaySign=" + prePaySign + ", warnSwitch=" + warnSwitch
				+ ", warnStyle=" + warnStyle + ", warnThre=" + warnThre+ "]";
	}
	
	public Map<String,String> check_view(){
		HashMap<String, String> result = new HashMap<>();
		result.put("success", "true");
		
		String[] ldh = c_num.split("[ ,.-]");
		if(ldh.length == 3){
			louNum = ldh[0];
			dyNum = ldh[1];
			huNum = ldh[2];
		}else{
			result.put("success", "false");
			result.put("c_num", "用户号错误");
		}
		
		return result;
	}
	
}
