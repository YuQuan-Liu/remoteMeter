package com.xdkj.yccb.main.charge.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xdkj.yccb.main.charge.dto.CustomerpaylogView;
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
	List<CustomerpaylogView> getList(int count,int custId);
	
	
	public List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre);


	List<AdminSum> getAdminSum(int n_id, String start, String end, int pre);
	
	Customerpaylog getById(Integer id);
	
	void updateCustLog(Customerpaylog c);


	/**
	 * 添加用户交费记录
	 * @param amount
	 * @param c_id
	 * return 返回添加的cpl 的ID
	 */
	int addPaylog(int adminid,BigDecimal amount, int c_id);

	/**
	 * 根据用户的交费记录id  返回用于交费单据中使用
	 * @param cplid
	 * @return
	 */
	CustomerpaylogView getViewById(int cplid);

	/**
	 * 获取当前cplid 和cplid 前一条记录
	 * @param cplid
	 * @return
	 */
	List<Customerpaylog> getPaylogLimit2(int cid,int cplid);

	/**
	 * 获取当前交费记录之后的所有的交费之和  用在打印详单
	 * @param cid
	 * @param cplid
	 * @return
	 */
	BigDecimal getsumAfterPay(int cid, int cplid);
}
