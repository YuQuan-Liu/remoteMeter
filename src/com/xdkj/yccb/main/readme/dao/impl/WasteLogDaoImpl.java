package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.readme.dao.WasteLogDao;
import com.xdkj.yccb.main.readme.dto.WasteReadView;

@Repository
public class WasteLogDaoImpl extends HibernateDAO implements WasteLogDao {

	@Override
	public List<WasteReadView> getWasteByReadlogid(int readlogid) {
		
		String SQL = "select w.pid,w.louNum,w.meterRead,w.salveSum,w.waste,wd.wasted from wastelog w " +
				"left join ( " +
				"select meterid,sum(waste) wasted from wastelog " +
				"where meterid in( " +
				"select meterid from wastelog " +
				"where readlogid = "+readlogid+" ) and valid = 1 " +
				"group by meterid " +
				") wd " +
				"on w.meterid = wd.meterid " +
				"where readlogid = "+readlogid;
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pid",Hibernate.INTEGER)
				.addScalar("louNum",Hibernate.STRING)
				.addScalar("meterRead",Hibernate.INTEGER)
				.addScalar("salveSum",Hibernate.INTEGER)
				.addScalar("waste",Hibernate.INTEGER)
				.addScalar("wasted",Hibernate.INTEGER);
		
		q.setResultTransformer(Transformers.aliasToBean(WasteReadView.class));
		return q.list();
		
	}

	@Override
	public void addWaste(int wid, String reason) {
		String hql = "update Wastelog w set w.valid = '1',w.remark=:reason where w.pid = "+wid;
		Query q = getSession().createQuery(hql);
		q.setString("reason", reason);
		q.executeUpdate();
		
	}

}
