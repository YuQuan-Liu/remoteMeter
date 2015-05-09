package com.xdkj.yccb.main.adminor.dto;

public class MeterkindView {
	private Integer pid;
	private String meterTypeName;
	private String meterMm;
	private int remote;
	private Integer handStyle;
	private char valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getMeterTypeName() {
		return meterTypeName;
	}
	public void setMeterTypeName(String meterTypeName) {
		this.meterTypeName = meterTypeName;
	}
	public String getMeterMm() {
		return meterMm;
	}
	public void setMeterMm(String meterMm) {
		this.meterMm = meterMm;
	}
	public int getRemote() {
		return remote;
	}
	public void setRemote(int remote) {
		this.remote = remote;
	}
	public Integer getHandStyle() {
		return handStyle;
	}
	public void setHandStyle(Integer handStyle) {
		this.handStyle = handStyle;
	}
	public char getValid() {
		return valid;
	}
	public void setValid(char valid) {
		this.valid = valid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public MeterkindView() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
