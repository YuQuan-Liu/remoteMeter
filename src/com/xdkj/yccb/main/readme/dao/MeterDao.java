package com.xdkj.yccb.main.readme.dao;

import com.xdkj.yccb.main.entity.Meter;

public interface MeterDao {

	public Meter getMeterByID(int m_id);

	public void updateMeterRead(int m_id, int i, int m_read);
}
