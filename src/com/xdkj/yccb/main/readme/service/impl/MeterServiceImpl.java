package com.xdkj.yccb.main.readme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.service.MeterService;

@Service
public class MeterServiceImpl implements MeterService {

	@Autowired
	private MeterDao meterDao;
	
	@Override
	public Meter getMeterbyPID(String m_id) {
		return meterDao.getMeterByID(Integer.parseInt(m_id));
	}

}
