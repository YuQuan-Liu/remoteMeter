package com.xdkj.yccb.usermanage.dao.impl;

import java.util.List;


import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.usermanage.dao.UserDao;
import com.xdkj.yccb.usermanage.entity.Users;

@Repository
public class UserDaoImpl extends HibernateDAO implements UserDao {

	@Override
	public Users findByUname(String uname) {
		List<Users> list = this.getHibernateTemplate().find("from Users where uname='"+uname+"'");
		if(null!=list&&list.size()>0){
			Users u = list.get(0);
			return u;
		}
		return null;
	}

}
