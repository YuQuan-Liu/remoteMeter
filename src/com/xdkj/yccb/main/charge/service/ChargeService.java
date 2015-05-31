package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.CustompaylogView;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;

public interface ChargeService {
	/**
	 * 通过小区id和用户id查询用户信息
	 * @param nbrId 小区id
	 * @param custId 用户信息
	 * @return 用户bean
	 */
	CustomerView getCustByNeibourAndCustId(String nbrId,String custId);
	/**
	 * 预后付费转换
	 * @param cstId
	 * @param prePaySign
	 * @return
	 */
	String updatePayment(String cstId,String prePaySign);
	/**
	 * Description: 收费记录
	 * @param custId
	 * @param count
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<CustompaylogView> getCList(String custId,int count);
	/**
	 * Description: 扣费记录
	 * @param custId
	 * @param count
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<MeterdeductionlogView> getMList(String custId,int count);
	/**
	 *  开关阀
	 * @param meterId 表id
	 * @param valueState 1 开阀
	 * @return
	 */
	String changeValue(String meterId,String valueState);
	/**
	 * 更新单价
	 * @param meterId 表id
	 * @param priceId 单价id
	 * @return
	 */
	String updatePrice(String meterId,String priceId);
	/**
	 * 撤销缴费
	 * Description: 
	 * @param custPayLogId 缴费记录id
	 * @return
	 * @author SongWei
	 * @date 2015-5-31
	 * @version 1.0
	 */
	String cancleCustPay(String custPayLogId);
	/**
	 * 撤销扣费
	 * Description: 
	 * @param meterDeLogId
	 * @return
	 * @author SongWei
	 * @date 2015-5-31
	 * @version 1.0
	 */
	String cancleCost(String meterDeLogId);

}
