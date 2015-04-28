package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
@Repository
public class GprsDAOImpl extends HibernateDAO<Gprs> implements GprsDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Gprs> getListByNeighborId(int nbrid) {
		String hql = "from Gprs g where g.neighbor.pid=:pid and g.valid='1' ";
		Query q = getSession().createQuery(hql);
		q.setParameter("pid", nbrid);
		return q.list();
	}
	@Override
	public Gprs getById(int gprsId) {
		String hql = "from Gprs g where g.pid=:pid and g.valid='1' ";
		Query q = getSession().createQuery(hql);
		q.setParameter("pid", gprsId);
		return (Gprs) q.uniqueResult();
	}
	@Override
	public int addGprs(Gprs gprs) {
		getHibernateTemplate().save(gprs);
		return gprs.getPid();
	}

	@Override
	public void deleteGprs(int gprsId) {
		String hql = "update Gprs g set g.valid='0' where g.pid=:pid";
		getSession().createQuery(hql).setParameter("pid", gprsId).executeUpdate();
	}

	@Override
	public void update(Gprs gprs) {
		getHibernateTemplate().update(gprs);
	}

}
