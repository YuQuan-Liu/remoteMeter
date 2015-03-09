package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Admininfo;

public interface AdministratorDAO {
	/**
	 * 获取管理员列表
	 * @return
	 */
	List<Admininfo> getList(Admininfo adInfo,PageBase pageInfo);
	/**
	 * 获取总记录数
	 * @param adInfo
	 * @return
	 */
	Integer getTotalCount(Admininfo adInfo);
	/**
	 * 通过id获取管理员信息
	 * @param adminId
	 * @return
	 */
	Admininfo getById(Integer adminId);
	/**
	 * 添加管理员
	 * @param adminInfo
	 * @return id
	 */
	int addAdmin(Admininfo adminInfo);
	/**
	 * 通过id删除管理员
	 * @param adminId
	 * @return
	 */
	boolean removeById(Integer adminId);
	/**
	 * 更新管理员信息
	 * @param adminInfo
	 * @return
	 */
	boolean update (Admininfo adminInfo);
	/**
	 * 获取账户信息
	 * @param loginName
	 * @param password
	 * @return
	 */
	Admininfo getByLoginName(String loginName,String password);
	

}
