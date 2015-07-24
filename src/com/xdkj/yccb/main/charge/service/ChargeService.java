package com.xdkj.yccb.main.charge.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xdkj.yccb.main.charge.dto.CustomerpaylogView;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;

public interface ChargeService {
	/**
	 * 预后付费转换
	 * @param cstId
	 * @param prePaySign
	 * @return
	 */
	String updatePayment(int cstId,int prePaySign);
	/**
	 * Description: 收费记录
	 * @param custId
	 * @param count
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<CustomerpaylogView> getCList(int custId,int count);
	/**
	 * Description: 扣费记录
	 * @param custId
	 * @param count
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<SettledView> getMList(int c_id,int count);
	
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
	/**
	 * 水费减免
	 * @param m_id
	 * @param waste
	 * @return
	 */
	String addwaterwaste(int m_id, int waste);
	/**
	 * 用户交费
	 * @param c_id
	 * @param amount
	 * @return 最新的用户余额
	 */
	String addpay(int adminid,int c_id, BigDecimal amount);
	/**
	 * 根据cplid  返回当前的用户信息金额信息  和cplid对应的交费信息
	 * @param cplid
	 * @return
	 */
	CustomerpaylogView getPaylog(int cplid);
	/**
	 * 根据交费记录  获取  本次交费记录  上一次交费记录  的信息  获取时间   打印详单使用
	 * @param cplid
	 * @return
	 */
	List<Customerpaylog> getPaylogLimit2(int cid,int cplid);
	/**
	 * 获取用户下  两条交费记录之间的扣费信息
	 * @param cid
	 * @param start
	 * @param end
	 * @return
	 */
	List<SettledView> getMeterDeLog(int cid, Date start, Date end);
	/**
	 * 当前表具今年对应的 全部扣费读数   用于画当前水表的扣费读数曲线
	 * @param mid
	 * @return
	 */
	String getDrawMeter(int mid);


	/**
	 * 获取当前交费记录之后的所有的交费之和  用在打印详单
	 * @param cid
	 * @param cplid
	 * @return
	 */
	public BigDecimal sumAfterPay(int cid, int cplid);
}
