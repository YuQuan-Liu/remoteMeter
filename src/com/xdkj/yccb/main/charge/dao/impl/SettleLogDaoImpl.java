package com.xdkj.yccb.main.charge.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.SettleLogDao;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Settlelog;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;

@Repository
public class SettleLogDaoImpl extends HibernateDAO implements SettleLogDao {

	@Autowired
	private ReadLogDao readLogDao;
	@Override
	public List<Settlelog> getTodaySettleLog(int n_id) {

		String hql = "from Settlelog log " +
				"where log.objectId = :n_id and log.objectType = 1 and log.auto = 0 and log.startTime >= :today";
		Query q = getSession().createQuery(hql);
		q.setInteger("n_id", n_id);
		q.setString("today", new SimpleDateFormat("yyyy.MM.dd").format(new Date()));
		return q.list();
	}

	@Override
	public void settleAll(int n_id, int adminid, int readlogid) {
		//选出当前小区  最新的抄表记录
		readLogDao.settleAll(n_id,adminid,readlogid);
	}

	@Override
	public Settlelog getLastSettleLog(int n_id) {
		String hql = "from Settlelog log " +
				"where log.objectId = :n_id and log.objectType = 1 and log.auto = 0 order by log.pid desc ";
		Query q = getSession().createQuery(hql);
		q.setInteger("n_id", n_id);
		q.setFirstResult(0);
		q.setMaxResults(1);
		return (Settlelog) q.uniqueResult();
	}

	@Override
	public void settleSingle(int m_id, int adminid, int settlelogid) {
		
		readLogDao.settleSingle(m_id, adminid, settlelogid);
	}

	@Override
	public String getSettleLogs(int n_id) {
		String hql = "from Settlelog log " +
				"where log.objectId = :n_id and log.objectType = 1 order by log.pid desc ";
		Query q = getSession().createQuery(hql);
		q.setInteger("n_id", n_id);
		q.setFirstResult(0);
		q.setMaxResults(10);
		List<Settlelog> list = q.list();
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		Settlelog settlelog = null;
		for(int i = 0;list != null && i < list.size();i++){
			settlelog = list.get(i);
			jo = new JSONObject();
			jo.put("pid", settlelog.getPid());
			jo.put("startTime", settlelog.getStartTime().toLocaleString());
			ja.add(jo);
		}
		return ja.toJSONString();
	}

	@Override
	public String getSettleLogsAuto(int n_id) {
		String hql = "from Settlelog log " +
				"where log.objectId = :n_id and log.objectType = 1 and log.auto = 1 order by log.pid desc ";
		Query q = getSession().createQuery(hql);
		q.setInteger("n_id", n_id);
		q.setFirstResult(0);
		q.setMaxResults(20);
		List<Settlelog> list = q.list();
		JSONArray ja = new JSONArray();
		JSONObject jo = null;
		Settlelog settlelog = null;
		for(int i = 0;list != null && i < list.size();i++){
			settlelog = list.get(i);
			jo = new JSONObject();
			jo.put("pid", settlelog.getPid());
			jo.put("startTime", settlelog.getStartTime().toLocaleString());
			ja.add(jo);
		}
		return ja.toJSONString();
	}

}
