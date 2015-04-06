package com.xdkj.yccb.main.adminor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.DepartmentDAO;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Department;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentDAO departmentDAO;

	@Override
	public List<DepartmentView> getList(DepartmentView depview,
			PageBase pageInfo) {
		List<Department> list = departmentDAO.getList(depview, pageInfo);
		List<DepartmentView> listView = new ArrayList<DepartmentView>();
		for (Department department : list) {
			DepartmentView dv = new DepartmentView();
			dv.setPid(department.getPid());
			dv.setDepartmentName(department.getDepartmentName());
			dv.setRemark(department.getRemark());
			dv.setValid(department.getValid());
			listView.add(dv);
		}
		return listView;
	}

	@Override
	public int getTotalCount(DepartmentView depview) {
		return departmentDAO.getTotalCount(depview);
	}

	@Override
	public String add(Department dep) {
		if(departmentDAO.add(dep)>0){
			return "succ";
		}else{
			return "fail";
		}
	}

	@Override
	public String delete(String ids) {
		
		return null;
	}

}
