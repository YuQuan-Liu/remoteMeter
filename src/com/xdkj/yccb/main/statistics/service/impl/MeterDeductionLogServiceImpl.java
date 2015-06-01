package com.xdkj.yccb.main.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;
import com.xdkj.yccb.main.statistics.service.MeterDeductionLogService;
@Service
public class MeterDeductionLogServiceImpl implements MeterDeductionLogService {
	
	@Autowired 
	private MeterDeductionLogDao meterDeductionLogDao;
	
	@Override
	public List<MeterdeductionlogView> getMeterDeductionLog(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String chargepostpay(int adminid, int[] mdl_ids) {
		JSONObject jo = new JSONObject();
		meterDeductionLogDao.chargepostpay(adminid,mdl_ids);
		jo.put("done", true);
		return jo.toJSONString();
	}

	@Override
	public List<PostCharge> getPostCharge(String ids) {
		
		return meterDeductionLogDao.getPostCharge(ids);
	}

	

	

}
