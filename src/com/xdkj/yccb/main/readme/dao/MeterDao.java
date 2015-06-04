package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Meter;

public interface MeterDao {

	public Meter getMeterByID(int m_id);

	public void updateMeterRead(int m_id, int type, int m_read);
	
	int updateMeterPrice(int meterId,int priceId);
	
	//sList<Meter> getList(SettlementView sv,PageBase pageInfo);
}
