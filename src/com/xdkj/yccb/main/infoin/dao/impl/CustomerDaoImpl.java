package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;

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
		String hql = "from Customer c where c.valid='1' and c.customerId = "+c_identify+" ";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<Customer> getCustomerListByNLDH(String n_id, String lou, String dy,	String hu) {
		String hql = "from Customer c where c.valid='1' and c.neighbor.pid = "+n_id+" and c.louNum = "+lou+" and c.dyNum = "+dy+" and c.huNum = "+hu;
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public List<Meter> getMeterListByCid(String cpid) {
		String hql = "from Meter m where m.valid='1' and m.customer.pid = "+cpid;
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public int addCustomer(Customer c) {
		this.getHibernateTemplate().save(c);
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
		try{
			this.getHibernateTemplate().update(c);
			return 1;
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public Customer getCustByNborOrCust(int nbrId, String cust) {
		String hql = "from Customer c where c.valid='1' and c.neighbor.pid=:pid "
				+ "and (c.customerId=:cust0 or c.customerName=:cust1 )";
		@SuppressWarnings("unchecked")
		List<Customer> list = getSession().createQuery(hql).setParameter("pid", nbrId)
		.setParameter("cust0", cust).setParameter("cust1", cust).list();
		if(null!=list&&list.size()==1){
			return list.get(0);
		}
		return null;
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
	public void updatePrePaySign(int custId, byte prepaySign) {
		String hql = "update Customer c set c.prePaySign = :pps where c.pid=:pid";
		getSession().createQuery(hql).setParameter("pps", prepaySign)
			.setParameter("pid", custId).executeUpdate();
	}

	
}
