package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.security.UserForSession;

public interface DepartmentService {
	
	List<DepartmentView> getList(DepartmentView depview,PageBase pageInfo);
	
	int getTotalCount(DepartmentView depview);
	/**
	 * 添加片区
	 * @param dep 片区信息
	 * @param nbrIds 小区ids
	 * @return succ or fail
	 */
	String add( Department dep,String [] nbrIds);
	
	String delete(String ids);
	
	String getNbrByCurrUser(UserForSession u,String depId);
	
	Department getById(String depId);
	
	String update(Department dep,String [] nbrIds);

}
