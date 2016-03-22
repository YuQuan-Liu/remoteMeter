package com.xdkj.yccb.main.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_paras",catalog = "remotemeter")
public class SysPara {

	private int pid;
	private String name;
	private String value;
	private String remark;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PID", nullable = false)
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "value", nullable = false, length = 500)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Column(name = "remark", nullable = false, length = 500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SysPara() {
		super();
	}
	
	
}
