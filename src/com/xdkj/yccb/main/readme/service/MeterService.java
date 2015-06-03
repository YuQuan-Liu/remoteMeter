package com.xdkj.yccb.main.readme.service;


import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readmeterlog;

public interface MeterService {

	public Meter getMeterbyPID(String m_id);

	public String addMeterRead(int m_id, int m_read);

	public Map addMeterReads(List<Readmeterlog> list);

	/**
	 * 小区这个月month对应下的  所有检测表的每日最后一次的数据
	 * @param n_id
	 * @param month
	 * @return
	 */
	public String getVIPMonitor(int n_id, String month);
	
	
	
}
