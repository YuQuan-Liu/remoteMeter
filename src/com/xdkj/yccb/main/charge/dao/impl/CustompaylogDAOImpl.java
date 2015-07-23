package com.xdkj.yccb.main.charge.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.charge.dto.CustomerpaylogView;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.statistics.dto.AdminSum;
import com.xdkj.yccb.main.statistics.dto.PayInfo;
@Repository
public class CustompaylogDAOImpl extends HibernateDAO<Customerpaylog> implements CustompaylogDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerpaylogView> getList(int count, int custId) {
		
		String SQL = "select cpl.pid,cpl.amount,cpl.actionTime,cpl.prePaySign,ad.adminName," +
				"c.customerBalance,c.customerId,c.customerName,c.customerAddr," +
				"concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num from customerpaylog cpl " +
				"join customer c " +
				"on cpl.customerid = c.pid " +
				"join admininfo ad " +
				"on cpl.adminid = ad.pid " +
				"where c.pid = :custId and c.valid = 1 and cpl.valid = 1 " +
				"order by cpl.pid desc " +
				"limit :count";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pid", Hibernate.INTEGER)
				.addScalar("c_num", Hibernate.STRING)
				.addScalar("customerId", Hibernate.STRING)
				.addScalar("customerName", Hibernate.STRING)
				.addScalar("customerAddr", Hibernate.STRING)
				.addScalar("adminName", Hibernate.STRING)
				.addScalar("amount", Hibernate.BIG_DECIMAL)
				.addScalar("actionTime", Hibernate.STRING)
				.addScalar("prePaySign", Hibernate.BYTE)
				.addScalar("customerBalance", Hibernate.BIG_DECIMAL);
		
		q.setResultTransformer(Transformers.aliasToBean(CustomerpaylogView.class));
		q.setInteger("custId", custId);
		q.setInteger("count", count);
		List<CustomerpaylogView> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
		
//		String hql = "from Customerpaylog cpl where cpl.customer.pid=:pid and cpl.valid = 1 order by cpl.actionTime desc ";
//		return getSession().createQuery(hql).setParameter("pid", custId)
//			.setFirstResult(0).setMaxResults(count).list();
	}
	

	@Override
	public CustomerpaylogView getViewById(int cplid) {
		String SQL = "select cpl.pid,cpl.amount,cpl.actionTime,cpl.prePaySign,ad.adminName," +
				"c.customerBalance,c.customerId,c.customerName,c.customerAddr," +
				"concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num from customerpaylog cpl " +
				"join customer c " +
				"on cpl.customerid = c.pid " +
				"join admininfo ad " +
				"on cpl.adminid = ad.pid " +
				"where cpl.pid=:cplid and c.valid = 1 ";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pid", Hibernate.INTEGER)
				.addScalar("c_num", Hibernate.STRING)
				.addScalar("customerId", Hibernate.STRING)
				.addScalar("customerName", Hibernate.STRING)
				.addScalar("customerAddr", Hibernate.STRING)
				.addScalar("adminName", Hibernate.STRING)
				.addScalar("amount", Hibernate.BIG_DECIMAL)
				.addScalar("actionTime", Hibernate.STRING)
				.addScalar("prePaySign", Hibernate.BYTE)
				.addScalar("customerBalance", Hibernate.BIG_DECIMAL);
		
		q.setResultTransformer(Transformers.aliasToBean(CustomerpaylogView.class));
		q.setInteger("cplid", cplid);
		return (CustomerpaylogView) q.uniqueResult();
	}
	
	public List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre){
		
		String SQL = "";
		if(n_id == 0){
			SQL = "select c.customerName,c.customerMobile,c.customerAddr,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerBalance," +
					"c.prepaysign,cpl.amount,cpl.actionTime,cpl.prepaysign payPre,ad.adminName from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 and cpl.prepaysign = :pre " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 ";
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
						"where c.valid = 1 ";
				
			}
			
		}else{
			SQL = "select c.customerName,c.customerMobile,c.customerAddr,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerBalance," +
					"c.prepaysign,cpl.amount,cpl.actionTime,cpl.prepaysign payPre,ad.adminName from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 and cpl.prepaysign = :pre " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 and c.neighborid = :n_id ";
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
		if(n_id != 0){
			q.setInteger("n_id", n_id);
		}
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		q.setResultTransformer(Transformers.aliasToBean(PayInfo.class));
		
		return q.list();
	}

	@Override
	public List<AdminSum> getAdminSum(int n_id, String start, String end,
			int pre) {
		String SQL = "";
		if(n_id != 0){
			SQL = "select adminName,sum(amount) amount from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 and cpl.prepaysign = :pre " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 and c.neighborid = :n_id " +
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
		}else{
			SQL = "select adminName,sum(amount) amount from customer c " +
					"join ( " +
					"select * from customerpaylog cpl " +
					"where actiontime > :start and actiontime < date_add(:end,interval 1 day) and cpl.valid = 1 and cpl.prepaysign = :pre " +
					")cpl " +
					"on c.pid = cpl.customerid " +
					"join admininfo ad " +
					"on cpl.adminid = ad.pid " +
					"where c.valid = 1 " +
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
						"where c.valid = 1 " +
						"group by ad.pid,ad.adminName";
				
			}
		}
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("amount",Hibernate.BIG_DECIMAL)
				.addScalar("adminName",Hibernate.STRING);
		q.setString("start", start);
		q.setString("end", end);
		if(n_id != 0){
			q.setInteger("n_id", n_id);
		}
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

	@Override
	public int addPaylog(int adminid,BigDecimal amount, int c_id) {
		Customerpaylog cpl = new Customerpaylog();
		cpl.setActionTime(new Date());
		Admininfo admininfo = new Admininfo();
		admininfo.setPid(adminid);
		cpl.setAdmininfo(admininfo);
		cpl.setAmount(amount);
		Customer c = new Customer();
		c.setPid(c_id);
		cpl.setCustomer(c);
		cpl.setPrePaySign((byte)1);
		cpl.setValid("1");
		this.getHibernateTemplate().save(cpl);
		return cpl.getPid();
	}


	@Override
	public List<Customerpaylog> getPaylogLimit2(int cid,int cplid) {
		String SQL = "select * from customerpaylog where customerID = :cid and pid >= :cplid and valid = 1 order by pid asc limit 2";
		Query q = getSession().createSQLQuery(SQL).addEntity(Customerpaylog.class);
		q.setInteger("cid", cid);
		q.setInteger("cplid", cplid);
		return q.list();
	}


}
