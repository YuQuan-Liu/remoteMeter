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
	public void changeValue(int meterId, byte valueState) {
		String hql = "update Meter m set m.valveState=:valueState where m.pid =:pid";
		getSession().createQuery(hql).setParameter("valueState", valueState)
			.setInteger("pid", meterId).executeUpdate();
	}

	@Override
	public void updateMeterPrice(int meterId, int priceId) {
		String hql = "update Meter m set m.pricekind.pid=:priceId where m.pid=:meterId";
		getSession().createQuery(hql).setInteger("priceId", priceId)
			.setInteger("meterId", meterId).executeUpdate();
		
	}

}
