package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dto.Collector;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dto.MeterViewSimple;
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
		String sql = "update Meter m set m.valid='0' where m.gprs.pid=:pid";
		getSession().createQuery(sql).setParameter("pid", gprsId).executeUpdate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteByNbrId(int nbrId) {
		String hql = "update Gprs g set g.valid='0' where g.neighbor.pid=:pid";
		getSession().createQuery(hql).setParameter("pid", nbrId).executeUpdate();
	}
	
	@Override
	public void update(Gprs gprs) {
		getHibernateTemplate().update(gprs);
	}
	@Override
	public Gprs getByAddr(String g_addr) {
		String hql = "from Gprs g where g.gprsaddr = '"+g_addr+"' and g.valid='1' ";
		Query q = getSession().createQuery(hql);
		return (Gprs) q.uniqueResult();
	}
	@Override
	public List<Gprs> getCleanList() {
		String hql = "from Gprs g where g.cleanSwitch=1 and g.gprsprotocol=2 and g.valid='1' ";
		Query q = getSession().createQuery(hql);
		return q.list();
	}
	@Override
	public List<MeterViewSimple> listgprsmeters(int gid,String caddr) {
		String SQL = "select pid,collectorAddr,meterAddr from meter " +
				"where gprsid = :gid and collectoraddr = :caddr and valid = 1 " +
				"order by collectoraddr,meteraddr";
		
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pid",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING);

		q.setInteger("gid", gid);
		q.setString("caddr", caddr);
		
		q.setResultTransformer(Transformers.aliasToBean(MeterViewSimple.class));
		
		return q.list();
	}
	@Override
	public List<Collector> listCollectors(int pid) {
		String SQL = "select distinct collectorAddr caddr from meter " +
				"where gprsid = :gid and valid = 1 " +
				"order by collectoraddr";
		
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("caddr",Hibernate.STRING);

		q.setInteger("gid", pid);
		
		q.setResultTransformer(Transformers.aliasToBean(Collector.class));
		
		return q.list();
	}

}
