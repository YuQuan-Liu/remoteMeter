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
@Table(name = "remoteexport", catalog = "remotemeter")
public class RemoteExport implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int pid;
	private String clazz;
	private String exportName;
	private Watercompany watercompany;
	
	public RemoteExport() {
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

	@Column(name = "clazz", nullable = false, length = 100)
	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	@Column(name = "exportname", nullable = false, length = 45)
	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
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
