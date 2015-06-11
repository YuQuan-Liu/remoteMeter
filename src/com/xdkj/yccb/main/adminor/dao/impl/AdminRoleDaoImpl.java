package com.xdkj.yccb.main.adminor.dao.impl;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.AdminRoleDao;
import com.xdkj.yccb.main.entity.AdminRole;

@Repository
public class AdminRoleDaoImpl extends HibernateDAO<AdminRole> implements AdminRoleDao {

	@Override
	public int addAdminRole(AdminRole adminrole) {
		getHibernateTemplate().save(adminrole);
		return adminrole.getPid();
	}

}
