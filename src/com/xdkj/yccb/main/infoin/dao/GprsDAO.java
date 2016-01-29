package com.xdkj.yccb.main.infoin.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.infoin.dto.MeterViewSimple;

public interface GprsDAO {
	/**
	 * 通过小区id获取集中器
	 * @param nbrid
	 * @return 集中器列表
	 */
	List<Gprs> getListByNeighborId(int nbrid);
	
	Gprs getById(int gprsId);
	
	int addGprs (Gprs gprs);
	
	void deleteGprs (int gprsId);
	
	void update (Gprs gprs);
	
	void deleteByNbrId(String nbrId);

	Gprs getByAddr(String g_addr);

	/**
	 * 获取系统中所有的需要阀门清理的集中器
	 * @return
	 */
	List<Gprs> getCleanList();

	/**
	 * 根据GPRSid 获取集中器下的所有表的采集器、表地址、mid
	 * @param gid
	 * @return
	 */
	List<MeterViewSimple> listgprsmeters(int gid);
}
