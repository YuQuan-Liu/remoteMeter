package com.xdkj.yccb.main.charge.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.statistics.dto.AdminSum;
import com.xdkj.yccb.main.statistics.dto.PayInfo;
@Repository
public class CustompaylogDAOImpl extends HibernateDAO<Customerpaylog> implements CustompaylogDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Customerpaylog> getList(int count, int custId) {
		String hql = "from Customerpaylog cpl where cpl.customer.pid=:pid order by cpl.actionTime desc ";
		return getSession().createQuery(hql).setParameter("pid", custId)
			.setFirstResult(0).setMaxResults(count).list();
	}
	
	public List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre){
		
		String SQL = "select c.customerName,c.customerMobile,c.customerAddr,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerBalance," +
				"c.prepaysign,cpl.amount,cpl.actionTime,cpl.prepaysign payPre,ad.adminName from customer c " +
				"join ( " +
				"select * from customerpaylog cpl " +
				"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 " +
				")cpl " +
				"on c.pid = cpl.customerid " +
				"join admininfo ad " +
				"on cpl.adminid = ad.pid " +
				"where c.valid = 1 and c.neighborid = :n_id and c.prepaysign = :pre ";
		if(pre == 2){
			SQL = "select c.customerName,c.customerMobile,c.customerAddr,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerBalance," +
					"c.prepaysign,cpl.amount,cpl.actionTime,cpl.prepaysign payPre,ad.adminName from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 and c.neighborid = :n_id ";
			
		}
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("amount",Hibernate.BIG_DECIMAL)
				.addScalar("actionTime",Hibernate.STRING)
				.addScalar("payPre",Hibernate.BYTE)
				.addScalar("adminName",Hibernate.STRING);
		q.setString("start", start);
		q.setString("end", end);
		q.setInteger("n_id", n_id);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(PayInfo.class));
		
		return q.list();
	}

	@Override
	public List<AdminSum> getAdminSum(int n_id, String start, String end,
			int pre) {
		
		String SQL = "select adminName,sum(amount) amount from customer c " +
				"join ( " +
				"select * from customerpaylog cpl " +
				"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 " +
				")cpl " +
				"on c.pid = cpl.customerid " +
				"join admininfo ad " +
				"on cpl.adminid = ad.pid " +
				"where c.valid = 1 and c.neighborid = :n_id and c.prepaysign = :pre " +
				"group by ad.pid,ad.adminName";
		if(pre == 2){
			SQL = "select adminName,sum(amount) amount from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 and c.neighborid = :n_id " +
					"group by ad.pid,ad.adminName";
			
		}
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("amount",Hibernate.BIG_DECIMAL)
				.addScalar("adminName",Hibernate.STRING);
		q.setString("start", start);
		q.setString("end", end);
		q.setInteger("n_id", n_id);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(AdminSum.class));
		
		return q.list();
	}

	@Override
	public Customerpaylog getById(Integer id) {
		return this.getById(Customerpaylog.class, id);
	}

	@Override
	public void updateCustLog(Customerpaylog c) {
		this.updateBase(c);
	}

}
