package com.xdkj.yccb.main.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dto.RoleView;

public interface RoleService {
	List<RoleView> getList(int wcid);
	
	int getCount(RoleView rv,PageBase pb);
	
	String addRole(RoleView r,String childauth,int wcid);
	
	String delete(RoleView r);
	
	Roles getRoleInfo(String pid);
	
	String updateRole(String childauth,int pid);
	
	/**
	 * 获取全部的权限  
	 * @param request
	 * @return
	 */
	public String getAuthTreeJson(HttpServletRequest request);
	
	/**
	 * 获取角色下的权限信息
	 * @param request
	 * @return
	 */
	public String getAuthTreeJson(HttpServletRequest request,int pid);

	/**
	 * 检测自来水公司下是否有此角色名了
	 * @param name
	 * @return
	 */
	String checkname(int wcid,String name);
}
