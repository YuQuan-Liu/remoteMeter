package com.xdkj.yccb.main.infoin.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.statistics.dto.ChargeRate;
import com.xdkj.yccb.main.statistics.dto.MonthSettled;
import com.xdkj.yccb.main.statistics.dto.MonthWaste;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.dto.SettledWaste;
import com.xdkj.yccb.main.statistics.dto.SettledWater;
import com.xdkj.yccb.main.statistics.dto.SettledWaterN;

public interface NeighborDAO {
	
	/*
	 * 根据片区id获取所有的小区的信息  
	 * 片区id==0  返回自来水公司下的所有的小区
	 */
	public List<Neighbor> getList(int depart_id,int wcid);
	
	int addNeighbor(Neighbor nbr);
	
	int getTotalCount(Neighbor nv, PageBase pb);
	
	Neighbor getById(int pid);
	
	void update (Neighbor nbr);
	
	void delete(int id);
	/**
	 * 通过自来水公司id获取小区集合
	 * @param wcId 自来水公司id
	 * @return
	 */
	List<Neighbor> getNbrByWatcomId(int wcId);

	Neighbor getNbrByWcIdName(String wcid, String n_name);

	List<NeighborBalance> getNeighborBalance(int n_id);

	List<String> getLous(int n_id);

	List<String> getDys(int n_id, String lou);

	List<MonthWaste> getWaste(int n_id, int year);

	List<SettledWater> getSettledWater(String ids, int year);

	List<MonthSettled> getMonthSettled(int n_id, int year);

	List<SettledWaste> getSettledyl(int n_id, int year);

	List<SettledWaterN> getSettledWaterN(String ids, int year);

	List<ChargeRate> getChargeRate(String ids, int year);

	public String checkn_name(int wcid, String n_name);

	public String checkGPRSAddr(String gprsaddr);

	/**
	 * 获取全部需要定时抄表的小区
	 * @return
	 */
	public List<Neighbor> getTimerList();

	/**
	 * 获取小区所在自来水  pid 最靠前的管理员   用于自动抄表启动时
	 * @param pid
	 * @return
	 */
	public Admininfo getAdmin(Neighbor n);

	
}
