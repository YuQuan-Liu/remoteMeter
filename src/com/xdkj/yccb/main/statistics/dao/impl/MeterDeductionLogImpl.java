package com.xdkj.yccb.main.statistics.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.dto.SettleView;

import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
@Repository
public class MeterDeductionLogImpl extends HibernateDAO<Meterdeductionlog> implements MeterDeductionLogDao {

	@Override
	public Meterdeductionlog getMeterDeductionLog(int mdl_id) {
		String hql = "from Meterdeductionlog mdl where mdl.pid=:pid ";
		return (Meterdeductionlog) getSession().createQuery(hql).setParameter("pid", mdl_id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meterdeductionlog> getList(int count, int custId) {
		String hql = "from Meterdeductionlog mdl where mdl.meter.customer.pid=:pid and mdl.valid = 1 order by mdl.actionTime desc";
		return getSession().createQuery(hql).setParameter("pid", custId)
				.setFirstResult(0).setMaxResults(count).list();
	}

	@Override
	public Meterdeductionlog getById(Integer mdlId) {
		return this.getById(Meterdeductionlog.class, mdlId);
	}

	@Override
	public void updateMeterductionLog(Meterdeductionlog mdl) {
		this.updateBase(mdl);
	}
	@Override
	public List<SettledView> getLogPostPay(int n_id, int settle_id) {
		
		String SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
				"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
				"pk.pricekindname from meterdeductionlog mdl " +
				"join meter m " +
				"on m.pid = mdl.meterid " +
				"join customer c " +
				"on c.pid = m.customerid " +
				"join pricekind pk " +
				"on m.pricekindid = pk.pid " +
				"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.paytype = 0 and mdl.settlelogid = :settle_id and c.neighborid = :n_id";
		
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
				.addScalar("steelNum",Hibernate.STRING)
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
				.addScalar("changeend",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		List<SettledView> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<SettledView> getLogAuto(int n_id, int settle_id) {
		
		String SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
				"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
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
				.addScalar("steelNum",Hibernate.STRING)
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
				.addScalar("changeend",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		return q.list();
	}
	
	@Override
	public void chargepostpay(int adminid, int[] mdl_ids) {
		
		Query q = getSession().createSQLQuery("{call postpay(?,?)}");
		for(int i = 0;i < mdl_ids.length;i++){
			Meterdeductionlog mdl = getMeterDeductionLog(mdl_ids[i]);
			if(mdl.getPayed() == '0'){
				q.setInteger(0, adminid);
				q.setInteger(1, mdl_ids[i]);
				q.executeUpdate();
			}
		}
		
	}

	@Override
	public List<PostCharge> getPostCharge(String ids) {
		
		List<PostCharge> list = null;
		String[] mdl_ids = ids.split(",");
		if(!ids.trim().equals("") && mdl_ids.length > 0){
			String SQL = "select mdl.MeterRead thisread,mdl.LastDeRead lastread,mdl.MeterReadTime readTime,mdl.changend changeend,mdl.demoney demoney,ad.AdminName,pk.pricekindname pkName," +
					"c.CustomerAddr,c.CustomerName,c.CustomerID,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num," +
					"case when mdl.changend > 0 then mdl.MeterRead+mdl.changend - mdl.LastDeRead else mdl.MeterRead-mdl.LastDeRead end yl,m.steelNum from meterdeductionlog mdl " +
					"join meter m on mdl.meterid = m.pid " +
					"join customer c on m.customerid = c.pid " +
					"join settlelog settle on settle.pid = mdl.settlelogid " +
					"join admininfo ad on settle.adminid = ad.pid " +
					"join pricekind pk on pk.pid = mdl.pricekindid " +
					"where mdl.pid in ("+ids+")";
			
			Query q = getSession().createSQLQuery(SQL)
					.addScalar("adminName",Hibernate.STRING)
					.addScalar("customerAddr",Hibernate.STRING)
					.addScalar("customerName",Hibernate.STRING)
					.addScalar("c_num",Hibernate.STRING)
					.addScalar("customerID",Hibernate.STRING)
					.addScalar("yl",Hibernate.STRING)
					.addScalar("lastread",Hibernate.STRING)
					.addScalar("thisread",Hibernate.STRING)
					.addScalar("changeend",Hibernate.STRING)
					.addScalar("demoney",Hibernate.DOUBLE)
					.addScalar("readTime",Hibernate.STRING)
					.addScalar("pkName",Hibernate.STRING)
					.addScalar("steelNum",Hibernate.STRING);
			q.setResultTransformer(Transformers.aliasToBean(PostCharge.class));
			list = q.list();
		}
		
		
		return list;
	}

	@Override
	public List<SettledView> getLogAll(int n_id, int settle_id, int pre) {
		
		String SQL = "";
		if(pre == 2){
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
					"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
					"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
					"pk.pricekindname from meterdeductionlog mdl " +
					"join meter m " +
					"on m.pid = mdl.meterid " +
					"join customer c " +
					"on c.pid = m.customerid " +
					"join pricekind pk " +
					"on m.pricekindid = pk.pid " +
					"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.settlelogid = :settle_id and c.neighborid = :n_id";
		}else{
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
					"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
					"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
					"pk.pricekindname from meterdeductionlog mdl " +
					"join meter m " +
					"on m.pid = mdl.meterid " +
					"join customer c " +
					"on c.pid = m.customerid " +
					"join pricekind pk " +
					"on m.pricekindid = pk.pid " +
					"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.paytype = :pre and mdl.settlelogid = :settle_id and c.neighborid = :n_id";
		}
		
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
				.addScalar("steelNum",Hibernate.STRING)
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
				.addScalar("changeend",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		
		List<SettledView> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<SettledView> getLouLogAll(int n_id, int settle_id, int pre,
			String lou) {
		String SQL = "";
		if(pre == 2){
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
					"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
					"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
					"pk.pricekindname from meterdeductionlog mdl " +
					"join meter m " +
					"on m.pid = mdl.meterid " +
					"join customer c " +
					"on c.pid = m.customerid " +
					"join pricekind pk " +
					"on m.pricekindid = pk.pid " +
					"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.settlelogid = :settle_id and c.neighborid = :n_id and c.louNum = :lou";
		}else{
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
					"m.pid m_id,m.steelNum, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState," +
					"mdl.pid mdl_id,mdl.lastderead,mdl.meterread,mdl.meterreadtime,mdl.demoney,mdl.paytype,mdl.printed,mdl.payed,mdl.changend changeend, " +
					"pk.pricekindname from meterdeductionlog mdl " +
					"join meter m " +
					"on m.pid = mdl.meterid " +
					"join customer c " +
					"on c.pid = m.customerid " +
					"join pricekind pk " +
					"on m.pricekindid = pk.pid " +
					"where mdl.valid = 1 and m.valid = 1 and c.valid = 1 and mdl.paytype = :pre and mdl.settlelogid = :settle_id and c.neighborid = :n_id and c.louNum = :lou";
		}
		
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
				.addScalar("steelNum",Hibernate.STRING)
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
				.addScalar("changeend",Hibernate.INTEGER)
				.addScalar("pricekindname",Hibernate.STRING);
		
		q.setInteger("n_id", n_id);
		q.setInteger("settle_id", settle_id);
		q.setString("lou", lou);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(SettledView.class));
		
		List<SettledView> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
