package com.xdkj.yccb.main.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "nonremoteexport", catalog = "remotemeter")
public class NonRemoteExport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pid;
	private String clazzup;
	private String clazzdown1;
	private String clazzdown2;
	private String exportname;
	private Watercompany watercompany;
	public NonRemoteExport() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PID", unique = true, nullable = false)
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	@Column(name = "clazzup", nullable = false, length = 100)
	public String getClazzup() {
		return clazzup;
	}
	public void setClazzup(String clazzup) {
		this.clazzup = clazzup;
	}
	
	@Column(name = "clazzdown1", nullable = false, length = 100)
	public String getClazzdown1() {
		return clazzdown1;
	}
	public void setClazzdown1(String clazzdown1) {
		this.clazzdown1 = clazzdown1;
	}
	
	@Column(name = "clazzdown2", nullable = false, length = 100)
	public String getClazzdown2() {
		return clazzdown2;
	}
	public void setClazzdown2(String clazzdown2) {
		this.clazzdown2 = clazzdown2;
	}
	
	@Column(name = "exportname", nullable = false, length = 45)
	public String getExportname() {
		return exportname;
	}
	public void setExportname(String exportname) {
		this.exportname = exportname;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WCID", nullable = false)
	public Watercompany getWatercompany() {
		return watercompany;
	}
	public void setWatercompany(Watercompany watercompany) {
		this.watercompany = watercompany;
	}
	
	
}
