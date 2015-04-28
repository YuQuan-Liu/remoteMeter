package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
@Repository
public class NeighborDAOImpl extends HibernateDAO<Neighbor> implements NeighborDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Neighbor> getList(NeighborView nbr, PageBase pb) {
		String hql = "from Neighbor nbr where nbr.valid='1' ";
		Query q = getSession().createQuery(hql);
		q.setFirstResult((pb.getPage()-1)*pb.getRows());
		q.setMaxResults(pb.getRows());
		return q.list();
	}

	@Override
	public int addNeighbor(Neighbor nbr) {
		this.getHibernateTemplate().save(nbr);
		return nbr.getPid();
	}

	@Override
	public int getTotalCount(Neighbor nbr,PageBase pb) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Neighbor nbr where 1=1 ");
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Neighbor getById(int pid) {
		String hql = "from Neighbor nbr where nbr.pid= "+pid;
		Query q = getSession().createQuery(hql);
		return (Neighbor) q.uniqueResult();
	}

	@Override
	public void update(Neighbor nbr) {
		this.getHibernateTemplate().update(nbr);
	}

}
