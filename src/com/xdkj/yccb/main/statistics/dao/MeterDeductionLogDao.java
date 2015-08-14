package com.xdkj.yccb.main.statistics.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.charge.dto.MeterDereadMonth;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.entity.Meterdeductionlog;

/**
 * 
 * Description: 结算用水统计——连接数据库操作
 * @author SongWei
 * @date 2015-5-23
 * @version 1.0
 */
public interface MeterDeductionLogDao {
	Meterdeductionlog getMeterDeductionLog(int mdl_id);
	
	

	Meterdeductionlog getById( Integer mdlId);
	
	void updateMeterductionLog(Meterdeductionlog mdl);
	
	List<SettledView> getLogPostPay(int n_id, int settle_id,String lou);
	
	List<SettledView> getLogAuto(int n_id, int settle_id);
	
	/**
	 * 对后付费用户进行交费处理
	 * @param adminid
	 * @param mdl_ids
	 */
	void chargepostpay(int adminid, int[] mdl_ids);
	
	/**
	 * 获取后付费用户的扣费信息  进行打印
	 * @param ids
	 * @return
	 */
	List<PostCharge> getPostCharge(String ids);
	
	/**
	 * 获取后付费用户的扣费信息 根据楼  &&  demoney>0  进行打印
	 * @param n_id
	 * @param settle_id
	 * @param lou
	 * @return
	 */
	List<PostCharge> getPostChargeLou(int n_id, int settle_id, String lou);
	
	/**
	 * 获取当前结算  的所有记录显示  根据pre 
	 * @param n_id
	 * @param settle_id
	 * @param pre  2表示全部
	 * @return
	 */
	List<SettledView> getLogAll(int n_id, int settle_id, int pre);
	List<SettledView> getLouLogAll(int n_id, int settle_id, int pre, String lou);
	/**
	 * 根据最新的扣费记录  添加水费减免
	 * @param m_id
	 * @param waste
	 * @return
	 */
	int addWaste(int m_id, int waste);
	/**
	 * 获取最新的扣费记录
	 * @param mid
	 * @return
	 */
	Meterdeductionlog getLastmdl(int mid);
	/**
	 * 获取当前用户下的  前count条扣费记录
	 * @param c_id
	 * @param count
	 * @return
	 */
	List<SettledView> getLogDetail(int c_id, int count);
	/**
	 * 当前用户 start  到  end 期间的所有的扣费记录
	 * @param cid
	 * @param start
	 * @param end
	 * @return
	 */
	List<SettledView> getLogDetail(int cid, Date start, Date end);

	List<MeterDereadMonth> getMeterDeread(int mid);



}
