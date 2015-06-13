package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Meter;

public interface MeterDao {

	public Meter getMeterByID(int m_id);

	public void updateMeterRead(int m_id, int type, int m_read);
	
	int updateMeterPrice(int meterId,int priceId);

	public int updateDeread(int m_id, int waste);

	/**
	 * 获取全部需要定时抄表的表
	 * @return
	 */
	public List<Meter> getTimerList();
	
	//sList<Meter> getList(SettlementView sv,PageBase pageInfo);
}
