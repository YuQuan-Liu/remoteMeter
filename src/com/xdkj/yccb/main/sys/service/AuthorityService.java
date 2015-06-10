package com.xdkj.yccb.main.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.sys.dto.AuthorityView;

public interface AuthorityService {
	/**
	 * 获取所有权限
	 * @return jsonstring显示为树
	 */
	List<AuthorityView> getList(Authority au,PageBase pageInfo);
	int getTotalCount(Authority au);
	/**
	 * 获取父级权限菜单
	 * @return
	 */
	List<AuthorityView> getParentAuth();
	/**
	 * 获取权限信息
	 * @param auId
	 * @return
	 */
	Authority getById(String auId);
	/**
	 * 添加权限
	 * @param au
	 * @return succ 或 fail
	 */
	String add(Authority au);
	/**
	 * 更新权限
	 * @param au
	 * @return succ 或 fail
	 */
	String update(Authority au);
	/**
	 * 删除权限
	 * @param ids
	 * @return
	 */
	String delete(String ids);
	/**
	 * 获取权限树
	 * @param request
	 * @param roleId
	 * @return
	 */
	String getAuthTreeJson(HttpServletRequest request);
	

}
