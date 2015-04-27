package com.xdkj.yccb.main.infoin.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;

public interface NeighborDAO {
	
	List<Neighbor> getList(Neighbor nbr,PageBase pb);
	
	int addNeighbor(Neighbor nbr);
	
	int getTotalCount(Neighbor nbr);
	
	Neighbor getById(int pid);
	
	void update (Neighbor nbr);
}
