package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.main.adminor.dto.MeterkindView;

public interface MeterKindDao {

	/**
	 * 获取数据库中所有的水表类型。
	 * @param wcid
	 * @return
	 */
	public List<MeterkindView> getList();
}
