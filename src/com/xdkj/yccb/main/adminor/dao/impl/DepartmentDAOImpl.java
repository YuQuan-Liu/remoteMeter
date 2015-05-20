package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.DepartmentDAO;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;
@Repository
public class DepartmentDAOImpl extends HibernateDAO<Department> implements DepartmentDAO {

	@Override
	public List<Department> getList(DepartmentView depv, PageBase pageInfo) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("from Department d where 1=1 ");
		/*if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}*/
		Query q = getSession().createQuery(sb.toString());
		q.setFirstResult((pageInfo.getPage()-1)*pageInfo.getRows());
		q.setMaxResults(pageInfo.getRows());
		return q.list();
	}

	@Override
	public Integer getTotalCount(DepartmentView depv) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Department d where 1=1 ");
		/*if(null!=adInfo.getAdminName()){
			sb.append(" and ai.adminName like %"+adInfo.getAdminName()+"%");
		}*/
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Department getById(Integer depId) {
		String hql = "from Admininfo a where a.pid=:pid ";
		Query q = getSession().createQuery(hql);
		q.setInteger("pid", depId);
		return (Department) q.uniqueResult();
	}

	@Override
	public int add(Department dep) {
		getHibernateTemplate().save(dep);
		return dep.getPid();
	}

	@Override
	public boolean deleteById(Integer depId) {
		return false;
	}

	@Override
	public boolean update(Department dep) {
		getHibernateTemplate().update(dep);
		return true;
	}

}
