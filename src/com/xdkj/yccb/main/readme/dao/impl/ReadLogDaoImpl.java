package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Admininfo;
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

	@Override
	public Readlog getReadLogByID(int readlogid) {
		Query q = getSession().createQuery("from Readlog log where log.pid = "+readlogid);
		
		return (Readlog) q.uniqueResult();
	}

	@Override
	public List<Readlog> getReadLogNeighbors(int readlogid, int adminid) {
		Query q = getSession().createQuery("from Readlog log where log.pid >= "+readlogid +" and log.admininfo.pid = "+adminid);
		
		return q.list();
	}

	@Override
	public String getReadLogNeighborsNonSettle(String n_id) {
		Query q = getSession().createQuery("select max(log.pid) from Readlog log " +
				"where log.readObject <> 3 and log.objectId = "+n_id+" and log.settle = 1");
		int maxpid = 0;
		if(q.uniqueResult() != null){
			maxpid = (int) q.uniqueResult();
		}
		q = getSession().createQuery("from Readlog log " +
				"where log.readObject <> 3 and log.objectId = "+n_id +" and log.pid > "+maxpid+" and log.readStatus = 100 and log.completeTime is not null " +
						"order by log.pid desc ");
		q.setFirstResult(0);
		q.setMaxResults(20);
		List<Readlog> list = q.list();
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		Readlog readlog = null;
		for(int i = 0;list != null && i < list.size();i++){
			readlog = list.get(i);
			jo = new JSONObject();
			jo.put("pid", readlog.getPid());
			jo.put("completetime", readlog.getCompleteTime().toLocaleString());
			ja.add(jo);
		}
		return ja.toJSONString();
	}

	@Override
	public void updateException(Readlog readlog, Admininfo admin, Exception e) {
		Query q = getSession().createQuery("update ReadLog " +
				"set ReadStatus = 100,FailReason = :e,completeTime = now(),Result = '抄表异常' " +
				"where pid >= "+readlog.getPid()+" and adminid = "+admin.getPid());
		q.setString("e", e.getMessage());
		q.executeUpdate();
	}

	@Override
	public Readlog getMaxReadlogNonSettle(int n_id) {
		
		Query q = getSession().createQuery("from Readlog log " +
				"where log.readObject <> 3 and log.objectId = "+n_id +" and log.readStatus = 100 and log.settle = 0 and log.completeTime is not null order by log.pid desc ");
		q.setFirstResult(0);
		q.setMaxResults(1);
		return (Readlog) q.uniqueResult();
	}
	
	@Override
	public Readlog getMaxReadlogSettle(int n_id) {
		
		Query q = getSession().createQuery("from Readlog log " +
				"where log.readObject <> 3 and log.objectId = "+n_id +" and log.readStatus = 100 and log.settle = 1 and log.completeTime is not null order by log.pid desc ");
		q.setFirstResult(0);
		q.setMaxResults(1);
		return (Readlog) q.uniqueResult();
	}

	@Override
	public void settleAll(int n_id, int adminid, int readlogid) {
		
		Query q = getSession().createSQLQuery("{call calculate_neighbor(?,?,?)}");
		q.setInteger(0, n_id);
		q.setInteger(1, adminid);
		q.setInteger(2, readlogid);
		q.executeUpdate();
	}

	@Override
	public void settleSingle(int m_id, int adminid, int settlelogid) {

		Query q = getSession().createSQLQuery("{call calculate_hu(?,?,?)}");
		q.setInteger(0, m_id);
		q.setInteger(1, adminid);
		q.setInteger(2, settlelogid);
		q.executeUpdate();
	}

}
