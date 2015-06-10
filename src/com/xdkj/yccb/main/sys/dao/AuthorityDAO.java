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
	 * 获取列表
	 * @param au
	 * @param pageInfo
	 * @return
	 */
	List<Authority> getAllList(Authority au,PageBase pageInfo);
	
	List<Authority> getAuthByPpid(Integer ppId);
	
	int getTotalCount(Authority au);
	/**
	 * 新增
	 * @param au
	 * @return
	 */
	int add(Authority au);
	/**
	 * 修改
	 * @param au
	 */
	void update(Authority au);
	/**
	 * 删除 批量
	 * @param ids
	 */
	void delete( Integer [] ids);
	Authority getById(int auId);
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
