package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadDao {

	List<ReadView> getRemoteMeters(String n_id);

	List<ReadView> getNonRemoteMeters(String n_id);

	List<ReadView> getAllRemoteMeters(List<Integer> nid_list);

	/**
	 * 烟台  wcid = 2导出时的数据
	 * @param nid_list
	 * @return
	 */
	List<ReadView> getYT2Data(List<Integer> nid_list);
	

}
