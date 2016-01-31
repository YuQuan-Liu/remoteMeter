package com.xdkj.yccb.main.readme.dao;

import java.util.Date;
import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Meter;

public interface MeterDao {

	public Meter getMeterByID(int m_id);
	/**
	 * 根据水表的APID 在n_id 下查找表具
	 * @param apid
	 * @param n_id
	 * @return
	 */
	public Meter getMeterByAPID(String apid, int n_id);
	
	public void updateMeterRead(int m_id, int type, int m_read);
	
	int updateMeterPrice(int meterId,int priceId);

	/**
	 * 更新m_id的扣费读数为deread
	 * @param m_id
	 * @param deread
	 * @return
	 */
	public int updateDeread(int m_id, int deread);

	/**
	 * 撤销扣费使用   更新meter的deread、detime 为上次的读数
	 * @param m_id
	 * @param deread
	 * @param detime
	 * @return
	 */
	public int updateDeread(int m_id, int deread,Date detime);
	/**
	 * 获取全部需要定时抄表的表
	 * @return
	 */
	public List<Meter> getTimerList();
	
	//sList<Meter> getList(SettlementView sv,PageBase pageInfo);
}
