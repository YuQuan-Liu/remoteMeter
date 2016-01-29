package com.xdkj.yccb.main.infoin.service;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.GprsView;
import com.xdkj.yccb.main.infoin.dto.MeterViewSimple;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.statistics.dto.ChargeRate;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.dto.SettledWaste;

public interface NeighborService {
	
	/*
	 * 根据片区id获取所有的小区的信息  
	 * 片区id==0  返回自来水公司下的所有的小区
	 */
	public List<NeighborView> getList(int depart_id,int wcid);
	
	String addNeighbor(int adid,int wcid,int depart_id,Neighbor nbr);
	
	String updateNeighbor(Admininfo admin,Neighbor nv);
	String deleteNbrById(int nbrId);
	int getCount(Neighbor nv, PageBase pb);
	
	Neighbor getNbrById(int nbrId);
	String addGprs(Gprs gprs);
	String updateGprs(Gprs gprs);
	Gprs getGprsById(int gprsId);
	String deleteGprsById(int gprsId);
	
	List<GprsView> getGprsByNbrId(int nbrId);
	List<NeighborBalance> getNeighborBalance(int n_id);
	/**
	 * 获取小区下的所有的楼号   将每个小区下的楼号  以lou为key 楼号为value存入map  便于json 数组转换 
	 * @param n_id
	 * @return
	 */
	List<Map> getLous(int n_id);
	List<Map> getDys(int n_id, String lou);
	/**
	 * 选出当前小区 本年度对应的所有的扣费记录对应抄表记录的水损分析   小区总表  每个月最后的扣费记录   画图
	 * @param n_id 
	 * @param year
	 * @return
	 */
	String getWaste(int n_id, int year);
	/**
	 * 选出当前管理员下的小区  year 对应的 每次结算的用水量和扣费情况
	 * @param neighbor_list
	 * @param year
	 * @return
	 */
	String getSettledwater(List<NeighborView> neighbor_list, int year);
	/**
	 *  当前小区  year 对应的全部结算过的用水统计  绘制曲线
	 * @param n_id
	 * @param year
	 * @return
	 */
	String getDrawSettledwater(int n_id, int year);
	/**
	 * 当前小区  本年度对应的所有的扣费记录对应抄表记录的水损分析   填表
	 * @param n_id
	 * @param year
	 * @return
	 */
	List<SettledWaste> getSettledyl(int n_id, int year);
	/**
	 * 当前管理员下的小区 year 对应的每个小区结算的信息和
	 * @param neighbor_list
	 * @param year
	 * @return
	 */
	String getSettledwaterN(List<NeighborView> neighbor_list, int year);
	/**
	 * 当前管理员下的小区 year  收费统计信息
	 * @param neighbor_list
	 * @param year
	 * @return
	 */
	List<ChargeRate> getChargeRate(List<NeighborView> neighbor_list, int year);
	/**
	 * 当前管理员下的小区 year  收费统计信息  echarts 数据
	 * @param neighbor_list
	 * @param year
	 * @return
	 */
	String getDrawChargerate(List<NeighborView> neighbor_list, int year);

	/**
	 * 根据自来水公司ID  和小区名获取 是否已经录入  
	 * @param waterComId
	 * @param n_name
	 * @return
	 *  数据库中包含  true
	 *  没有  false
	 */
	public String checkn_name(int waterComId, String n_name);

	/**
	 * 检测集中器是否已经录入
	 * @param gprsaddr
	 * @return
	 */
	public String checkGPRSAddr(String gprsaddr);

	/**
	 * 获取自来水公司下的所有小区
	 * @param waterComId
	 * @return
	 */
	public List<Neighbor> getallNbrBywcid(int waterComId);

	/**
	 * 列举出GPRS 下的所有的表具
	 * @param gid
	 * @return
	 */
	public List<MeterViewSimple> listgprsmeters(int gid);
	
}
