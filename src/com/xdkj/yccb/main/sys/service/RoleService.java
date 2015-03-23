package com.xdkj.yccb.main.sys.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dto.RoleView;

public interface RoleService {
	List<RoleView> getList(RoleView rv,PageBase pb);
	
	int getCount(RoleView rv,PageBase pb);
	
	String addRole(Roles r);
	
	String delete(Roles r);
	

}
