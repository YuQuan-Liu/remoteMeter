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
	List<Roles> getList(RoleView rv,PageBase pageInfo);
	
	int getTotalCount(RoleView rv,PageBase pb);
	
	int add(Roles r);
	
	void update(Roles r);
	
	void delete(Roles r);
}
