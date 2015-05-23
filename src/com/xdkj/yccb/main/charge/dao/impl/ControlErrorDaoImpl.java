package com.xdkj.yccb.main.charge.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.ControlErrorDao;
import com.xdkj.yccb.main.charge.dto.ControlErrorView;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Valveconflog;

@Repository
public class ControlErrorDaoImpl extends HibernateDAO implements
		ControlErrorDao {

	@Override
	public List<ControlErrorView> getControlErrors(int n_id) {
		Query q = getSession().createSQLQuery("select concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.CustomerMobile,c.CustomerBalance," +
				"g.GPRSAddr g_addr, m.collectorAddr,m.meterAddr,m.valveState,m.meterState," +
				"conflog.switch switch_,conflog.errorReason,conflog.pid conf_id,conflog.completeTime from customer c " +
				"left join meter m " +
				"on c.pid = m.customerid " +
				"left join gprs g " +
				"on m.gprsid = g.pid " +
				"left join valveconflog conflog " +
				"on m.pid = conflog.meterid " +
				"where c.neighborid = "+n_id+" and c.valid = 1 and m.valid = 1 and conflog.result = 2 and conflog.errorstatus = 0")
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("switch_",Hibernate.INTEGER)
				.addScalar("errorReason",Hibernate.STRING)
				.addScalar("completeTime",Hibernate.STRING)
				.addScalar("conf_id",Hibernate.INTEGER);

		
		q.setResultTransformer(Transformers.aliasToBean(ControlErrorView.class));
		
		return q.list();
	}

}
