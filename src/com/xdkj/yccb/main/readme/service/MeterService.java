package com.xdkj.yccb.main.readme.service;


import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readmeterlog;

public interface MeterService {

	public Meter getMeterbyPID(String m_id);

	public String addMeterRead(int m_id, int m_read);

	public Map addMeterReads(List<Readmeterlog> list);
	
	
	
}
