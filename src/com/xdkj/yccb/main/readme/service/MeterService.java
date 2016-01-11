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
	 * 根据显示模式  显示出start时间对应的 小区下的所有的检测表的数据   和 D10的数据
	 * module == 1(月模式，即start月内每天的最后一次数据)
	 * module == 2(日模式，即start那一天每个小时内最后一次数据)
	 * @param n_id
	 * @param start
	 * @param module
	 * @return
	 */
	public String getVIPMonitor(int n_id, String start,int module);

	/**
	 * 根据表的apid 在n_id 下查找对应表具
	 * @param apid
	 * @param n_id
	 * @return
	 */
	public Meter getMeterbyAPID(String apid, int n_id);
	
	
	
}
