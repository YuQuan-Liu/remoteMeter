package com.xdkj.yccb.main.readme.dao;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettleView;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.statistics.dto.VIPMonitor;

public interface ReadMeterLogDao {

	Readmeterlog getMaxReadMeterLog(int m_id);

	Readmeterlog addReadMeterLog(Readmeterlog newlog);

	Map addReadMeterLogs(List<Readmeterlog> list);

	List<SettleView> getReadMeterLogToSettle(int n_id);

	SettleView getReadMeterLog(int m_id);

	List<SettleSum> getSettleSum(int n_id);

	List<SettleSum> getSettledSum(int n_id, int settle_id,int pre);

	List<SettleSum> getLouSettledSum(int n_id, int settle_id, int pre,
			String lou);

	/**
	 * 获取小区下  定时抄表的表（包括D10）  在month月内  每天最后一次抄表记录
	 * @param n_id
	 * @param month
	 * @return
	 */
	List<VIPMonitor> getVIPMonitor(int n_id, String month);

	/**
	 * 获取小区下  定时抄表的表（包括D10） 在start这天  每个小时最后一次抄表记录
	 * @param n_id
	 * @param start
	 * @return
	 */
	List<VIPMonitor> getVIPMonitorDay(int n_id, String start);

}
