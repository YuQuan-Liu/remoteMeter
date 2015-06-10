package com.xdkj.yccb.main.sys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.sys.dao.AuthorityDAO;
@Repository
public class AuthorityDAOImpl extends HibernateDAO<Authority>implements AuthorityDAO {

	@Override
	public List<Authority> getList(int ppid) {
		Session s = getSession();
		String hql = "from Authority a where a.valid='1' " +
				" and a.ppid=:ppid ";
		Query q = s.createQuery(hql);
		q.setInteger("ppid", ppid);
		return q.list();
	}
	
	@Override
	public List<Authority> getListAll() {
		String sql = "select aa.* from authority a " +
				"join authority aa " +
				"on a.pid = aa.ppid " +
				"order by aa.ppid,aa.pid ";
		Query q = getSession().createSQLQuery(sql).addEntity(Authority.class);
		return q.list();
	}

	@Override
	public List<Authority> getAuthsByRole(int pid) {
		String sql = "select a.* from role_authority r " +
				"join authority a " +
				"on r.AuthorityID = a.PID " +
				"where r.RoleID = :roleid ";
		Query q = getSession().createSQLQuery(sql).addEntity(Authority.class).setInteger("roleid", pid);
		return q.list();
	} 

}
