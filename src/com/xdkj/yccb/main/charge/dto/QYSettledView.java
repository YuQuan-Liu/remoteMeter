package com.xdkj.yccb.main.charge.dto;

public class QYSettledView {

	private int c_id;
	private int m_id;
	private int mdl_id;
	private String c_num;
	private String customerName;
	private String kj;

	private int pkid;
	private int lastderead;
	private int meterread;
	private int changend;
	
	private int gy; //工业
	private int jz; //建筑
	private int jm; //居民
	private String remark;

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getGy() {
		return gy;
	}

	public void setGy(int gy) {
		this.gy = gy;
	}

	public int getJz() {
		return jz;
	}

	public void setJz(int jz) {
		this.jz = jz;
	}

	public int getJm() {
		return jm;
	}

	public void setJm(int jm) {
		this.jm = jm;
	}

	public int getMdl_id() {
		return mdl_id;
	}

	public void setMdl_id(int mdl_id) {
		this.mdl_id = mdl_id;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public String getKj() {
		return kj;
	}

	public void setKj(String kj) {
		this.kj = kj;
	}

	public int getLastderead() {
		return lastderead;
	}

	public void setLastderead(int lastderead) {
		this.lastderead = lastderead;
	}

	public int getMeterread() {
		return meterread;
	}

	public void setMeterread(int meterread) {
		this.meterread = meterread;
	}

	public int getChangend() {
		return changend;
	}

	public void setChangend(int changend) {
		this.changend = changend;
	}

	public int getPkid() {
		return pkid;
	}

	public void setPkid(int pkid) {
		this.pkid = pkid;
	}

	public QYSettledView() {
		super();
	}
	
	
	
}
