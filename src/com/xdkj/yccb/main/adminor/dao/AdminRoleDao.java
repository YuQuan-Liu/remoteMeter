package com.xdkj.yccb.main.adminor.dao;

import com.xdkj.yccb.main.entity.AdminRole;

public interface AdminRoleDao {

	int addAdminRole(AdminRole adminrole);

	String updateRole(int pid, int rid);

}
