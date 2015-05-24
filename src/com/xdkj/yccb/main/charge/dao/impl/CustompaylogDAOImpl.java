package com.xdkj.yccb.main.charge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.entity.Customerpaylog;
@Repository
public class CustompaylogDAOImpl extends HibernateDAO<Customerpaylog> implements CustompaylogDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Customerpaylog> getList(int count, int custId) {
		String hql = "from Customerpaylog cpl where cpl.customer.pid=:pid order by cpl.actionTime desc ";
		return getSession().createQuery(hql).setParameter("pid", custId)
			.setFirstResult(0).setMaxResults(count).list();
	}

}
