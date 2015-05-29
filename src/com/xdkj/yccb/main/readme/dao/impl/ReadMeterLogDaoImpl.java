package com.xdkj.yccb.main.readme.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.entity.Valveconflog;
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

	@Override
	public Map addReadMeterLogs(List<Readmeterlog> list) {
		int error = 0;
		String reason = "";
		Session session = getSession();
		//事务交由spring管理   再开事务的话  Hibernate 会报事务启动不成功异常
//		Transaction tr = session.beginTransaction();
		Readmeterlog readmeterlog = null;
		for(int i = 0;i < list.size();i++){
			readmeterlog = list.get(i);
			session.save(readmeterlog);
			if(readmeterlog.getPid() > 0){
//				good++;
			}else{
				error++;
				reason += (readmeterlog.getMeter().getPid()+"~");
			}
			if(i/20==0){
				session.flush();
				session.clear();
			}
		}
//		tr.commit();
		Map map = new HashMap();
		if(error != 0){
			map.put("success", false);
			map.put("reason", reason);
		}else{
			map.put("success", true);
			map.put("reason", "");
		}
		return map;
	}

}
