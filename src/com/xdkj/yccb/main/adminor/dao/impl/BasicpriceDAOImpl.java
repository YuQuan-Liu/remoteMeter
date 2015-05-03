package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.BasicpriceDAO;
import com.xdkj.yccb.main.entity.Basicprice;
@Repository
public class BasicpriceDAOImpl extends HibernateDAO<Basicprice> implements BasicpriceDAO {

	@Override
	public List<Basicprice> getListByPriceKindId(int pkId) {
		String hql = "from Basicprice b where b.pricekind.pid=:pid and b.valid='1' ";
		return getSession().createQuery(hql).setParameter("pid", pkId).list();
	}

	@Override
	public int addBasicprice(Basicprice bp) {
		getHibernateTemplate().save(bp);
		return bp.getPid();
	}

	@Override
	public void delete(int bpId) {
		String hql = "update Basicprice bp set bp.valid='0' ";
		getSession().createQuery(hql).executeUpdate();
	}

}
