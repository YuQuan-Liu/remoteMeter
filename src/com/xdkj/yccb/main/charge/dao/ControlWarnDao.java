package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.dto.WarnPostPay;
import com.xdkj.yccb.main.entity.Customer;

public interface ControlWarnDao {

	List<ControlWarnView> getControlWarns(int n_id);

	void addWarnLog(Customer c, boolean done,String failReason,String cid);

	/**
	 * 查询一个手机号码今天是否可以发送     >3次  不可发送
	 * @param mobile
	 * @return
	 */
	boolean todaySend(String mobile);

	/**
	 * 后付费用户短信发送 存数据库
	 * @param warnPostPay
	 * @param done
	 * @param failReason
	 */
	void addWarnLog(WarnPostPay warnPostPay, boolean done, String failReason);

	/**
	 * 根据手机号 添加通知日志
	 * @param mobile
	 * @param done
	 * @param failReason
	 * @param cid
	 */
	void addWarnLog(String mobile, boolean done, String failReason, String cid);
}
