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

	@Override
	public String updateRole(int pid, int rid) {
		String hql = "update AdminRole a set a.roles.pid= :rid where a.admininfo.pid = :pid";
		if(getSession().createQuery(hql).setInteger("pid", pid).setInteger("rid", rid).executeUpdate()>0){
			return "true";
		}
		return "false";
	}

}
