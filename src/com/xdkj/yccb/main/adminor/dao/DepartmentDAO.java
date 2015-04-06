package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;

public interface DepartmentDAO {
	
	/**
	 * 获取管理员列表
	 * @return
	 */
	List<Department> getList(DepartmentView depv,PageBase pageInfo);
	/**
	 * 获取总记录数
	 * @param adInfo
	 * @return
	 */
	Integer getTotalCount(DepartmentView depv);
	/**
	 * 通过id获取管理员信息
	 * @param adminId
	 * @return
	 */
	Department getById(Integer depId);
	/**
	 * 添加管理员
	 * @param adminInfo
	 * @return id
	 */
	int add(Department dep);
	/**
	 * 通过id删除管理员
	 * @param adminId
	 * @return
	 */
	boolean deleteById(Integer depId);
	/**
	 * 更新管理员信息
	 * @param adminInfo
	 * @return
	 */
	boolean update (Department dep);

}
