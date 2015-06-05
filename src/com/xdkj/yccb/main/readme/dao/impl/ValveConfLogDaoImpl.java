package com.xdkj.yccb.main.readme.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.entity.Valvelog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;

@Repository
public class ValveConfLogDaoImpl extends HibernateDAO implements ValveConfLogDao {

	@Autowired
	private MeterDao meterDao;
	
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


	@Override
	public void addConfLogs(Object[] ids, Valvelog valvelog) {
		
		Valveconflog conflog = null;
		Meter meter = null;
		Session session = getSession();
		//事务交由spring管理   再开事务的话  Hibernate 会报事务启动不成功异常
//		Transaction tr = session.beginTransaction();
		for(int i = 0;i < ids.length;i++){
			meter = meterDao.getMeterByID(Integer.parseInt(ids[i].toString()));
			conflog = new Valveconflog();
			conflog.setMeter(meter);
			conflog.setValvelog(valvelog);
			conflog.setSwitch_(0);
			conflog.setResult(0);
			conflog.setErrorReason("");
			conflog.setErrorStatus(0);
			conflog.setRemoveReason("");
			
			session.save(conflog);
			if(i/20==0){
				session.flush();
				session.clear();
			}
		}
//		tr.commit();
	}


	@Override
	public int updateException(Valvelog valvelog) {
		Query q = getSession().createQuery("update ValveConfLog " +
				"set result = 2,errorReason = "+valvelog.getFailReason()+",errorStatus = 1,completeTime = now() " +
				"where valveLogID = "+valvelog.getPid());
		return q.executeUpdate();
	}

	
}
