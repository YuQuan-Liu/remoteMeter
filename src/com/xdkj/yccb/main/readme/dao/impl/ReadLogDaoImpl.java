package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;

@Repository
public class ReadLogDaoImpl extends HibernateDAO implements ReadLogDao {

	@Override
	public int addReadLog(Readlog readlog) {
		this.getHibernateTemplate().save(readlog);
		return readlog.getPid();
	}

	@Override
	public List<Readlog> findadminReading(int adminid) {
		Session session = getSession();
		session.setFlushMode(FlushMode.AUTO);
		Query q = session.createQuery("from Readlog log where log.admininfo.pid = "+adminid+" and log.readStatus < 100");
		
		return q.list();
	}

	@Override
	public int addReadLogs(List<Readlog> readlogs) {
		
		Readlog readlog = readlogs.get(0);
		
		Session session = getSession();
		Transaction tr = session.beginTransaction();
		for(int i = 0;i < readlogs.size();i++){
			session.save(readlogs.get(i));
			if(i/10==0){
				session.flush();
				session.clear();
			}
		}
		
		tr.commit();
		
		return readlog.getPid();
	}

}
