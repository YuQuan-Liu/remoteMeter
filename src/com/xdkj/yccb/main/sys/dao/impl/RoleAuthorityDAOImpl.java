package com.xdkj.yccb.main.sys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.main.sys.dao.RoleAuthorityDAO;
@Repository
public class RoleAuthorityDAOImpl extends HibernateDAO<RoleAuthority> implements RoleAuthorityDAO {

	@Override
	public void delete(List<Integer> raIds) {
		String hql = "delete from RoleAuthority r where r.pid in (:pids)";
		Query q = getSession().createQuery(hql);
		q.setParameterList("pids", raIds);
		q.executeUpdate();
	}

}
