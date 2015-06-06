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

	@Override
	public String checkPKname(String pkname, int wcid) {
		String sql = "from Pricekind pk where pk.valid='1' and pk.priceKindName = :pkname and pk.watercompany.pid = "+ wcid +" ";
		Query q = getSession().createQuery(sql);
		q.setString("pkname", pkname);
		
		if(null == q.uniqueResult()){
			return "false";
		}
		return "true";
	}

	@Override
	public String deletePK(int pid) {
		String sql = "update Pricekind set valid = 0 where pid = :pid";
		
		if(getSession().createQuery(sql).setInteger("pid", pid).executeUpdate() > 0){
			return "true";
		}
		return "false";
	}

	@Override
	public String changepk(int old_, int new_) {
		String sql = "update Meter set pricekindid = :new_ where pricekindid = :old_";
		
		if(getSession().createQuery(sql).setInteger("old_", old_).setInteger("new_", new_).executeUpdate() > 0){
			return "true";
		}
		return "false";
	}

	

}
