package com.xdkj.yccb.main.readme.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.entity.Valvelog;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;
import com.xdkj.yccb.main.readme.dao.ValveLogDao;
import com.xdkj.yccb.main.readme.dao.WasteLogDao;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.dto.WasteReadView;
import com.xdkj.yccb.main.readme.service.ReadService;

@Service
public class ReadServiceImpl implements ReadService {

	@Autowired
	private ReadDao readDao;
	@Autowired
	private ReadLogDao readLogDao;
	@Autowired
	private ReadMeterLogDao readMeterLogDao;
	@Autowired
	private NeighborDAO neighborDao;
	
	@Autowired
	private ValveLogDao valveLogDao;
	@Autowired
	private ValveConfLogDao valveConfLogDao;
	@Autowired
	private MeterDao meterDao;
	@Autowired
	private WasteLogDao wasteLogDao;
	
	
	@Override
	public List<ReadView> getRemoteMeters(String n_id) {
		
		List<ReadView> list = readDao.getRemoteMeters(n_id);
		return list;
	}
	
	@Override
	public List<ReadView> getNonRemoteMeters(String n_id) {
		List<ReadView> list = readDao.getNonRemoteMeters(n_id);
		return list;
	}
	
	@Override
	public String checkReading(int readlogid, int adminid) {
		Readlog readlog = readLogDao.getReadLogByID(readlogid);
		JSONObject jo = new JSONObject();
		boolean done = true;
		if(readlog.getReadObject() == 2){
			//抄全部表
			//
			List<Readlog> list = readLogDao.getReadLogNeighbors(readlogid, adminid);
			Readlog readlog_ = null;
			for(int i = 0;list!= null && i < list.size();i++){
				readlog_ = list.get(i);
				if(readlog_.getReadStatus() == 0){
					done = false;
					break;
				}
			}
			if(!done){
				jo.put("pid", readlog.getPid());
				jo.put("readStatus", 0);
				jo.put("failReason", "");
				jo.put("result", "");
			}else{
				String failreason = "";
				String result = "";
				for(int i = 0;list!= null && i < list.size();i++){
					readlog_ = list.get(i);
					failreason += (readlog_.getFailReason()+"\r\n");
					result += (readlog_.getResult()+"\r\n");
				}
				jo.put("pid", readlog.getPid());
				jo.put("readStatus", 100);
				jo.put("failReason", failreason);
				jo.put("result", result);
			}
		}else{ 
			if(readlog.getReadObject() == 1){
				//抄单个小区
				jo.put("pid", readlog.getPid());
				jo.put("readStatus", readlog.getReadStatus());
				jo.put("failReason", readlog.getFailReason());
				jo.put("result", readlog.getResult());
			}else{
				//抄单个表 
				jo.put("pid", readlog.getPid());
				jo.put("readStatus", readlog.getReadStatus());
				jo.put("failReason", readlog.getFailReason());
				jo.put("result", readlog.getResult());
				Readmeterlog readmeterlog = readMeterLogDao.getMaxReadMeterLog(readlog.getObjectId());
				jo.put("read", readmeterlog.getActionResult());
				jo.put("time", readmeterlog.getActionTime().toLocaleString());
				jo.put("status", readmeterlog.getActionType());
			}
			
		}
		jo.put("objectid", readlog.getObjectId());
		jo.put("readobject", readlog.getReadObject());
		return jo.toJSONString();
	}

	@Override
	public String checkControling(int valvelogid, int adminid) {
		Valvelog valvelog = valveLogDao.getValveLogByID(valvelogid);
		JSONObject jo = new JSONObject();
		if(valvelog.getActionCount() == 1){
			//只操作一个阀门
			Valveconflog conflog = valveConfLogDao.getConfLogByLogID(valvelog.getPid());
			jo.put("switch_", conflog.getSwitch_());
		}
		jo.put("completecount", valvelog.getCompleteCount());
		jo.put("errorcount", valvelog.getErrorCount());
		jo.put("status", valvelog.getStatus());
		jo.put("failreason", valvelog.getFailReason());
		return jo.toJSONString();
	}

	@Override
	public String addNonRemoteRead(int m_id, int newread, int readlogid) {
		Readmeterlog newlog = new Readmeterlog();
		newlog.setActionResult(newread);
		newlog.setActionTime(new Date());
		newlog.setActionType((byte) 5);
		newlog.setMeter(meterDao.getMeterByID(m_id));
		newlog.setReadlog(readLogDao.getReadLogByID(readlogid));
		newlog.setRemark("");
		
		meterDao.updateMeterRead(m_id, 5, newread);
		
		readMeterLogDao.addReadMeterLog(newlog);
		JSONObject jo = new JSONObject();
		jo.put("actionResult", newlog.getActionResult());
		jo.put("actionTime", newlog.getActionTime().toLocaleString());
		return jo.toJSONString();
	}

	@Override
	public String showWaste(int readlogid) {
		List<WasteReadView> list = wasteLogDao.getWasteByReadlogid(readlogid);
		return JSON.toJSONString(list);
	}

	@Override
	public void addWaste(int wid, String reason) {
		wasteLogDao.addWaste(wid,reason);
	}

	
	
}
