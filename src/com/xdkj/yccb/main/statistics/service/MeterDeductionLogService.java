package com.xdkj.yccb.main.statistics.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.PostCharge;
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
}
