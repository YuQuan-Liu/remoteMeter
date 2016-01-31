package com.xdkj.yccb.main.readme.dao.impl;

import java.util.Date;
import java.util.List;

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
	public Meter getMeterByAPID(String apid, int n_id) {
		Query q = getSession().createQuery("from Meter m where m.apid = :apid and m.neighbor.pid=:n_id").setString("apid", apid).setInteger("n_id", n_id);
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
	public int updateDeread(int m_id, int deread) {
		String hql = "update Meter m set m.deread=:deread where m.pid=:meterId";
		
		return getSession().createSQLQuery(hql).setInteger("deread", deread)
			.setInteger("meterId", m_id).executeUpdate();
	}
	
	@Override
	public int updateDeread(int m_id, int deread,Date detime) {
		String hql = "update Meter m set m.deread=:deread,m.detime=:detime where m.pid=:meterId";
		
		return getSession().createSQLQuery(hql).setInteger("deread", deread)
			.setInteger("meterId", m_id).setDate("detime", detime).executeUpdate();
	}

	@Override
	public List<Meter> getTimerList() {
		
		Query q = getSession().createQuery("from Meter m where m.valid = 1 and m.timerSwitch = 1 and m.gprs.gprsprotocol <> 4");
		return q.list();
	}

}
