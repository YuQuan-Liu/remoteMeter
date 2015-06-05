package com.xdkj.yccb.main.readme.dao.impl;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.dao.MeterDao;

@Repository
public class MeterDaoImpl extends HibernateDAO<Meter> implements MeterDao {

	@Override
	public Meter getMeterByID(int m_id) {
		
		Query q = getSession().createQuery("from Meter m where m.pid = "+m_id);
		return (Meter) q.uniqueResult();
	}

	@Override
	public void updateMeterRead(int m_id, int type, int m_read) {
		
		Meter m = getMeterByID(m_id);
		m.setReaddata(m_read);
		m.setReadtime(new Date());
		m.setMeterState((byte)type);
		getHibernateTemplate().merge(m);
		
	}

	@Override
	public int updateMeterPrice(int meterId, int priceId) {
		String hql = "update Meter m set m.pricekindid=:priceId where m.pid=:meterId";
		
		return getSession().createSQLQuery(hql).setInteger("priceId", priceId)
			.setInteger("meterId", meterId).executeUpdate();
		
	}

	@Override
	public int updateDeread(int m_id, int waste) {
		String hql = "update Meter m set m.deread=m.deread+:deread,m.detime=now() where m.pid=:meterId";
		
		return getSession().createSQLQuery(hql).setInteger("deread", waste)
			.setInteger("meterId", m_id).executeUpdate();
	}

}
