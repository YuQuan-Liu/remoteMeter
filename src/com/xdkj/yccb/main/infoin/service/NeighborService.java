package com.xdkj.yccb.main.infoin.service;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;

public interface NeighborService {
	
	List<NeighborView> getList(NeighborView nv,PageBase pb);
	/*
	 * 根据片区id获取所有的小区的信息  
	 * 片区id==0  返回自来水公司下的所有的小区
	 */
	public List<NeighborView> getList(int depart_id,int wcid);
	
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
	List<NeighborBalance> getNeighborBalance(int n_id);
	/**
	 * 获取小区下的所有的楼号   将每个小区下的楼号  以lou为key 楼号为value存入map  便于json 数组转换 
	 * @param n_id
	 * @return
	 */
	List<Map> getLous(int n_id);
	List<Map> getDys(int n_id, String lou);
	
}
