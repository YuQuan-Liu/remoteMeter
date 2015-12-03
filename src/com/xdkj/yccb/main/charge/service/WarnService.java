package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.dto.WarnPostPay;
import com.xdkj.yccb.main.entity.Customer;

public interface WarnService {

	List<ControlWarnView> getControlWarns(int n_id);

	void addWarnSingle(Customer c, boolean done,String failReason);

	/**
	 * 查询一个手机号码今天是否可以发送     >3次  不可发送
	 * @param mobile
	 * @return
	 */
	boolean todaySend(String mobile);

	/**
	 * 后付费发送短信存数据库
	 * @param warnPostPay
	 * @param done
	 * @param re
	 */
	void addWarnSinglePostPay(WarnPostPay warnPostPay, boolean done, String failReason);
}
