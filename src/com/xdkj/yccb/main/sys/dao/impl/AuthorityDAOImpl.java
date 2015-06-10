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
	public int add(Authority au) {
		this.getHibernateTemplate().save(au);
		return au.getPid();
	}

	@Override
	public void update(Authority au) {
		this.getHibernateTemplate().update(au);
	}

	@Override
	public void delete(Integer[] ids) {
		Session s = getSession();
		String hql = "update Authority a set a.valid='0' where a.pid in (:pid) ";
		Query q = s.createQuery(hql);
		q.setParameterList("pid", ids);
		q.executeUpdate();
		releaseSession(s);
	}

	@Override
	public List<Authority> getAllList(Authority au, PageBase pageInfo) {
		String hql = "from Authority a where a.valid='1' ";
		Session session = getSession();
		Query q = session.createQuery(hql);
		q.setFirstResult((pageInfo.getPage()-1)*pageInfo.getRows());
		q.setMaxResults(pageInfo.getRows());
		return q.list();
	}

	@Override
	public int getTotalCount(Authority au) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Authority a where  a.valid='1' ");
		//if(null!=adInfo.getAdminName()){
		//	sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		//}
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public List<Authority> getAuthByPpid(Integer ppId) {
		String hql = "from Authority a where a.ppid=:ppid";
		Query q = getSession().createQuery(hql);
		q.setInteger("ppid", ppId);
		return q.list();
	}

	@Override
	public Authority getById(int auId) {
		String hql = "from Authority a where a.pid=:pid";
		return (Authority) getSession().createQuery(hql).setParameter("pid", auId).uniqueResult();
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
