package com.xdkj.yccb.main.readme.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.statistics.dto.VIPMonitor;

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
	public Meter getMeterbyAPID(String apid, int n_id) {
		return meterDao.getMeterByAPID(apid,n_id);
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

	@Override
	public String getVIPMonitor(int n_id, String start,int module) {
		String result = "";
		switch (module){
		case 1:
			//月
			result = getEchartDataMonth(n_id,start);
			break;
		case 2:
			//日
			result = getEchartDataDay(n_id,start);
			break;
		}
		
		return result;
	}

	private String getEchartDataDay(int n_id, String start) {
		List<VIPMonitor> list = readMeterLogDao.getVIPMonitorDay(n_id,start);
		JSONArray ja = new JSONArray();
		
		VIPMonitor vip = null;
		int mid = 0;
		JSONObject jo = null;
		JSONArray ja_data = null;
		
		for(int i = 0;i<list.size();i++){
			vip = list.get(i);
			if(mid != vip.getM_id()){
				mid = vip.getM_id();
				jo = new JSONObject();
				ja_data = new JSONArray();
				for(int j = 0;j < 24;j++){
					ja_data.add(j, 0);
				}
				jo.put("id", vip.getM_id());
				jo.put("meteraddr", vip.getMeterAddr());
				jo.put("data", ja_data);
				ja.add(jo);
			}
			ja_data.set(vip.getDay(),vip.getReaddata());
		}
		return ja.toJSONString();
	}

	private String getEchartDataMonth(int n_id, String start) {
		List<VIPMonitor> list = readMeterLogDao.getVIPMonitor(n_id,start);
		JSONArray ja = new JSONArray();
		
		VIPMonitor vip = null;
		int mid = 0;
		JSONObject jo = null;
		JSONArray ja_data = null;
		
		for(int i = 0;i<list.size();i++){
			vip = list.get(i);
			if(mid != vip.getM_id()){
				mid = vip.getM_id();
				jo = new JSONObject();
				ja_data = new JSONArray();
				for(int j = 0;j < 31;j++){
					ja_data.add(j, 0);
				}
				jo.put("id", vip.getM_id());
				jo.put("meteraddr", vip.getMeterAddr());
				jo.put("data", ja_data);
				ja.add(jo);
			}
			ja_data.set(vip.getDay()-1,vip.getReaddata());
		}
		return ja.toJSONString();
	}

}
