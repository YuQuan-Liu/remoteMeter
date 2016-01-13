package com.xdkj.yccb.main.statistics.service;

import java.util.Collection;
import java.util.List;

import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.QYDetail;
import com.xdkj.yccb.main.charge.dto.QYMeters;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;

/**
 * 
 * Description: 结算用水统计业务逻辑
 * @author SongWei
 * @date 2015-5-23
 * @version 1.0
 */
public interface MeterDeductionLogService {
	
	public List<MeterdeductionlogView> getMeterDeductionLog(String id);

	public String chargepostpay(int adminid, int[] mdl_ids);

	public List<PostCharge> getPostCharge(String ids);

	public List<PostCharge> getPostChargeLou(int n_id, int settle_id, String lou);

	/**
	 * 根据扣费ids  获取所有的用户的信息
	 * @param ids
	 * @return
	 */
	public List<Customer> getCustomers(String ids);

	/**
	 * 更加用户id 和settlelogid  获取用户下的所有表settlelogid对应的扣费信息
	 * @param cid
	 * @param settlelogid
	 * @return
	 */
	public List<QYMeters> getMeters(int cid, int settlelogid);

	/**
	 * 根据用量和pkid  获取基本单价的detail 企业打印使用
	 * @param pkid
	 * @param yl
	 * @return
	 */
	public List<QYDetail> getDetails(int pkid, int yl);
}
