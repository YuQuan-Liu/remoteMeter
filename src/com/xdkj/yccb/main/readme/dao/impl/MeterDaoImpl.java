package com.xdkj.yccb.main.readme.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.dao.MeterDao;

@Repository
public class MeterDaoImpl extends HibernateDAO implements MeterDao {

	@Override
	public Meter getMeterByID(int m_id) {
		
		Query q = getSession().createQuery("from Meter m where m.pid = "+m_id);
		return (Meter) q.uniqueResult();
	}

}
