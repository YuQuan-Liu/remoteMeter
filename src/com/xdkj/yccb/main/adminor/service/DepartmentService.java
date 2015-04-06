package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;

public interface DepartmentService {
	
	List<DepartmentView> getList(DepartmentView depview,PageBase pageInfo);
	
	int getTotalCount(DepartmentView depview);
	
	String add( Department dep);
	
	String delete(String ids);

}
