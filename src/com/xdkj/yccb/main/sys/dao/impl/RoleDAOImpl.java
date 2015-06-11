package com.xdkj.yccb.main.sys.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dao.RoleDAO;
import com.xdkj.yccb.main.sys.dto.RoleView;
@Repository
public class RoleDAOImpl extends HibernateDAO<Roles>implements RoleDAO {

	@Override
	public List<Roles> getList(int wcid) {
		Query q = getSession().createQuery("from Roles r where r.watercompany.pid=:wcid and r.systemRole = 0 and r.valid = 1");
		q.setInteger("wcid", wcid);
		return q.list();
	}

	@Override
	public int add(Roles r) {
		this.getHibernateTemplate().save(r);
		return r.getPid();
	}

	@Override
	public void update(Roles r) {
		this.getHibernateTemplate().update(r);
	}

	@Override
	public void delete(Roles r) {
		
	}

	@Override
	public int getTotalCount(RoleView rv, PageBase pb) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Roles r where 1=1 ");
		/*if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}*/
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Roles getById(int pid) {
		String hql = "from Roles r where r.pid= "+pid;
		Query q = getSession().createQuery(hql);
		return (Roles) q.uniqueResult();
	}

	@Override
	public String checkname(int wcid, String name) {
		String sql = "from Roles r where r.watercompany.pid=:wcid and r.roleName = :name and r.valid = 1";
		Query q = getSession().createQuery(sql);
		q.setInteger("wcid", wcid);
		q.setString("name", name);
		if(q.list().size() > 0){
			return "true";
		}
		return "false";
	}

	@Override
	public List<Roles> getSystemList() {
		Query q = getSession().createQuery("from Roles r where r.systemRole = 1 and r.valid = 1");
		return q.list();
	}

}
