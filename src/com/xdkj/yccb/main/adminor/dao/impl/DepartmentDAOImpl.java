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
	public List<Department> getList(int wcid) {
		
		String sql = "from Department d where d.watercompany.pid = :wcid and d.valid = 1 ";
		Query q = getSession().createQuery(sql);
		q.setInteger("wcid", wcid);
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
		String hql = "from Department d where d.pid=:pid ";
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

	@Override
	public String checkdepname(int wcid, String name) {
		
		String sql = "from Department d where d.watercompany.pid = :wcid and d.departmentName=:name and d.valid = 1 ";
		if(null == getSession().createQuery(sql).setInteger("wcid", wcid).setString("name", name).uniqueResult()){
			return "false";
		}
		return "true";
	}

	@Override
	public String deletedep(int pid) {
		String sql = "update Department d set d.valid = 0 where d.pid = :pid";
		if(getSession().createQuery(sql).setInteger("pid", pid).executeUpdate() > 0){
			return "true";
		}
		return "false";
	}

}
