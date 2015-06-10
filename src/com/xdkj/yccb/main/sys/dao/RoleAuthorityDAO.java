package com.xdkj.yccb.main.sys.dao;

import java.util.List;

public interface RoleAuthorityDAO {
	

	/**
	 * 给指定pid   添加add里的权限
	 * @param pid
	 * @param add
	 */
	void add(int pid, List<String> add);

	/**
	 * 删除pid   delete 中的权限
	 * @param pid
	 * @param delete
	 */
	void delete(int pid, List<String> delete);

}
