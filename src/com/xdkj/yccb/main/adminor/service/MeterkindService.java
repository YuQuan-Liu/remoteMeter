package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.main.adminor.dto.MeterkindView;

public interface MeterkindService {

	/**
	 * 获取数据库中所有的水表类型
	 * @return
	 */
	public List<MeterkindView> getList() ;
}
