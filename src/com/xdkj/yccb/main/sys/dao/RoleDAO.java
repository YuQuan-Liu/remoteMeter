package com.xdkj.yccb.main.sys.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dto.RoleView;

public interface RoleDAO {
	/**
	 * 获取角色列表
	 * @param r
	 * @param pageInfo
	 * @return
	 */
	List<Roles> getList(int wcid);
	
	int getTotalCount(RoleView rv,PageBase pb);
	
	int add(Roles r);
	
	void update(Roles r);
	
	void delete(Roles r);
	
	Roles getById(int pid);

	/**
	 * 检测此自来水下是否有此角色名了
	 * @param wcid
	 * @param name
	 * @return
	 */
	String checkname(int wcid, String name);

	/**
	 * 获取系统默认的权限
	 * @return
	 */
	List<Roles> getSystemList();
}
