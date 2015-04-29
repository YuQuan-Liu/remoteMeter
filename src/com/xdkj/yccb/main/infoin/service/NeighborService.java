package com.xdkj.yccb.main.infoin.service;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;

public interface NeighborService {
	List<NeighborView> getList(NeighborView nv,PageBase pb);
	
	String addNeighbor(Neighbor nbr);
	
	String updateNeighbor(Neighbor nv);
	String deleteNbrById(String nbrId);
	int getCount(Neighbor nv, PageBase pb);
	
	Neighbor getNbrById(int nbrId);
	String addGprs(Gprs gprs);
	String updateGprs(Gprs gprs);
	Gprs getGprsById(int gprsId);
	String deleteGprsById(String gprsId);
	
	List<GprsView> getGprsByNbrId(int nbrId);

	

}
