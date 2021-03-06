package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.WaterCompanyDAO;
import com.xdkj.yccb.main.entity.Watercompany;
@Repository
public class WaterCompanyDAOImpl extends HibernateDAO<Watercompany> implements WaterCompanyDAO {

	@Override
	public List<Watercompany> getList(Watercompany watcom, PageBase pageInfo) {
		String hql = "from Watercompany wc ";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public Integer getTotalCount(Watercompany watcom) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) from Watercompany ");
		Session session = getSession();
		Query q = session.createQuery(hql.toString());
		int count = ((Number)q.uniqueResult()).intValue();
		releaseSession(session);//关闭session
		return count;
	}

	@Override
	public Integer addWatcom(Watercompany watcom) {
		getHibernateTemplate().save(watcom);
		return watcom.getPid();
	}

	@Override
	public Watercompany getById(Integer pid) {
		return getHibernateTemplate().get(Watercompany.class, pid);
	}

	@Override
	public String updateWC(Watercompany watcom) {
		getSession().update(watcom);
		return "succ";
	}

}
