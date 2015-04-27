package com.xdkj.yccb.main.infoin.dto;

import java.util.Date;

import com.xdkj.yccb.main.entity.Neighbor;

public class GprsView {
	
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
	private String valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Neighbor getNeighbor() {
		return neighbor;
	}
	public void setNeighbor(Neighbor neighbor) {
		this.neighbor = neighbor;
	}
	public String getGprstel() {
		return gprstel;
	}
	public void setGprstel(String gprstel) {
		this.gprstel = gprstel;
	}
	public String getGprsaddr() {
		return gprsaddr;
	}
	public void setGprsaddr(String gprsaddr) {
		this.gprsaddr = gprsaddr;
	}
	public String getInstallAddr() {
		return installAddr;
	}
	public void setInstallAddr(String installAddr) {
		this.installAddr = installAddr;
	}
	public Date getInstallTime() {
		return installTime;
	}
	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}
	public String getInstallPerson() {
		return installPerson;
	}
	public void setInstallPerson(String installPerson) {
		this.installPerson = installPerson;
	}
	public int getGprsprotocol() {
		return gprsprotocol;
	}
	public void setGprsprotocol(int gprsprotocol) {
		this.gprsprotocol = gprsprotocol;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
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
