package com.xdkj.yccb.usermanage.dao;

import com.xdkj.yccb.usermanage.entity.Users;

public interface UserDao {
	/**
	 * 获取通过用户账户获取用户信息
	 * @param uname
	 * @return
	 */
	public Users findByUname(String uname);

}
