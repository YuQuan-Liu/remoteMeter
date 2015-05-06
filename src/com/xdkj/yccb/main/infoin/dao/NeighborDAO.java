package com.xdkj.yccb.main.infoin.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.NeighborView;

public interface NeighborDAO {
	
	List<Neighbor> getList(NeighborView nbr,PageBase pb);
	
	int addNeighbor(Neighbor nbr);
	
	int getTotalCount(Neighbor nv, PageBase pb);
	
	Neighbor getById(int pid);
	
	void update (Neighbor nbr);
	
	void delete(String ids);
	/**
	 * 通过自来水公司id获取小区集合
	 * @param wcId 自来水公司id
	 * @return
	 */
	List<Neighbor> getNbrByWatcomId(int wcId);

	
}
