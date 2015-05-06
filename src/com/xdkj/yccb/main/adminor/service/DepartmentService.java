package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.security.UserForSession;

public interface DepartmentService {
	
	List<DepartmentView> getList(DepartmentView depview,PageBase pageInfo);
	
	int getTotalCount(DepartmentView depview);
	
	String add( Department dep);
	
	String delete(String ids);
	
	List<NeighborView> getNbrByCurrUser(UserForSession u);

}
