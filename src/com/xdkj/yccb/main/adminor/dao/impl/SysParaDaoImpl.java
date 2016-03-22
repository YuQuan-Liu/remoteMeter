package com.xdkj.yccb.main.adminor.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.SysParaDao;
import com.xdkj.yccb.main.entity.SysPara;

@Repository
public class SysParaDaoImpl extends HibernateDAO<SysPara> implements SysParaDao {

	@Override
	public String getValue(String name) {
		
		String hql = "from SysPara sys where sys.name = :name";
		Query q = getSession().createQuery(hql).setString("name", name);
		SysPara sys = (SysPara) q.uniqueResult();
		if(sys == null){
			return "";
		}
		return sys.getValue();
	}

}
