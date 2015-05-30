package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.SettleView;

public interface SettleService {

	List<SettleView> getSettleData(int n_id);

	String settleAll(int n_id,int adminid);

	String settleSingle(int m_id,int adminid);

}
