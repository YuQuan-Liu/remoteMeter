package com.xdkj.yccb.main.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sms_template",catalog="remotemeter")
public class SMSTemplate implements Serializable{

	private int pid;
	private String cid;
	private String content;
	private int para_cnt;
	private Watercompany watercompany;
	private int qf;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PID", nullable = false)
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Column(name = "cid", nullable = false, length = 45)
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	@Column(name = "content", nullable = false, length = 500)
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "para_cnt", nullable = false)
	public int getPara_cnt() {
		return para_cnt;
	}
	public void setPara_cnt(int para_cnt) {
		this.para_cnt = para_cnt;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WCID", nullable = false)
	public Watercompany getWatercompany() {
		return watercompany;
	}
	public void setWatercompany(Watercompany watercompany) {
		this.watercompany = watercompany;
	}
	
	@Column(name = "qf", nullable = false)
	public int getQf() {
		return qf;
	}
	public void setQf(int qf) {
		this.qf = qf;
	}
	public SMSTemplate() {
	}
	
	
}
