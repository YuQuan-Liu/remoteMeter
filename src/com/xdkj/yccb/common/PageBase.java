package com.xdkj.yccb.common;

public class PageBase {

	private int page;//当前页
	private int totalPage;//总页数
	private int rows;//每页多少条
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	

}
