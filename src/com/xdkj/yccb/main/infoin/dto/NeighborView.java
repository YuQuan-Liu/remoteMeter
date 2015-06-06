package com.xdkj.yccb.main.infoin.dto;

public class NeighborView {
	private Integer pid;
//	private String watercompany;
	private String neighborName;
	private String neighborAddr;
	private int mainMeter;
	private Integer timerSwitch;
	private String timer;
	private String ip;
	private String valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
//	public String getWatercompany() {
//		return watercompany;
//	}
//	public void setWatercompany(String watercompany) {
//		this.watercompany = watercompany;
//	}
	public String getNeighborName() {
		return neighborName;
	}
	public void setNeighborName(String neighborName) {
		this.neighborName = neighborName;
	}
	public String getNeighborAddr() {
		return neighborAddr;
	}
	public void setNeighborAddr(String neighborAddr) {
		this.neighborAddr = neighborAddr;
	}
	public int getMainMeter() {
		return mainMeter;
	}
	public void setMainMeter(int mainMeter) {
		this.mainMeter = mainMeter;
	}
	public Integer getTimerSwitch() {
		return timerSwitch;
	}
	public void setTimerSwitch(Integer timerSwitch) {
		this.timerSwitch = timerSwitch;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
}
