package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.WaterCompanyView;
import com.xdkj.yccb.main.entity.Watercompany;

public interface WaterCompanyService {
	/**
	 * 获取列表信息  获取系统中所有的自来水信息
	 * @param watcom
	 * @param pageInfo
	 * @return
	 */
	List<WaterCompanyView> getList(Watercompany watcom ,PageBase pageInfo);
	/**
	 * 获取总记录数
	 * @param watcom
	 * @return
	 */
	int getTotalCount(Watercompany watcom);
	/**
	 * 添加自来水公司
	 * @param watcom
	 * @return
	 */
	String addWatcom( Watercompany watcom);
	
	Watercompany getById(String pid);
	/**
	 * 更新自来水公司信息
	 * @param watcom
	 * @return
	 */
	String update(Watercompany watcom);
}
