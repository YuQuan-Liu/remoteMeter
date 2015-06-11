package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.entity.Admininfo;

public interface AdministratorDAO {
	/**
	 * 获取自来水下的管理员列表
	 * @return
	 */
	List<Admininfo> getList(int wcid);
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
	/**
	 * 获取自来水下的管理员列表  显示管理员列表
	 * @param wcid
	 * @return
	 */
	List<AdminInfoView> getListView(int wcid);
	/**
	 * 检查登录名是否存在
	 * @param name
	 * @return
	 */
	String checkLoginName(String name);
	/**
	 * 管理员对应的view
	 * @param pid
	 * @return
	 */
	AdminInfoView getViewByid(int pid);
	/**
	 * 检测旧密码
	 * @param pid
	 * @param oldpwd
	 * @return
	 */
	boolean checkoldpwd(int pid, String oldpwd);
	/**
	 * 更新密码
	 * @param pid
	 * @param newpwd
	 * @return
	 */
	boolean updatePwd(int pid, String newpwd);
	/**
	 * 重置密码
	 * @param pid
	 * @return
	 */
	String resetpwd(int pid);
	String updateDep(int pid, int did);
	

}
