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
	public void deleteMeter(int cid) {
		
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

}
