package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.statistics.dto.AdminSum;
import com.xdkj.yccb.main.statistics.dto.PayInfo;

public interface CustompaylogDAO {
	/**
	 * Description: 缴费信息 目前显示最新10条
	 * @param count 显示条数
	 * @param custId 用户id
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<Customerpaylog> getList(int count,int custId);
	
	
	public List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre);


	List<AdminSum> getAdminSum(int n_id, String start, String end, int pre);
	
	Customerpaylog getById(Integer id);
	
	void updateCustLog(Customerpaylog c);
}
