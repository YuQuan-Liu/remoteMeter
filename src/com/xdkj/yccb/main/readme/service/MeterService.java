package com.xdkj.yccb.main.readme.service;


import com.xdkj.yccb.main.entity.Meter;

public interface MeterService {

	public Meter getMeterbyPID(String m_id);

	public String addMeterRead(int m_id, int m_read);
	
	
	
}
