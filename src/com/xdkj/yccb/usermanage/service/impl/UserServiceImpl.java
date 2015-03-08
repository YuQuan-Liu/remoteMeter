package com.xdkj.yccb.usermanage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.usermanage.dao.UserDao;
import com.xdkj.yccb.usermanage.entity.Users;
import com.xdkj.yccb.usermanage.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Override
	public Users findByUname(String uname) {
		Users u = userDao.findByUname(uname);
		return u;
	}

}
