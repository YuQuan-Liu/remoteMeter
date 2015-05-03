package com.xdkj.yccb.main.adminor.dto;

import java.math.BigDecimal;

public class BasicpriceView {
	private Integer pid;
	//private Pricekind pricekind;
	private String basicPriceName;
	private BigDecimal basicPriceFirst;
	private int basicFirstOver;
	private BigDecimal basicPriceSecond;
	private int basicSecondOver;
	private BigDecimal basicPriceThird;
	private String valid;
	private String remark;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getBasicPriceName() {
		return basicPriceName;
	}
	public void setBasicPriceName(String basicPriceName) {
		this.basicPriceName = basicPriceName;
	}
	public BigDecimal getBasicPriceFirst() {
		return basicPriceFirst;
	}
	public void setBasicPriceFirst(BigDecimal basicPriceFirst) {
		this.basicPriceFirst = basicPriceFirst;
	}
	public int getBasicFirstOver() {
		return basicFirstOver;
	}
	public void setBasicFirstOver(int basicFirstOver) {
		this.basicFirstOver = basicFirstOver;
	}
	public BigDecimal getBasicPriceSecond() {
		return basicPriceSecond;
	}
	public void setBasicPriceSecond(BigDecimal basicPriceSecond) {
		this.basicPriceSecond = basicPriceSecond;
	}
	public int getBasicSecondOver() {
		return basicSecondOver;
	}
	public void setBasicSecondOver(int basicSecondOver) {
		this.basicSecondOver = basicSecondOver;
	}
	public BigDecimal getBasicPriceThird() {
		return basicPriceThird;
	}
	public void setBasicPriceThird(BigDecimal basicPriceThird) {
		this.basicPriceThird = basicPriceThird;
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
