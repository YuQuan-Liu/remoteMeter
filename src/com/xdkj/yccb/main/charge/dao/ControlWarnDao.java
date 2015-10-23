package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;

public interface ControlWarnDao {

	List<ControlWarnView> getControlWarns(int n_id);

	void addWarnLog(Customer c, boolean done,String failReason);

	/**
	 * 查询一个手机号码今天是否可以发送     >3次  不可发送
	 * @param mobile
	 * @return
	 */
	boolean todaySend(String mobile);
}
