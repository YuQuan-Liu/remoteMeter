package com.xdkj.yccb.main.sys.dao;

import java.util.List;

public interface RoleAuthorityDAO {
	/**
	 * 批量删除角色权限对应表
	 * @param raIds
	 */
	void delete(List<Integer> raIds);

}
