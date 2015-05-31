package com.xdkj.yccb.main.statistics.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.dto.SettleView;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
@Repository
public class MeterDeductionLogImpl extends HibernateDAO<Meterdeductionlog> implements MeterDeductionLogDao {

	@Override
	public List<Meterdeductionlog> getMeterDeductionLog(String id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meterdeductionlog> getList(int count, int custId) {
		String hql = "from Meterdeductionlog mdl where mdl.meter.customer.pid=:pid order by mdl.actionTime desc";
		return getSession().createQuery(hql).setParameter("pid", custId)
				.setFirstResult(0).setMaxResults(count).list();
	}

	@Override
	public List<SettledView> getLogPostPay(int n_id, int settle_id) {
		
		String SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"m.pid m_id, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
				"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed," +
				"pk.pricekindname from meterdeductionlog mdl " +
				"join meter m " +
				"on m.pid = mdl.meterid " +
				"join customer c " +
				"on c.pid = m.customerid " +
				"join pricekind pk " +
				"on m.pricekindid = pk.pid " +
				"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.paytype = 1 and mdl.settlelogid = :settle_id and c.neighborid = :n_id";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("mdl_id",Hibernate.INTEGER)
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
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("isValve",Hibernate.INTEGER)
				
				.addScalar("lastderead",Hibernate.INTEGER)
				.addScalar("meterread",Hibernate.INTEGER)
				.addScalar("meterreadtime",Hibernate.STRING)
				.addScalar("demoney",Hibernate.BIG_DECIMAL)
				.addScalar("paytype",Hibernate.INTEGER)
				.addScalar("printed",Hibernate.INTEGER)
				.addScalar("payed",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		return q.list();
	}

	@Override
	public List<SettledView> getLogAuto(int n_id, int settle_id) {
		
		String SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"m.pid m_id, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
				"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed," +
				"pk.pricekindname from meterdeductionlog mdl " +
				"join meter m " +
				"on m.pid = mdl.meterid " +
				"join customer c " +
				"on c.pid = m.customerid " +
				"join pricekind pk " +
				"on m.pricekindid = pk.pid " +
				"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.settlelogid = :settle_id and c.neighborid = :n_id";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("mdl_id",Hibernate.INTEGER)
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
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("isValve",Hibernate.INTEGER)
				
				.addScalar("lastderead",Hibernate.INTEGER)
				.addScalar("meterread",Hibernate.INTEGER)
				.addScalar("meterreadtime",Hibernate.STRING)
				.addScalar("demoney",Hibernate.BIG_DECIMAL)
				.addScalar("paytype",Hibernate.INTEGER)
				.addScalar("printed",Hibernate.INTEGER)
				.addScalar("payed",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		return q.list();
	}

}
