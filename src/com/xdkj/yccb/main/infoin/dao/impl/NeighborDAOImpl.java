package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
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
	
	public List<Neighbor> getList(int depart_id,int wcid) {
		String hql = "";
		if(depart_id == 0){
			//获取自来水下全部小区
			hql = "from Neighbor nbr where nbr.valid='1' and wcid = "+ wcid +" ";
		}else{
			//获取片区下的全部小区
			hql = "select neighbor from Detaildepart detail where detail.valid='1' and detail.department.pid = "+depart_id+" and detail.neighbor.valid = 1 ";
		}

		Query q = getSession().createQuery(hql);
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
		sb.append("select count(*) from Neighbor nbr where nbr.valid='1' ");
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

	@SuppressWarnings("rawtypes")
	@Override
	public void delete(String ids) {
		String[] id = ids.split(",");
		List<Integer> idlist = new ArrayList<Integer>();
		for (String str : id) {
			idlist.add(Integer.parseInt(str));
			}
		Query q = this.getSession().createQuery("from Neighbor nbr where nbr.pid in(:ids)");
		q.setParameterList("ids", idlist);
		Iterator nbr = q.list().iterator();
		while(nbr.hasNext()){
			Neighbor nbor = (Neighbor) nbr.next();
			nbor.setValid("0");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Neighbor> getNbrByWatcomId(int wcId) {
		String hql = "from Neighbor nbr where nbr.valid='1' and nbr.watercompany.pid=:pid";
		return getSession().createQuery(hql).setParameter("pid", wcId).list();
	}

	@Override
	public Neighbor getNbrByWcIdName(String wcid, String n_name) {
		String hql = "from Neighbor nbr where nbr.watercompany.pid= "+wcid+" and nbr.neighborName = '"+ n_name+"' and nbr.valid = 1";
		Query q = getSession().createQuery(hql);
		return (Neighbor) q.uniqueResult();
	}

	@Override
	public List<NeighborBalance> getNeighborBalance(int n_id) {
		
		String SQL = "select case when prepaysign = 1 then '预'else '后' end pre,sum(customerBalance) balance from customer c " +
				"where c.neighborid = :n1 and valid = 1 " +
				"group by prepaysign " +
				"union " +
				"select '合计' pre,sum(customerBalance) balance from customer c " +
				"where c.neighborid = :n1 and valid = 1";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pre", Hibernate.STRING).addScalar("balance", Hibernate.BIG_DECIMAL);
		q.setResultTransformer(Transformers.aliasToBean(NeighborBalance.class));
		q.setInteger("n1", n_id);
//		q.setInteger("n2", n_id);
		List<NeighborBalance> list = new ArrayList<>();
		
		try {
			list = q.list();
		} catch (HibernateException e) {
//			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<String> getLous(int n_id) {
		String sql = "select distinct louNum lou from customer where valid = 1 and neighborid = :n_id";
		Query q = getSession().createSQLQuery(sql).addScalar("lou", Hibernate.STRING);
		q.setInteger("n_id", n_id);
		return q.list();
	}

	@Override
	public List<String> getDys(int n_id, String lou) {
		String sql = "select distinct dyNum dy from customer where valid = 1 and neighborid = :n_id and louNum = :lou";
		Query q = getSession().createSQLQuery(sql).addScalar("dy", Hibernate.STRING);
		q.setInteger("n_id", n_id);
		q.setString("lou", lou);
		return q.list();
	}

}
