package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.entity.Admininfo;
@Repository
public class AdministratorDAOImpl extends HibernateDAO<Admininfo> implements AdministratorDAO {

	@Override
	public List<Admininfo> getList(Admininfo adInfo,PageBase pageInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Admininfo ai where 1=1 ");
		if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}
		Query q = getSession().createQuery(sb.toString());
		q.setFirstResult((pageInfo.getPage()-1)*pageInfo.getRows());
		q.setMaxResults(pageInfo.getRows());
		return q.list();
	}

	@Override
	public Admininfo getById(Integer adminId) {
		String hql = "from Admininfo a where a.pid=:pid ";
		Query q = getSession().createQuery(hql);
		q.setInteger("pid", adminId);
		return (Admininfo) q.uniqueResult();
	}

	@Override
	public int addAdmin(Admininfo adminInfo) {
		getHibernateTemplate().save(adminInfo);
		return adminInfo.getPid();
	}

	@Override
	public boolean removeById(Integer adminId) {
		Admininfo adInfo = new Admininfo();
		adInfo.setPid(adminId);
		getHibernateTemplate().delete(adInfo);
		return false;
	}

	@Override
	public boolean update(Admininfo adminInfo) {
		getHibernateTemplate().merge(adminInfo);
		return false;
	}

	@Override
	public Integer getTotalCount(Admininfo adInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Admininfo ai where 1=1 ");
		if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Admininfo getByLoginName(String loginName, String password) {
		Query q = getSession().createQuery("from Admininfo a where a.loginName=:loginName");
		q.setString("loginName", loginName);
		Admininfo adInfo = null;
		adInfo = (Admininfo) q.uniqueResult();
		return adInfo;
	}

}
