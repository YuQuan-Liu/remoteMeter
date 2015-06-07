package com.xdkj.yccb.main.adminor.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.security.UserForSession;

public interface DepartmentService {
	
	
	/**
	 * 获取自来水公司下的所有的片区
	 * @param wcid
	 * @return
	 */
	List<DepartmentView> getList(int wcid);
	
	int getTotalCount(DepartmentView depview);
	/**
	 * 添加片区
	 * @param dep 片区信息
	 * @param nbrIds 小区ids
	 * @return succ or fail
	 */
	String add(int wcid,String name,String remark,int[] nbr_ids);
	
	
	Department getById(String depId);
	
	String checkdepname(int wcid, String name);

	/**
	 * 删除片区
	 * @param pid
	 * @return
	 */
	String deletedep(int pid);

	/**
	 * 删除片区下的小区
	 * @param dep_id
	 * @param n_id
	 * @return
	 */
	String deletedetail(int dep_id, int n_id);

	/**
	 * 添加片区下的小区
	 * @param dep_id
	 * @param n_id
	 * @return
	 */
	String adddetail(int dep_id, int n_id);


}
