package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.QYSettledView;
import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.dto.SettleView;

public interface SettleService {

	/**
	 * 结算界面的信息
	 * @param n_id
	 * @return
	 */
	List<SettleView> getSettleData(int n_id);

	String settleAll(int n_id,int adminid);

	String settleSingle(int m_id,int adminid);

	/**
	 * 小区对应的所有的扣费记录
	 * @param n_id
	 * @return
	 */
	String getSettleLog(int n_id);

	/**
	 * 阀控自动扣费的记录
	 * @param n_id
	 * @return
	 */
	String getSettleLogAuto(int n_id);

	/**
	 * 结算对应的后付费用户的信息   在后付费界面使用
	 * @param n_id
	 * @param settle_id
	 * @return
	 */
	List<SettledView> getSettledDataPostPay(int n_id, int settle_id,String lou);

	/**
	 * 阀控表自动结算过的信息
	 * @param n_id
	 * @param settle_id
	 * @return
	 */
	List<SettledView> getSettledAuto(int n_id, int settle_id);

	/**
	 * 结算界面   本次结算的用量为多少
	 * @param n_id
	 * @return
	 */
	List<SettleSum> getSettleYL(int n_id);

	/**
	 * 本次结算对应的用量为多少   根据结算时的付费方式   
	 * @param n_id
	 * @param settle_id
	 * @param pre 2表示全部
	 * @return
	 */
	List<SettleSum> getSettledYL(int n_id, int settle_id,int pre);

	/**
	 * 扣费统计   结算对应的信息
	 * @param n_id
	 * @param settle_id
	 * @param pre 2表示全部
	 * @return
	 */
	List<SettledView> getSettledAll(int n_id, int settle_id, int pre);

	List<SettledView> getLouSettledAll(int n_id, int settle_id, int pre, String lou);

	List<SettleSum> getLouSettledYL(int n_id, int settle_id, int pre, String lou);

	/**
	 * 楼宇统计时   选择全部用户统计时  统计出  预后付费的户数、用水量、金额
	 * @param n_id
	 * @param settle_id
	 * @param pre
	 * @param lou
	 * @return
	 */
	List getLouSettledYLCount(int n_id, int settle_id, String lou);

	/**
	 * 获取小区名为企业下的 本次扣费记录的用量统计
	 * @param n_id
	 * @param settle_id
	 * @return
	 */
	List<QYSettledView> getQYSettledAll(int n_id, int settle_id);

}
