package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.DetaildepartDAO;
import com.xdkj.yccb.main.entity.Detaildepart;
@Repository
public class DetaildepartDAOImpl extends HibernateDAO<Detaildepart> implements
		DetaildepartDAO {

	@Override
	public List<Detaildepart> getListByDepId(int depId) {
		String hql = "from Detaildepart dpt where dpt.valid='1' and dpt.department.pid=:pid";
		return getSession().createQuery(hql).setParameter("pid", depId).list();
	}

	@Override
	public int addDetaildepart(Detaildepart dpt) {
		getHibernateTemplate().save(dpt);
		return dpt.getPid();
	}

	@Override
	public void deleteDetaildepart(int dptId) {

	}

	@Override
	public void deleteBatch(List<Integer> dpIds) {
		String hql = "update Detaildepart dpt set dpt.valid='0' where dpt.pid in (:pids)";
		getSession().createQuery(hql).setParameterList("pids", dpIds).executeUpdate();
	}

}
