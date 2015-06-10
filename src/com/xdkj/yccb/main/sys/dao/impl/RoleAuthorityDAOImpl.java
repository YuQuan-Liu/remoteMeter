package com.xdkj.yccb.main.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dao.RoleAuthorityDAO;
@Repository
public class RoleAuthorityDAOImpl extends HibernateDAO<RoleAuthority> implements RoleAuthorityDAO {

	@Override
	public void delete(int pid,List<String> delete) {
		String hql = "delete from RoleAuthority r where r.roles.pid = :pid and r.authority.pid in (:pids)";
		Query q = getSession().createQuery(hql);
		List<Integer> delete_int = new ArrayList<>();
		
		for(int i = 0;i < delete.size();i++){
			delete_int.add(Integer.parseInt(delete.get(i)));
		}
		
		q.setParameterList("pids", delete_int);
		q.setInteger("pid", pid);
		q.executeUpdate();
	}

	@Override
	public void add(int pid, List<String> add) {
		Roles r = new Roles();
		r.setPid(pid);
		for(int i = 0;i < add.size();i++){
			RoleAuthority ra = new RoleAuthority();
			
			Authority a = new Authority();
			a.setPid(Integer.parseInt(add.get(i)));
			
			ra.setAuthority(a);
			ra.setRoles(r);
			getSession().save(ra);
		}
		
	}

	
}
