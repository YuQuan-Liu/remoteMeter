package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.PriceKindDAO;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Pricekind;
@Repository
public class PriceKindDAOImpl extends HibernateDAO<Pricekind> implements PriceKindDAO {

	@Override
	public List<Pricekind> getList(PriceKindView pkv, PageBase pb) {
		StringBuffer sb = new StringBuffer();
		sb.append("from Pricekind pk where 1=1 ");
		/*if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}*/
		Query q = getSession().createQuery(sb.toString());
		q.setFirstResult((pb.getPage()-1)*pb.getRows());
		q.setMaxResults(pb.getRows());
		return q.list();
	}
	
	@Override
	public List<Pricekind> getList(int wcid) {
		String hql = "from Pricekind pk where Valid = 1 and wcid = "+wcid;
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public Integer getTotalCount(PriceKindView pkv) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Pricekind pk where 1=1 ");
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Integer addPriceKind(Pricekind pk) {
		getHibernateTemplate().save(pk);
		return pk.getPid();
	}

	@Override
	public boolean update(Pricekind pk) {
		getHibernateTemplate().update(pk);
		return false;
	}

	@Override
	public Pricekind getById(int pid) {
		return (Pricekind) getSession().createQuery("from Pricekind pk where pk.pid=:pid ")
				.setParameter("pid", pid).uniqueResult();
	}

	

}
