package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.entity.Admininfo;

public interface AdministratorService {
	
	/**
	 * 获取自来水公司下的管理员列表
	 * @return
	 */
	List<AdminInfoView> getList(int wcid);
	/**
	 * 获取总记录数
	 * @param adInfo
	 * @return
	 */
	int getTotalCount(Admininfo adInfo);
	/**
	 * 通过id获取管理员信息
	 * @param adminId
	 * @return
	 */
	Admininfo getById(String adminId);
	/**
	 * 添加管理员
	 * @param adminInfo
	 * @return id
	 */
	String addAdmin(Admininfo adminInfo,int roleid);
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
	 * 通过登录帐号和密码
	 * @param loginName
	 * @param password
	 * @return
	 */
	Admininfo getByLoginName(String loginName,String password);
	/**
	 * 检测登录名是否存在
	 * @param name
	 * @return
	 */
	String checkLoginName(String name);
	/**
	 * 获取管理员对应的  view  更新用户时用
	 * @param pid
	 * @return
	 */
	AdminInfoView getAdminViewById(int pid);
	/**
	 * 修改密码
	 * @param pid
	 * @param old_
	 * @param new_
	 * @return
	 */
	String changepwd(int pid, String old_, String new_);
	/**
	 * 重置密码
	 * @param pid
	 * @return
	 */
	String resetpwd(int pid);
	/**
	 * 修改角色
	 * @param pid
	 * @param rid
	 * @return
	 */
	String changerole(int pid, int rid);
	/**
	 * 修改片区
	 * @param pid
	 * @param did
	 * @return
	 */
	String changedep(int pid, int did);
	

}
