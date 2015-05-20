package com.xdkj.yccb.main.readme;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;

@Component
public class ReadMeter {

	@Autowired
	private ReadLogDao readLogDao;
	@Autowired
	private NeighborDAO neighborDAO;
	/**
	 * 抄小区
	 * @param n
	 * @param admin
	 * @return
	 */
	public String readNeighbor(Neighbor n,Admininfo admin){
		
		JSONObject jo = new JSONObject();
		//查看当前用户是否有抄表在进行
//		ReadLogDao readLogDao = new ReadLogDaoImpl();
		List<Readlog> readlogs = readLogDao.findadminReading(admin.getPid());
		if(null != readlogs && readlogs.size() > 0){
			//有抄表任务在进行
			jo.put("function", "read");
			jo.put("pid", readlogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "reading");
			return jo.toJSONString();
		}
		
		//往readlog 中插入一条抄表记录  并且获的抄表的id
		Readlog readlog = new Readlog();
		readlog.setObjectId(n.getPid());
		readlog.setAdmininfo(admin);
		readlog.setReadType(3);
		readlog.setRemote(1);
		readlog.setReadObject(1);
		readlog.setIp(n.getIp());
		readlog.setStartTime(new Date());
		readlog.setReadStatus(0);
		readlog.setFailReason("");
		readlog.setSettle(0);
		readlog.setResult("");
		int readlogid = readLogDao.addReadLog(readlog);
		
		
		jo.put("function", "read");
		jo.put("pid", readlogid);
		
		//建立socket 连接  IP 为小区对应的抄表IP  端口 5555
		//发送json {"function":"read","pid":"12031"} 

		//等待返回     并往上反
		//{"function":"read","pid":"12031","result":"success"}  开始抄表
		//{"function":"read","pid":"12031","result":"fail"}  抄表失败
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			socket = new Socket(n.getIp().trim(), 5555);
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
			jo.put("function", "read");
			jo.put("pid", readlog.getPid());
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
		return jo.toJSONString();
	}

	/**
	 * 抄表
	 * @param meter
	 * @param admin
	 * @return
	 */
	public String readMeter(Meter meter, Admininfo admin) {
		
		JSONObject jo = new JSONObject();
		//查看当前用户是否有抄表在进行
//		ReadLogDao readLogDao = new ReadLogDaoImpl();
		List<Readlog> readlogs = readLogDao.findadminReading(admin.getPid());
		if(null != readlogs && readlogs.size() > 0){
			//有抄表任务在进行
			jo.put("function", "read");
			jo.put("pid", readlogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "reading");
			return jo.toJSONString();
		}
		
		//往readlog 中插入一条抄表记录  并且获的抄表的id
		Readlog readlog = new Readlog();
		readlog.setObjectId(meter.getPid());
		readlog.setAdmininfo(admin);
		readlog.setReadType(3);
		readlog.setRemote(1);
		readlog.setReadObject(3);
		readlog.setIp(meter.getNeighbor().getIp().trim());
		readlog.setStartTime(new Date());
		readlog.setReadStatus(0);
		readlog.setFailReason("");
		readlog.setSettle(0);
		readlog.setResult("");
		int readlogid = readLogDao.addReadLog(readlog);
		
		
		jo.put("function", "read");
		jo.put("pid", readlogid);
		
		//建立socket 连接  IP 为小区对应的抄表IP  端口 5555
		//发送json {"function":"read","pid":"12031"} 

		//等待返回     并往上反
		//{"function":"read","pid":"12031","result":"success"}  开始抄表
		//{"function":"read","pid":"12031","result":"fail"}  抄表失败
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
			jo.put("function", "read");
			jo.put("pid", readlog.getPid());
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
		return jo.toJSONString();
	}

	/**
	 * 这个代码是只有1个抄表监听时候的处理   如果是多个的时候  这个地方的代码需要更改
	 * @param admin
	 * @return
	 */
	public String readNeighbors(Admininfo admin) {
		//TODO
		JSONObject jo = new JSONObject();
		//查看当前用户是否有抄表在进行
		List<Readlog> readlogs = readLogDao.findadminReading(admin.getPid());
		if(null != readlogs && readlogs.size() > 0){
			//有抄表任务在进行
			jo.put("function", "read");
			jo.put("pid", readlogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "reading");
			return jo.toJSONString();
		}
		// 获取管理员下的所有的小区列表   将这些小区添加到ReadLog中   获取这批小区的第一个添加到ReadLog中的id 
		
		List<Neighbor> neighbors = null;
//		neighbors = neighborDAO.getList(admin.getDepartment().getPid(), admin.getWatercompany().getPid());
		if(null == admin.getDepartment()){
			//all the neighbor under the wc
			neighbors = neighborDAO.getNbrByWatcomId(admin.getWatercompany().getPid());
		}else{
			//all the neighbor under the depart
			neighbors = neighborDAO.getList(admin.getDepartment().getPid(), admin.getWatercompany().getPid());
		}
		
		if(neighbors == null && neighbors.size() == 0){
			jo.put("function", "read");
			jo.put("pid", readlogs.get(0).getPid());
			jo.put("result", "fail");
			jo.put("reason", "no neighbor");
			return jo.toJSONString();
		}
		
		Readlog readlog = null;
		Neighbor n = null;
		List<Readlog> readLogs = new ArrayList<>();
		for(int i = 0;i<neighbors.size();i++){
			n = neighbors.get(i);
			readlog = new Readlog();
			readlog.setObjectId(n.getPid());
			readlog.setAdmininfo(admin);
			readlog.setReadType(3);
			readlog.setRemote(1);
			readlog.setReadObject(2);
			readlog.setIp(n.getIp().trim());
			readlog.setStartTime(new Date());
			readlog.setReadStatus(0);
			readlog.setFailReason("");
			readlog.setSettle(0);
			readlog.setResult("");
			readLogs.add(readlog);
		}
		
		//返回第一条记录的pi
		int readlogid = readLogDao.addReadLogs(readLogs);
		
		jo.put("function", "read");
		jo.put("pid", readlogid);
		
		//建立socket 连接  IP 为小区对应的抄表IP  端口 5555
		//发送json {"function":"read","pid":"12031"} 

		//等待返回     并往上反
		//{"function":"read","pid":"12031","result":"success"}  开始抄表
		//{"function":"read","pid":"12031","result":"fail"}  抄表失败
		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			socket = new Socket(n.getIp(), 5555);
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
			jo.put("function", "read");
			jo.put("pid", readlog.getPid());
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
		return jo.toJSONString();
	}
}
