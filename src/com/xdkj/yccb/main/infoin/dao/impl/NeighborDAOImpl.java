package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
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
	
	public List<Neighbor> getList(int depart_id,int wcid) {
		String hql = "";
		if(depart_id == 0){
			//获取自来水下全部小区
			hql = "from Neighbor nbr where nbr.valid='1' and wcid = "+ wcid +" ";
		}else{
			//获取片区下的全部小区
			hql = "select neighbor from Detaildepart detail where detail.valid='1' and detail.neighbor.valid='1' and detail.department.pid = "+depart_id+" ";
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

}
