package com.xdkj.yccb.main.readme.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;

@Repository
public class ReadMeterLogDaoImpl extends HibernateDAO implements
		ReadMeterLogDao {

	@Override
	public Readmeterlog getMaxReadMeterLog(int m_id) {
		Query q = getSession().createQuery("from Readmeterlog log order by log.pid desc where log.meter.pid = "+m_id);
		q.setFirstResult(0);
		q.setMaxResults(1);
		return (Readmeterlog) q.uniqueResult();
	}

	@Override
	public Readmeterlog addReadMeterLog(Readmeterlog newlog) {
		this.getHibernateTemplate().save(newlog);
		return newlog;
		
	}

}
