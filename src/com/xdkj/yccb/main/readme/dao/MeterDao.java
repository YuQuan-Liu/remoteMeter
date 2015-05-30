package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.charge.dto.SettlementView;
import com.xdkj.yccb.main.entity.Meter;

public interface MeterDao {

	public Meter getMeterByID(int m_id);

	public void updateMeterRead(int m_id, int type, int m_read);
	/**
	 *  开关阀
	 * @param meterId 表id
	 * @param valueState 阀门状态
	 */
	void changeValue(int meterId,byte valueState);
	
	void updateMeterPrice(int meterId,int priceId);
	
	List<Meter> getList(SettlementView sv,PageBase pageInfo);
}
