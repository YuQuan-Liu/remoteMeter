package com.xdkj.yccb.main.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	

	

}
