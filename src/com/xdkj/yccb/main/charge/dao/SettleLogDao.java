package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Settlelog;

public interface SettleLogDao {

	List<Settlelog> getTodaySettleLog(int n_id);

	void settleAll(int n_id, int adminid, int readlogid);

	Settlelog getLastSettleLog(int n_id);

	void settleSingle(int m_id, int adminid, int settlelogid);

	String getSettleLogs(int n_id);

	String getSettleLogsAuto(int n_id);

}
