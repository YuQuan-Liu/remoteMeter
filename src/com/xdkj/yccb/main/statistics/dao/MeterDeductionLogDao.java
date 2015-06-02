package com.xdkj.yccb.main.statistics.dao;

import java.util.List;
import java.util.Map;

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
	/**
	 * Description: 扣费信息  目前显示最新10条
	 * @param count 查询显示的条数
	 * @param custId 用户id
	 * @return 扣费记录
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	List<Meterdeductionlog> getList(int count,int custId);
	

	Meterdeductionlog getById( Integer mdlId);
	
	void updateMeterductionLog(Meterdeductionlog mdl);
	
	List<SettledView> getLogPostPay(int n_id, int settle_id);
	
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
	 * 获取当前结算  的所有记录显示  根据pre 
	 * @param n_id
	 * @param settle_id
	 * @param pre  2表示全部
	 * @return
	 */
	List<SettledView> getLogAll(int n_id, int settle_id, int pre);
}
