package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;


import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.entity.Admininfo;
@Repository
public class AdministratorDAOImpl extends HibernateDAO<Admininfo> implements AdministratorDAO {

	@Override
	public List<Admininfo> getList(int wcid) {
		
		Query q = getSession().createQuery("from Admininfo a where a.watercompany.pid = :wcid and a.valid = 1 ");
		q.setInteger("wcid", wcid);
		return q.list();
	}

	@Override
	public List<AdminInfoView> getListView(int wcid) {
		Query q = getSession().createSQLQuery("select ad.*,DepartmentName depName from admininfo ad " +
				"left join department d " +
				"on ad.DepartmentID = d.pid " +
				"where ad.WCID = :wcid and ad.valid = 1 ")
				.addScalar("pid", Hibernate.INTEGER)
				.addScalar("depName", Hibernate.STRING)
				.addScalar("adminName", Hibernate.STRING)
				.addScalar("loginName", Hibernate.STRING)
				.addScalar("adminEmail", Hibernate.STRING)
				.addScalar("adminAddr", Hibernate.STRING)
				.addScalar("adminMobile", Hibernate.STRING)
				.addScalar("adminTel", Hibernate.STRING);
		q.setInteger("wcid", wcid);
		q.setResultTransformer(Transformers.aliasToBean(AdminInfoView.class));
		return q.list();
	}
	
	@Override
	public Admininfo getById(Integer adminId) {
		String hql = "from Admininfo a where a.pid=:pid ";
		Query q = getSession().createQuery(hql);
		q.setInteger("pid", adminId);
		return (Admininfo) q.uniqueResult();
	}

	@Override
	public int addAdmin(Admininfo adminInfo) {
		getHibernateTemplate().save(adminInfo);
		return adminInfo.getPid();
	}

	@Override
	public boolean removeById(Integer adminId) {
		String hql = "update Admininfo a set a.valid='0' where a.pid = :adminId";
		if(getSession().createQuery(hql).setInteger("adminId", adminId).executeUpdate()>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean update(Admininfo adminInfo) {
		getHibernateTemplate().merge(adminInfo);
		return false;
	}

	@Override
	public Integer getTotalCount(Admininfo adInfo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Admininfo ai where 1=1 ");
		if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Admininfo getByLoginName(String loginName, String password) {
		Query q = getSession().createQuery("from Admininfo a where a.loginName=:loginName and a.loginKey=:password and a.valid = 1");
		q.setString("loginName", loginName);
		q.setString("password", password);
		return (Admininfo) q.uniqueResult();
	}

	@Override
	public String checkLoginName(String name) {
		Query q = getSession().createQuery("from Admininfo a where a.loginName = :name ");
		q.setString("name", name);
		if(q.list().size() > 0){
			return "true";
		}
		return "false";
	}


}
