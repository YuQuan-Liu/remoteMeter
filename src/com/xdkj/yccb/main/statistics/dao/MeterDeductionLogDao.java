package com.xdkj.yccb.main.statistics.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Meterdeductionlog;

/**
 * 
 * Description: 结算用水统计——连接数据库操作
 * @author SongWei
 * @date 2015-5-23
 * @version 1.0
 */
public interface MeterDeductionLogDao {
	List<Meterdeductionlog> getMeterDeductionLog(String id);
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
}
