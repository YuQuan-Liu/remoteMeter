package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.dto.SettleView;

public interface SettleService {

	List<SettleView> getSettleData(int n_id);

	String settleAll(int n_id,int adminid);

	String settleSingle(int m_id,int adminid);

	String getSettleLog(int n_id);

	List<SettledView> getSettleDataPostPay(int n_id, int settle_id);

	String getSettleLogAuto(int n_id);

	List<SettledView> getSettleAuto(int n_id, int settle_id);

	List<SettleSum> getSettleYL(int n_id);

	List<SettleSum> getSettleYL(int n_id, int settle_id);

}
