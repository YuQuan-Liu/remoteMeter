package com.xdkj.yccb.main.infoin.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dto.CustomerView;

@Repository
public class CustomerDaoImpl extends HibernateDAO implements CustomerDao{

	@Override
	public List<Customer> getCustomerList(String n_id) {
		String hql = "from Customer c where c.valid='1' and c.neighbor.pid = "+n_id+" ";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<Customer> getCustomerListByID(String c_identify) {
		String hql = "from Customer c where c.valid='1' and c.customerId = :c_identify ";
		Query q = getSession().createQuery(hql);
		q.setString("c_identify", c_identify);
		return q.list();
	}

	@Override
	public List<Customer> getCustomerListByNLDH(String n_id, String lou, String dy,	String hu) {
		String hql = "from Customer c where c.valid='1' and c.neighbor.pid = :n_id and c.louNum = :lou and c.dyNum = :dy and c.huNum = :hu";
		Query q = getSession().createQuery(hql);
		q.setString("n_id", n_id);
		q.setString("lou", lou);
		q.setString("dy", dy);
		q.setString("hu", hu);
		return q.list();
	}
	
	@Override
	public List<Meter> getMeterListByCid(String cpid) {
		String hql = "from Meter m where m.valid='1' and m.customer.pid = :cpid";
		Query q = getSession().createQuery(hql).setString("cpid", cpid);
		return q.list();
	}

	@Override
	public int addCustomer(Customer c) {
		this.getHibernateTemplate().save(c);
		
//		String hql = "update Meter m set m.valid = 0 where m.pid = "+mid;
//		Query q = getSession().createQuery(hql);
//		q.executeUpdate();
		
		return c.getPid();
	}

	@Override
	public int addMeter(Meter m) {
		this.getHibernateTemplate().save(m);
		return m.getPid();
	}

	@Override
	public int deleteMeter(int mid) {
		String hql = "update Meter m set m.valid = 0 where m.pid = "+mid;
		Query q = getSession().createQuery(hql);
		
		return q.executeUpdate();
	}

	@Override
	public Customer getCustomerByPid(int cid) {
		String hql = "from Customer c where c.valid='1' and c.pid = "+cid;
		Query q = getSession().createQuery(hql);
		return (Customer) q.uniqueResult();
	}

	@Override
	public int deleteCustomer(int cid) {
		
		
		String hql = "update Customer c set c.valid = 0 where c.pid = "+cid;
		Query q = getSession().createQuery(hql);
		
		return q.executeUpdate();
	}

	@Override
	public int updateCustomer(Customer c) {
		
//		String hql = "update Customer c set customername = :name,customerMobile=:mobile,customerEmail=:email,NationalID=:nationalid where c.pid = :pid";
//		Query q = getSession().createSQLQuery(hql);
//		q.setString("name", c.getCustomerName());
//		q.setString("mobile", c.getCustomerMobile());
//		q.setString("email", c.getCustomerEmail());
//		q.setString("nationalid", c.getNationalId());
//		q.setInteger("pid", c.getPid());
//		return q.executeUpdate();
//		
//		try{
//			this.getHibernateTemplate().update(c);
//			return 1;
//		}catch(Exception e){
//			return 0;
//		}
		return 1;
	}
	
	@Override
	public int updateCustomerInfo(CustomerView c) {
		String hql = "update Customer c set c.customername = :name,c.customerMobile=:mobile,c.customerEmail=:email,c.NationalID=:nationalid where c.pid = :pid";
		Query q = getSession().createSQLQuery(hql);
		q.setString("name", c.getCustomerName());
		q.setString("mobile", c.getCustomerMobile());
		q.setString("email", c.getCustomerEmail());
		q.setString("nationalid", c.getNationalId());
		q.setInteger("pid", c.getPid());
		return q.executeUpdate();
	}
	
	@Override
	public Meter getMeterByPid(int mid) {
		String hql = "from Meter m where m.valid='1' and m.pid = "+mid;
		Query q = getSession().createQuery(hql);
		return (Meter) q.uniqueResult();
	}

	@Override
	public int updateMeter(Meter m) {
		try{
			this.getHibernateTemplate().update(m);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public int updatePrePaySign(int custId, byte prepaySign) {
		String hql = "update Customer c set c.prePaySign = :pps where c.pid=:pid";
		return getSession().createQuery(hql).setParameter("pps", prepaySign)
			.setParameter("pid", custId).executeUpdate();
	}

	@Override
	public Meter getMeterByGCM(int gid, String collectorAddr,
			String meterAddr) {
		String hql = "from Meter m " +
				"where m.valid='1' and m.gprs.pid = :gid and m.collectorAddr = :collectorAddr and m.meterAddr = :meterAddr";
		Query q = getSession().createQuery(hql);
		q.setInteger("gid", gid);
		q.setString("collectorAddr", collectorAddr);
		q.setString("meterAddr", meterAddr);
		return (Meter) q.uniqueResult();
	}

	@Override
	public Meter getMeterByMAddr(String meterAddr) {
		String hql = "from Meter m " +
				"where m.valid='1' and m.meterAddr = :meterAddr";
		Query q = getSession().createQuery(hql).setString("meterAddr", meterAddr);
		return (Meter) q.uniqueResult();
	}

	@Override
	public void updateCustomerBalance(BigDecimal b,Integer custId) {
		String hql = "update Customer c set c.customerBalance = c.customerBalance + :balence where c.pid=:custId";
		getSession().createQuery(hql).setBigDecimal("balence", b).setInteger("custId", custId).executeUpdate();
	}

	@Override
	public List<ControlWarnView> getCustomerOwe(int n_id, int pre, double low) {
		String SQL = "";
		if(pre == 2){
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.customerBalance <= :low and c.valid = 1";
		}else{
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.prepaySign = :pre and c.customerBalance <= :low and c.valid = 1";
		}
		
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("warnStyle",Hibernate.INTEGER)
				.addScalar("warnCount",Hibernate.STRING);

		q.setInteger("n_id", n_id);
		q.setDouble("low", low);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		
		q.setResultTransformer(Transformers.aliasToBean(ControlWarnView.class));
		
		return q.list();
	}

	@Override
	public List<ControlWarnView> getCustomerOwe(int n_id, String lou, int pre,
			double low) {
		String SQL = "";
		if(pre == 2){
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.LouNum = :lou and c.customerBalance <= :low and c.valid = 1";
		}else{
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.LouNum = :lou and c.prepaySign = :pre and c.customerBalance <= :low and c.valid = 1";
		}
		
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("warnStyle",Hibernate.INTEGER)
				.addScalar("warnCount",Hibernate.STRING);

		q.setInteger("n_id", n_id);
		q.setString("lou", lou);
		q.setDouble("low", low);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		
		q.setResultTransformer(Transformers.aliasToBean(ControlWarnView.class));
		
		return q.list();
	}

	@Override
	public List<ControlWarnView> getCustomerOwe(int n_id, String lou,
			String dy, int pre, double low) {
		String SQL = "";
		if(pre == 2){
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.LouNum = :lou and c.DYNum = :dy and c.customerBalance <= :low and c.valid = 1";
		}else{
			SQL = "select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign," +
					"c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre,c.warnStyle,maxwarn.warnCount from customer c " +
					"left join ( " +
					"select max(pid),customerid,count(*) warncount from warnlog " +
					"where valid = 1 " +
					"group by customerid " +
					") maxwarn " +
					"on maxwarn.customerid = c.pid " +
					"where c.neighborid = :n_id and c.LouNum = :lou and c.DYNum = :dy and c.prepaySign = :pre and c.customerBalance <= :low and c.valid = 1";
		}
		
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("warnStyle",Hibernate.INTEGER)
				.addScalar("warnCount",Hibernate.STRING);

		q.setInteger("n_id", n_id);
		q.setString("lou", lou);
		q.setString("dy", dy);
		q.setDouble("low", low);
		if(pre != 2){
			q.setInteger("pre", pre);
		}
		
		q.setResultTransformer(Transformers.aliasToBean(ControlWarnView.class));
		
		return q.list();
	}

	
	
}
