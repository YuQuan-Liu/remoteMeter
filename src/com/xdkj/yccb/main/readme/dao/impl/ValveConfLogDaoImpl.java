package com.xdkj.yccb.main.readme.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;

@Repository
public class ValveConfLogDaoImpl extends HibernateDAO implements ValveConfLogDao {

	@Override
	public int addConfLog(Valveconflog conflog) {
		this.getHibernateTemplate().save(conflog);
		return conflog.getPid();
	}

	
	@Override
	public Valveconflog getConfLogByLogID(int valvelogid) {
		Query q = getSession().createQuery("from Valveconflog log where log.valvelog.pid = "+valvelogid);
		
		return (Valveconflog) q.uniqueResult();
	}

	@Override
	public Valveconflog getConfLogByID(int conf_id) {
		Query q = getSession().createQuery("from Valveconflog log where log.pid = "+conf_id);
		
		return (Valveconflog) q.uniqueResult();
	}

	@Override
	public Valveconflog updateError(int conf_id, String reason) {
		Valveconflog conflog = getConfLogByID(conf_id);
		conflog.setErrorStatus(1);
		conflog.setErrorReason(reason);
		this.getHibernateTemplate().update(conflog);
//		this.getHibernateTemplate().merge(conflog);
		return conflog;
	}

	
}
