package com.xdkj.yccb.main.charge.dto;

import java.math.BigDecimal;

import net.sf.jasperreports.engine.JRDataSource;

public class QYCharge {
	
	private String customerName;
	private String c_num;
	private JRDataSource meters;
	private JRDataSource detail;
	private BigDecimal sumdemoney;
	
	public BigDecimal getSumdemoney() {
		return sumdemoney;
	}

	public void setSumdemoney(BigDecimal sumdemoney) {
		this.sumdemoney = sumdemoney;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getC_num() {
		return c_num;
	}

	public void setC_num(String c_num) {
		this.c_num = c_num;
	}

	public JRDataSource getMeters() {
		return meters;
	}

	public void setMeters(JRDataSource meters) {
		this.meters = meters;
	}

	public JRDataSource getDetail() {
		return detail;
	}

	public void setDetail(JRDataSource detail) {
		this.detail = detail;
	}

	public QYCharge() {
		super();
	}
	
	
}
