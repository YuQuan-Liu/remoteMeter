package com.xdkj.yccb.main.readme.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;
import com.xdkj.yccb.main.readme.service.MeterService;

@Service
public class MeterServiceImpl implements MeterService {

	@Autowired
	private MeterDao meterDao;
	@Autowired
	private ReadMeterLogDao readMeterLogDao;
	@Override
	public Meter getMeterbyPID(String m_id) {
		return meterDao.getMeterByID(Integer.parseInt(m_id));
	}

	@Override
	public String addMeterRead(int m_id, int m_read) {

		//获取当前表最新一次的抄表记录
		Readmeterlog readmeterlog = readMeterLogDao.getMaxReadMeterLog(m_id);
		
		Readmeterlog newlog = new Readmeterlog();
		newlog.setActionTime(new Date());
		newlog.setMeter(readmeterlog.getMeter());
		newlog.setRemark("");
		newlog.setActionResult(m_read);
		newlog.setActionType((byte)5);
		newlog.setReadlog(readmeterlog.getReadlog());
		
		readMeterLogDao.addReadMeterLog(newlog);
		meterDao.updateMeterRead(m_id,5,m_read);
		
		return "{\"id\":"+newlog.getPid()+",\"read\":"+newlog.getActionResult()+"}";
	}

	@Override
	public Map addMeterReads(List<Readmeterlog> list) {
		
		return readMeterLogDao.addReadMeterLogs(list);
	}

}
