package com.xdkj.yccb.main.readme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.entity.Valvelog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;
import com.xdkj.yccb.main.readme.dao.ValveLogDao;

@Component
public class ValveControl {

	@Autowired
	private ValveLogDao valveLogDao;
	@Autowired
	private ValveConfLogDao valveConfLogDao;
	@Autowired
	private MeterDao meterDao;
	
	public String valveControl(Meter meter, Admininfo admin) {

		JSONObject jo = new JSONObject();
		if(meter.getIsValve() == 0){
			jo.put("function", "valve");
			jo.put("pid", "0");
			jo.put("result", "fail");
			jo.put("reason", "no valve");
			return jo.toJSONString();
		}
		//查看当前用户是否有抄表在进行
//		ReadLogDao readLogDao = new ReadLogDaoImpl();
		List<Valvelog> valvelogs = valveLogDao.findadminValveing(admin.getPid());
		if(null != valvelogs && valvelogs.size() > 0){
			//有抄表任务在进行
			jo.put("function", "valve");
			jo.put("pid", valvelogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "controling");
			return jo.toJSONString();
		}
		
		Valvelog valvelog = new Valvelog();
		valvelog.setActionCount(1);
		valvelog.setActionTime(new Date());
		valvelog.setAdmininfo(admin);
		valvelog.setAuto(0);
		valvelog.setCompleteCount(0);
		valvelog.setErrorCount(0);
		valvelog.setStatus(0);
		valvelog.setFailReason("");
		valvelog.setRemark("");
		
		valveLogDao.addValveLog(valvelog);
		
		Valveconflog conflog = new Valveconflog();
		conflog.setMeter(meter);
		conflog.setValvelog(valvelog);
		conflog.setSwitch_(meter.getValveState() != 0?0:1);
		conflog.setResult(0);
		conflog.setErrorReason("");
		conflog.setErrorStatus(0);
		conflog.setRemoveReason("");
		
		valveConfLogDao.addConfLog(conflog);
		
		return sendToReadMeter(meter, valvelog);
	}

	public String valveControlAll(Object[] ids, Admininfo admin) {
		
		JSONObject jo = new JSONObject();
		//查看当前用户是否有抄表在进行
//		ReadLogDao readLogDao = new ReadLogDaoImpl();
		List<Valvelog> valvelogs = valveLogDao.findadminValveing(admin.getPid());
		if(null != valvelogs && valvelogs.size() > 0){
			//有抄表任务在进行
			jo.put("function", "valve");
			jo.put("pid", valvelogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "controling");
			return jo.toJSONString();
		}
		
		Valvelog valvelog = new Valvelog();
		valvelog.setActionCount(ids.length);
		valvelog.setActionTime(new Date());
		valvelog.setAdmininfo(admin);
		valvelog.setAuto(0);
		valvelog.setCompleteCount(0);
		valvelog.setErrorCount(0);
		valvelog.setStatus(0);
		valvelog.setFailReason("");
		valvelog.setRemark("");
		
		valveLogDao.addValveLog(valvelog);
		valveConfLogDao.addConfLogs(ids,valvelog);
		
		Meter meter = meterDao.getMeterByID(Integer.parseInt(ids[0].toString()));

		
		return sendToReadMeter(meter, valvelog);
	}

	private String sendToReadMeter(Meter meter,Valvelog valvelog){
		JSONObject jo = new JSONObject();
		jo.put("function", "valve");
		jo.put("pid", valvelog.getPid());
		
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			socket = new Socket(meter.getNeighbor().getIp(), 5555);
			socket.setSoTimeout(3000);
			out = socket.getOutputStream();
			in = socket.getInputStream();
			
			out.write((jo.toJSONString()+"\r\n").getBytes());
			
			byte[] result = new byte[256];
			int count = 0;
			while((count = in.read(result)) > 0){
				return new String(result, 0, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
			valveLogDao.updateControlException(valvelog, e);
			jo.put("function", "valve");
			jo.put("pid", valvelog.getPid());
			jo.put("result", "fail");
			jo.put("reason", "error");
		} finally{
			try {
				if(in != null){
					in.close();
				}
				if(null != out){
					out.close();
				}
				if(null != socket){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
