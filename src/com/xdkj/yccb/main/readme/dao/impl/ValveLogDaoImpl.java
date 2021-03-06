package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Valvelog;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;
import com.xdkj.yccb.main.readme.dao.ValveLogDao;

@Repository
public class ValveLogDaoImpl extends HibernateDAO implements ValveLogDao {

	@Autowired
	private ValveConfLogDao valveConfLogDao;
	@Override
	public List<Valvelog> findadminValveing(Integer adminid) {

		Session session = getSession();
		session.setFlushMode(FlushMode.AUTO);
		Query q = session.createQuery("from Valvelog log where log.admininfo.pid = "+adminid+" and log.status < 100");
		
		return q.list();
	}

	@Override
	public int addValveLog(Valvelog valvelog) {
		this.getHibernateTemplate().save(valvelog);
		return valvelog.getPid();
	}

	@Override
	public Valvelog getValveLogByID(int valvelogid) {
		Query q = getSession().createQuery("from Valvelog log where log.pid = "+valvelogid);
		
		return (Valvelog) q.uniqueResult();
	}

	@Override
	public int updateControlException(Valvelog valvelog, Exception e) {
		valvelog.setStatus(100);
		valvelog.setFailReason(e.getMessage());
		valvelog.setErrorCount(valvelog.getActionCount());
		
		this.getHibernateTemplate().merge(valvelog);
		
		return valveConfLogDao.updateException(valvelog);
		
	}

}
