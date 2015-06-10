package com.xdkj.yccb.main.sys.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;

public interface AuthorityDAO {
	/**
	 * 通过ppid 获取权限集合
	 * @param ppid
	 * @return
	 */
	List<Authority> getList( int ppid);
	/**
	 * 获取所有的权限
	 * @return
	 */
	List<Authority> getListAll();
	/**
	 * 获取角色下所有  有效的权限
	 * @param pid
	 * @return
	 */
	List<Authority> getAuthsByRole(int pid);
	

}
