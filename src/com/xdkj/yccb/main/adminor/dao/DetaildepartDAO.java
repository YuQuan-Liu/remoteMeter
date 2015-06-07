package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Detaildepart;

public interface DetaildepartDAO {
	List<Detaildepart> getListByDepId(int depId);
	
	int addDetaildepart(Detaildepart dpt);
	
	/**
	 * 添加片区时   添加片区内的小区信息
	 * @param dep
	 * @param nbr_ids
	 * @return
	 */
	int addDetaildeparts(Department dep, int[] nbr_ids);

	/**
	 * 删除片区内的指定小区
	 * @param dep_id
	 * @param n_id
	 * @return
	 */
	String deleteDetaildepart(int dep_id, int n_id);

	/**
	 * 获取片区中是否 包含了这个小区
	 * @param dep_id
	 * @param n_id
	 * @return
	 */
	Detaildepart getDetailBy(int dep_id, int n_id);

}
