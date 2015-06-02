package com.xdkj.yccb.main.readme.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettleView;
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

	@Override
	public List<SettleView> getReadMeterLogToSettle(int n_id) {
		
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"g.GPRSAddr g_addr,m.pid m_id, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState,m.deread,m.readdata,m.readtime,m.changend changeend from customer c " +
				"left join meter m " +
				"on c.pid = m.customerid " +
				"left join gprs g " +
				"on m.gprsid = g.pid " +
				"where c.neighborid = :n_id and c.valid = 1 and m.valid = 1")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("isValve",Hibernate.INTEGER)
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("changeend",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING);
		q.setInteger("n_id", n_id);
		q.setResultTransformer(Transformers.aliasToBean(SettleView.class));
		return q.list();
	}

	@Override
	public SettleView getReadMeterLog(int m_id) {
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"g.GPRSAddr g_addr,m.pid m_id, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState,m.deread,m.readdata,m.readtime from customer c " +
				"left join meter m " +
				"on c.pid = m.customerid " +
				"left join gprs g " +
				"on m.gprsid = g.pid " +
				"where m.pid = :m_id")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("isValve",Hibernate.INTEGER)
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING);
		q.setInteger("m_id", m_id);
		q.setResultTransformer(Transformers.aliasToBean(SettleView.class));
		return (SettleView) q.uniqueResult();
	}

	@Override
	public List<SettleSum> getSettleSum(int n_id) {
		String SQL = "select pk.pricekindname,sum(m.readdata-m.deread) yl from customer c " +
				"join meter m " +
				"on c.pid = m.customerid " +
				"join pricekind pk " +
				"on pk.pid = m.PriceKindID " +
				"where c.Valid = 1 and m.valid = 1 and c.neighborid = :n_id " +
				"group by pk.PriceKindName";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pricekindname",Hibernate.STRING)
				.addScalar("yl",Hibernate.INTEGER);
		
		q.setInteger("n_id", n_id);
		q.setResultTransformer(Transformers.aliasToBean(SettleSum.class));
		List<SettleSum> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<SettleSum> getSettledSum(int n_id, int settle_id,int pre) {
		String SQL = "";
		
		if(pre == 2){
			
			SQL = "select pricekindname,sum(demoney) demoney,sum(yl) yl from ( " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and changend = 0 " +
					"union " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread+changend-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and changend > 0 " +
					") sum_";
		}else{
			SQL = "select pricekindname,sum(demoney) demoney,sum(yl) yl from ( " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.paytype = :pre and changend = 0 " +
					"union " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread+changend-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.paytype = :pre and changend > 0 " +
					") sum_";
		}
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pricekindname",Hibernate.STRING)
				.addScalar("yl",Hibernate.INTEGER)
				.addScalar("demoney",Hibernate.DOUBLE);
		
		q.setInteger("settlelogid", settle_id);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(SettleSum.class));
		
		List<SettleSum> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<SettleSum> getLouSettledSum(int n_id, int settle_id, int pre,
			String lou) {
		String SQL = "";
		
		if(pre == 2){
			
			SQL = "select pricekindname,sum(demoney) demoney,sum(yl) yl from ( " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"join meter m on mdl.meterid = m.pid " +
					"join customer c on c.pid = m.customerid " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.changend = 0 and m.valid = 1 and c.valid = 1 and c.louNum = :lou " +
					"union " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread+mdl.changend-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"join meter m on mdl.meterid = m.pid " +
					"join customer c on c.pid = m.customerid " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.changend > 0 and m.valid = 1 and c.valid = 1 and c.louNum = :lou " +
					") sum_";
		}else{
			SQL = "select pricekindname,sum(demoney) demoney,sum(yl) yl from ( " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"join meter m on mdl.meterid = m.pid " +
					"join customer c on c.pid = m.customerid " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.paytype = :pre and mdl.changend = 0 and m.valid = 1 and c.valid = 1 and c.louNum = :lou " +
					"union " +
					"select pk.pricekindname,sum(demoney) demoney,sum(meterread+mdl.changend-lastderead) yl from meterdeductionlog mdl " +
					"join pricekind pk " +
					"on mdl.pricekindid = pk.PID " +
					"join meter m on mdl.meterid = m.pid " +
					"join customer c on c.pid = m.customerid " +
					"where settlelogid = :settlelogid and mdl.valid = 1 and mdl.paytype = :pre and mdl.changend > 0 and m.valid = 1 and c.valid = 1 and c.louNum = :lou " +
					") sum_";
		}
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pricekindname",Hibernate.STRING)
				.addScalar("yl",Hibernate.INTEGER)
				.addScalar("demoney",Hibernate.DOUBLE);
		
		q.setInteger("settlelogid", settle_id);
		q.setString("lou", lou);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(SettleSum.class));
		
		List<SettleSum> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
