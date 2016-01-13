package com.xdkj.yccb.main.statistics.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.QYDetail;
import com.xdkj.yccb.main.charge.dto.QYMeters;
import com.xdkj.yccb.main.entity.Customer;
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

	@Override
	public List<PostCharge> getPostChargeLou(int n_id, int settle_id, String lou) {
		
		return meterDeductionLogDao.getPostChargeLou(n_id,settle_id,lou);
	}

	@Override
	public List<Customer> getCustomers(String ids) {
		
		return meterDeductionLogDao.getCustomers(ids);
	}

	@Override
	public List<QYMeters> getMeters(int cid, int settlelogid) {
		
		return meterDeductionLogDao.getMeters(cid,settlelogid);
	}

	@Override
	public List<QYDetail> getDetails(int pkid, int yl) {
		
		return meterDeductionLogDao.getDetails(pkid,yl);
	}

}
