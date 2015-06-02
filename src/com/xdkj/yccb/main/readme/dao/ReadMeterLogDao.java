package com.xdkj.yccb.main.readme.dao;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettleView;
import com.xdkj.yccb.main.entity.Readmeterlog;

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

}
