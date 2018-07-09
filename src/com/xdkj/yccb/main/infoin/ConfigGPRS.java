package com.xdkj.yccb.main.infoin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.StringUtil;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.readme.dto.Frame;

public class ConfigGPRS {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigGPRS.class);
	private static final int METER_BATCH = 3;
	/**
	 * 登录集中器
	 * @param s
	 * @param out
	 * @param in
	 * @param gprsaddr
	 * @return
	 */
	private static boolean loginListener(Socket s, OutputStream out, InputStream in, byte[] gprsaddr) {
		byte[] data = new byte[100];
		boolean good = false;

		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_LINE), 
				Frame.AFN_LOGIN, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x01, gprsaddr, new byte[0]);
		logger.info("login listener : "+ login.toString());
		//~~~~~~~~~~~~~~~~~~~~~~~登录监听服务器
		try {
			out.write(login.getFrame());
			s.setSoTimeout(6000);  //6s内接收服务器的返回
			int count = 0;
			while((count = in.read(data, 0, 100)) > 0){
				logger.info("login listener receive : "+ StringUtil.byteArrayToHexStr(data, count));
				break;
			}
			
			//6s内收到监听返回  判断是否是ack
			if(Frame.checkFrame(Arrays.copyOf(data, 17))){
				Frame login_result = new Frame(Arrays.copyOf(data, 17));
				logger.info("login listener result: "+ login_result.toString());
				if(login_result.getFn() == 0x01){  //online  集中器在线
					good = true;
				}
			}
		} catch (Exception e) {
			logger.error("loginListener error ! gprsaddr: "+gprsaddr, e);
		}
		return good;
	}

	/**
	 * 查询底层类型
	 * @param gprs
	 * @return
	 */
	public static String querygprsslave(Gprs gprs) {
		
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x0C, gprsaddr, new byte[0]);
			logger.info("query gprs slave : "+ query.toString());
			try {
				out.write(query.getFrame());
				s.setSoTimeout(10000);  //等待集中器收到的回应
				byte[] data = new byte[100];
				int count = 0;
				while((count = in.read(data, 0, 100)) > 0){
					logger.info("query gprs slave receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 18))){
					logger.info("query gprs slave result : "+ StringUtil.byteArrayToHexStr(data, 18));
					if(data[15] == (byte)0xAA){
						jo.put("slave", "MBUS");
					}
					if(data[15] == (byte)0xBB){
						jo.put("slave", "采集器");
					}
					if(data[15] == (byte)0xFF){
						jo.put("slave", "485");
					}
					done = true;
				}else{
					done = false;
					reason = "帧异常";
				}
			} catch (IOException e) {
				done = false;
				reason = "接收应答失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		return jo.toString();
	}

	/**
	 * 设置集中器底层类型
	 * @param g
	 * @param gprsslave
	 * @return
	 */
	public static String configgprsslave(Gprs gprs, int gprsslave) {
		
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		JSONObject jo = new JSONObject();
		
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			
			/************************************操作**************************************/
			byte[] framedata = new byte[1];
			switch (gprsslave){
			case 1:
				framedata[0]=(byte) 0xAA; //MBUS
				break;
			case 2:
				framedata[0]=(byte) 0xFF; //485
				break;
			case 3:
				framedata[0]=(byte) 0xBB; //CJQ
				break;
				default:
					framedata[0]=(byte) 0xBB; //默认CJQ
					break;
			}
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x0C, gprsaddr, framedata);
			logger.info("config gprs slave : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[100];
				int count = 0;
				while((count = in.read(data, 0, 100)) > 0){
					logger.info("config gprs slave receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					logger.info("config gprs slave result : "+ StringUtil.byteArrayToHexStr(data, 17));
					if(data[14] == (byte)0x01){
						done = true;
					}else{
						done = false;
						reason = "帧异常";
					}
				}else{
					done = false;
					reason = "帧异常";
				}
				
			} catch (IOException e) {
				done = false;
				reason = "接收应答失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		return jo.toString();
		
	}
	
	/**
	 * 查询采集器
	 * @param g
	 * @return
	 */
	public static String querycjqs(Gprs gprs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		String result = "";
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x07, gprsaddr, new byte[0]);
			logger.info("query cjq : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[512];
				int count = 0;  //记录收到了的数据个数
				while((count = in.read(data, 0, 512)) > 0){
					logger.info("query cjq receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, count))){
					logger.info("query cjq result : "+ StringUtil.byteArrayToHexStr(data, count));
					String show = "";
					for(int i = 0;i < (count-8-9)/6;i++){
						byte[] cjqaddrbytes = new byte[6];
						for(int k = 0;k < 6;k++){
							cjqaddrbytes[5-k] = data[15+6*i+k];
						}
						//get the addr 
						String cjqaddrstr = "";
						for(int k = 0;k < 6;k++){
							cjqaddrstr = cjqaddrstr+String.format("%02x", cjqaddrbytes[k]&0xFF)+" ";
						}
						show = show + cjqaddrstr+"\r\n,";
					}
					done = true;
					result = show;
				}else{
					done = false;
					reason = "帧异常";
				}
				
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("cjqs",result);
		return jo.toString();
	}
	
	/**
	 * 查询采集器
	 * @param g
	 * @return
	 */
	public static String querycjqsV2(Gprs gprs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		String result = "";
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x07, gprsaddr, new byte[0]);
			logger.info("query cjq : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[512];
				int count = 0;  //记录收到了的数据个数
				while((count = in.read(data, 0, 512)) > 0){
					logger.info("query cjq receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, count))){
					logger.info("query cjq result : "+ StringUtil.byteArrayToHexStr(data, count));
					String show = "";
					for(int i = 0;i < (count-8-9)/5;i++){
						byte[] cjqaddrbytes = new byte[5];
						for(int k = 0;k < 5;k++){
							cjqaddrbytes[4-k] = data[15+5*i+k];
						}
						//get the addr 
						String cjqaddrstr = "";
						for(int k = 0;k < 5;k++){
							cjqaddrstr = cjqaddrstr+String.format("%02x", cjqaddrbytes[k]&0xFF)+" ";
						}
						show = show + cjqaddrstr+"\r\n,";
					}
					done = true;
					result = show;
				}else{
					done = false;
					reason = "帧异常";
				}
				
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("cjqs",result);
		return jo.toString();
	}

	/**
	 * 添加采集器
	 * @param g
	 * @param caddr
	 * @return
	 */
	public static String addcjq(Gprs gprs, String caddr) {

		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		String result = "";
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			byte[] framedata = new byte[7];
			framedata[0] = 0x55;
			
			byte[] cjqaddr = StringUtil.string2Byte(caddr);
			for(int i = 0;i < 6;i++){
				framedata[1+i] = cjqaddr[5-i];
			}
			
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x07, gprsaddr, framedata);
			logger.info("add cjq : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[100];
				int count = 0;
				while((count = in.read(data, 0, 100)) > 0){
					logger.info("add cjq receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					logger.info("add cjq result : "+ StringUtil.byteArrayToHexStr(data, 17));
					if(data[14] == (byte)0x01){
						done = true;
					}else{
						done = false;
						reason = "帧异常";
					}
				}
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		return jo.toString();
	}
	
	
	/**
	 * 添加采集器
	 * @param g
	 * @param caddr
	 * @return
	 */
	public static String addcjqV2(Gprs gprs, String caddr) {

		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		String result = "";
		
		JSONObject jo = new JSONObject();
		if(caddr.length() != 10){
			jo.put("done", done);
			jo.put("reason", "采集器地址长度  != 10 ");
			return jo.toString();
		}
		
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			byte[] framedata = new byte[6];
			framedata[0] = 0x55;
			
			byte[] cjqaddr = StringUtil.string2Byte(caddr);
			for(int i = 0;i < 5;i++){
				framedata[1+i] = cjqaddr[4-i];
			}
			
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x07, gprsaddr, framedata);
			logger.info("add cjq : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[100];
				int count = 0;
				while((count = in.read(data, 0, 100)) > 0){
					logger.info("add cjq receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					logger.info("add cjq result : "+ StringUtil.byteArrayToHexStr(data, 17));
					if(data[14] == (byte)0x01){
						done = true;
					}else{
						done = false;
						reason = "帧异常";
					}
				}
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		return jo.toString();
	}
	
	

	/**
	 * 删除全部表具
	 * @param g
	 * @return
	 */
	public static String deleteAllmeters(Gprs gprs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			byte[] framedata = new byte[1];
			framedata[0] = (byte) 0xAA;
			
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x07, gprsaddr, framedata);
			logger.info("delete all meters : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(10000);
				byte[] data = new byte[100];
				int count = 0;
				while((count = in.read(data, 0, 100)) > 0){
					logger.info("delete all meters receive : "+ StringUtil.byteArrayToHexStr(data, count));
					break;
				}
				
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					logger.info("delete all result : "+ StringUtil.byteArrayToHexStr(data, 17));
					if(data[14] == (byte)0x01){
						done = true;
					}else{
						done = false;
						reason = "帧异常";
					}
				}
				
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (IOException e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("done", done);
		jo.put("reason", reason);
		return jo.toString();
	}

	/**
	 * 查询集中器中的数据  返回caddr下的数据
	 * @param g
	 * @param caddr
	 * @return
	 */
	public static String readJZQdata(Gprs gprs, String caddr) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			
			/************************************操作**************************************/
			
			byte[] framedata = new byte[10];
			framedata[0] = 0x10;
			framedata[1] = (byte) 0xFF;
			framedata[2] = (byte) 0xFF;
			framedata[3] = (byte) 0xFF;
			framedata[4] = (byte) 0xFF;
			framedata[5] = (byte) 0xFF;
			framedata[6] = (byte) 0xFF;
			framedata[7] = (byte) 0xFF;
			framedata[8] = (byte) 0x00;
			framedata[9] = (byte) 0x00;
			
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x06, gprsaddr, framedata);
			logger.info("read jzq data : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(15000);
				boolean rcv_over = false;
				byte[] data = new byte[256];
				byte[] middle_data = new byte[256];
				
				while(!rcv_over){
					byte[] deal = new byte[512];
					int middle = 0;
					int count = 0;
					while((count = in.read(data, 0, 256)) > 0){
						logger.info("read jzq data receive : "+StringUtil.byteArrayToHexStr(data, count));
						for(int k = 0;k < count;k++){
							deal[middle+k] = data[k];
						}
						middle = middle + count;
						int ret = checkFrame(deal, middle);
						switch(ret){
						case 0:  //数据不够  继续接收
							break;
						case -1:  //这一帧错误
							middle = 0;  //重新开始接收
							break;
						case 1:  //这一帧正确处理
							if((deal[13]&0x60) == (byte)0x60 || (deal[13]&0x60) == (byte)0x20){
								rcv_over = true;
							}
							//get the frame len
							int framelen = (deal[1]&0xFF) | ((deal[2]&0xFF)<<8);
							framelen = framelen >> 2;
							//deal the frame
							for(int i = 0;i < (framelen-9-1)/17;i++){
								byte[] maddrbytes = new byte[7];
								byte[] caddrbytes = new byte[6];
								for(int k = 0;k < 7;k++){
									maddrbytes[6-k] = deal[16+17*i+2+k];
								}
								for(int k = 0;k < 6;k++){
									caddrbytes[5-k] = deal[16+17*i+9+k];
								}
								
								String maddrstr = "";
								for(int k = 0;k < 7;k++){
									maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF);
								}
								
								String cjqaddrstr = "";
								for(int k = 0;k < 6;k++){
									cjqaddrstr = cjqaddrstr+String.format("%02x", caddrbytes[k]&0xFF);
								}
								if(cjqaddrstr.equals(caddr)){
									JSONObject jao = new JSONObject();
									ja.add(jao);
									jao.put("maddr", maddrstr);
								}
							}
							//多帧时   为接收下一帧做准备
							middle = middle - framelen-8;
							if(middle != 0){
								for(int m = 0;m < middle;m++){
									middle_data[m]=deal[framelen+8+m];
								}
								for(int m = 0;m < middle;m++){
									deal[m]=middle_data[m];
								}
							}
							break;
						}
						if(rcv_over){
							break;
						}
					}
					
				}
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("caddr", caddr);
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}
	
	/**
	 * 查询集中器中的数据  返回caddr下的数据
	 * @param g
	 * @param caddr
	 * @return
	 */
	public static String readJZQdataV2(Gprs gprs, String caddr) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		if(caddr.length() != 10){
			jo.put("caddr", caddr);
			jo.put("done", done);
			jo.put("reason", "采集器地址长度  != 10 ");
			jo.put("result", ja);
			return jo.toString();
		}
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			byte[] framedata = new byte[6];
			framedata[0] = (byte)0xAA;
			for (int i = 0; i < 5; i++) {
				framedata[1 + i] = cjqaddr[4 - i];
			}
			
			Frame query = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
					(byte)0x06, gprsaddr, framedata);
			logger.info("read jzq data : "+ query.toString());
			try {
				out.write(query.getFrame());
				//等待集中器收到的回应
				s.setSoTimeout(15000);
				boolean rcv_over = false;
				byte[] data = new byte[256];
				byte[] middle_data = new byte[256];
				int frame_count = 0;
				int frame_all = 0; 
				HashMap<String,String> receiveMeters = new HashMap<>();
				while(!rcv_over){
					byte[] deal = new byte[512];
					int middle = 0;
					int count = 0;
					while((count = in.read(data, 0, 256)) > 0){
						logger.info("read jzq data receive : "+StringUtil.byteArrayToHexStr(data, count));
						for(int k = 0;k < count;k++){
							deal[middle+k] = data[k];
						}
						middle = middle + count;
						int ret = checkFrame(deal, middle);
						switch(ret){
						case 0:  //数据不够  继续接收
							break;
						case -1:  //这一帧错误
							middle = 0;  //重新开始接收
							break;
						case 1:  //这一帧正确处理
							//get the frame len
							
							byte seq_ = (byte) (deal[13] & 0x0F); // 当前帧的seq
							Frame ack = new Frame(0,(byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND),Frame.AFN_YES,
									(byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR | seq_),(byte)0x01,gprsaddr,new byte[0]);
							logger.info("write jzq data ack: "+ ack.toString());
							out.write(ack.getFrame());
							
							int framelen = (deal[1]&0xFF) | ((deal[2]&0xFF)<<8);
							framelen = framelen >> 2;
							frame_all = (deal[15]&0xFF) | ((deal[16]&0xFF)<<8);
							frame_count = (deal[17]&0xFF) | ((deal[18]&0xFF)<<8);
							if(frame_all == frame_count){
								rcv_over = true;
							}
							
							for(int i = 0;i < (framelen + 8-8-9-9)/7;i++){
								byte[] maddrbytes = new byte[7];
								
								for(int k = 0;k < 7;k++){
									maddrbytes[6-k] = deal[15+4+5+7*i+k];
								}
								
								String maddrstr = "";
								for(int k = 0;k < 7;k++){
									maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF);
								}
								logger.info("read jzq data cjq: "+ caddr+"; meteraddr: "+maddrstr);
								receiveMeters.put(maddrstr, maddrstr);
							}
							//多帧时   为接收下一帧做准备
							middle = middle - framelen-8;
							if(middle != 0){
								for(int m = 0;m < middle;m++){
									middle_data[m]=deal[framelen+8+m];
								}
								for(int m = 0;m < middle;m++){
									deal[m]=middle_data[m];
								}
							}
							break;
						}
						if(rcv_over){
							for(Entry<String, String> entry: receiveMeters.entrySet()){
								JSONObject jao = new JSONObject();
								ja.add(jao);
								jao.put("maddr", entry.getKey());
							}
							done = true;
							break;
						}
					}
					
				}
			} catch (IOException e) {
				done = false;
				reason = "接收失败";
				logger.error(reason,e);
				throw new RuntimeException(reason);
			}
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}

		jo.put("caddr", caddr);
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}

	/**
	 * 添加表s
	 * @param g
	 * @param caddr
	 * @param maddrs
	 * @return
	 */
	public static String jzqaddmeters(Gprs gprs, String caddr, String[] maddrs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
						
			/*************Add meters*********************/
			for(int i = 0;i < maddrs.length;i++){
				JSONObject jao = new JSONObject();
				jao.put("maddr", maddrs[i]);
				ja.add(jao);
				byte[] framedata_meter = new byte[18];
				framedata_meter[0] = 0x10;
				framedata_meter[1] = (byte) 0x00;
				framedata_meter[2] = (byte) 0x00;
				
				//meteraddr
				byte[] maddr = StringUtil.string2Byte(maddrs[i]);
				for(int j = 0;j < 7;j++){
					framedata_meter[3+j] = maddr[6-j];
				}
				//cjqaddr
				for(int j = 0;j < 6;j++){
					framedata_meter[10+j] = cjqaddr[5-j];
				}
				
				framedata_meter[16] = (byte) 0x01;
				framedata_meter[17] = (byte) 0x01;
				
				Frame addmeter = new Frame(framedata_meter.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
						Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
						(byte)0x06, gprsaddr, framedata_meter);
				logger.info("add meter : "+ addmeter.toString());
				boolean added = false;
				String singlereason = "";
				for(int z = 0;z<3 && !added;z++){
					out.write(addmeter.getFrame());
					//等待集中器收到的回应
					s.setSoTimeout(3000);
					byte[] data = new byte[100];
					int count = 0;
					while((count = in.read(data, 0, 100)) > 0){
						logger.info("add meter receive : "+ StringUtil.byteArrayToHexStr(data, count));
						break;
					}
					
					if(Frame.checkFrame(Arrays.copyOf(data, 17))){
						logger.info("add meter result : "+ StringUtil.byteArrayToHexStr(data, 17));
						if(data[14] == (byte)0x01){
							added = true;
						}else{
							added = false;
							singlereason = "帧错误";
						}
					}
					Thread.sleep(300);
				}
				
				jao.put("done",added);
				jao.put("reason", singlereason);
				logger.info("singleadd: "+ added + ";meteraddr: "+maddrs[i]+";reason:"+singlereason);
				
			}
			done=true;
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}
	
	/**
	 * 添加表s
	 * @param g
	 * @param caddr
	 * @param maddrs
	 * @return
	 */
	public static String jzqaddmetersV2(Gprs gprs, String caddr, String[] maddrs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
						
			/*************Add meters*********************/
			int metercount = maddrs.length;
			//将表地址分成多组添加
			int times = metercount / METER_BATCH;  //10个一组添加多少次
			int remain = metercount % METER_BATCH;
			if(remain > 0){
				times += 1;
			}
			
			for(int i = 0;i < times;i++){
				JSONObject jao = new JSONObject();
				ja.add(jao);
				int meters = METER_BATCH;
				if(i == times - 1){
					if(remain == 0){
						meters = METER_BATCH;
					}else{
						meters = remain;
					}
				}
				
				byte[] framedata = new byte[meters*7+7]; 
				framedata[0] = 0x01;  //运行标志  添加~1  删除~0
				framedata[1] = 0x10;  //表类型
				// cjqaddr
				for (int j = 0; j < 5; j++) {
					framedata[2 + j] = cjqaddr[4 - j];
				}
				
				String meters_this = "";   //本次添加的表的地址
				for(int j = 0;j < meters;j++){
					meters_this = maddrs[i*METER_BATCH+j] + ":" + meters_this ;
					byte[] maddr = StringUtil.string2Byte(maddrs[i*METER_BATCH+j]);
					for(int z = 0;z < 7;z++){
						framedata[7+7*j + z] =  maddr[6-z];
					}
				}
				jao.put("maddr", meters_this);
				
				Frame addmeter = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
						Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
						(byte)0x06, gprsaddr, framedata);
				logger.info("add meters : "+ addmeter.toString());
				boolean added = false;
				String singlereason = "";
				for(int z = 0;z<3 && !added;z++){
					out.write(addmeter.getFrame());
					//等待集中器收到的回应
					s.setSoTimeout(10000);
					byte[] data = new byte[100];
					int count = 0;
					while((count = in.read(data, 0, 100)) > 0){
						logger.info("add meter receive : "+ StringUtil.byteArrayToHexStr(data, count));
						break;
					}
					
					if(Frame.checkFrame(Arrays.copyOf(data, 17))){
						logger.info("add meter result : "+ StringUtil.byteArrayToHexStr(data, 17));
						if(data[14] == (byte)0x01){
							added = true;
						}else{
							added = false;
							singlereason = "帧错误";
						}
					}
					Thread.sleep(300);
				}
				jao.put("done", true);
				jao.put("reason", singlereason);
				logger.info("singleadd: "+ added + ";meteraddr: "+meters_this+";reason:"+singlereason);
			}
			done=true;
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}

	/**
	 * 删除表s
	 * @param g
	 * @param caddr
	 * @param maddrs
	 * @return
	 */
	public static String jzqdeletemeters(Gprs gprs, String caddr, String[] maddrs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
			
			/************************************操作**************************************/
			/*******Delete meters********/
			for(int i = 0;i < maddrs.length;i++){
				JSONObject jao = new JSONObject();
				jao.put("maddr", maddrs[i]);
				ja.add(jao);
				byte[] framedata_meter = new byte[18];
				framedata_meter[0] = 0x10;
				framedata_meter[1] = (byte) 0x00;
				framedata_meter[2] = (byte) 0x00;
				//meteraddr
				byte[] maddr = StringUtil.string2Byte(maddrs[i]);
				for(int j = 0;j < 7;j++){
					framedata_meter[3+j] = maddr[6-j];
				}
				//cjqaddr
				byte[] cjqaddr = StringUtil.string2Byte(caddr);
				for(int j = 0;j < 6;j++){
					framedata_meter[10+j] = cjqaddr[5-j];
				}
				
				framedata_meter[16] = (byte) 0x00;
				framedata_meter[17] = (byte) 0x01;
				
				Frame deletemeter =  new Frame(framedata_meter.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
						Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
						(byte)0x06, gprsaddr, framedata_meter);
				logger.info("delete meter : "+ deletemeter.toString());
				boolean deleted = false;
				String singlereason = "";
				for(int z = 0;z<3 && !deleted;z++){
					out.write(deletemeter.getFrame());
					//等待集中器收到的回应
					s.setSoTimeout(3000);
					byte[] data = new byte[100];
					int count = 0;
					while((count = in.read(data, 0, 100)) > 0){
						logger.info("delete meter receive : "+ StringUtil.byteArrayToHexStr(data, count));
						break;
					}
					
					//判断接收到的集中器的回应
					if(Frame.checkFrame(Arrays.copyOf(data, 17))){
						logger.info("delete meter result : "+ StringUtil.byteArrayToHexStr(data, 17));
						if(data[14] == (byte)0x01){
							deleted = true;
						}else{
							deleted = false;
							singlereason = "帧错误";
						}
					}
					Thread.sleep(300);
				}
				
				jao.put("done",deleted);
				jao.put("reason", singlereason);
				logger.info("singledelete: "+ deleted + ";meteraddr: "+maddrs[i]+";reason:"+singlereason);
			}
			done=true;
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}
		
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}
	
	/**
	 * 添加表s
	 * @param g
	 * @param caddr
	 * @param maddrs
	 * @return
	 */
	public static String jzqdeletemetersV2(Gprs gprs, String caddr, String[] maddrs) {
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		boolean done = false;
		String reason = "";
		
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
			
			//登陆监听服务器
			boolean login = loginListener(s, out, in, gprsaddr);
			if (!login) {
				done = false;
				reason = "登陆监听异常";
				throw new RuntimeException(reason);
			}
						
			/*************Delete meters*********************/
			int metercount = maddrs.length;
			//将表地址分成多组添加
			int times = metercount / METER_BATCH;  //10个一组添加多少次
			int remain = metercount % METER_BATCH;
			if(remain > 0){
				times += 1;
			}
			
			for(int i = 0;i < times;i++){
				JSONObject jao = new JSONObject();
				ja.add(jao);
				int meters = METER_BATCH;
				if(i == times - 1){
					if(remain == 0){
						meters = METER_BATCH;
					}else{
						meters = remain;
					}
				}
				
				byte[] framedata = new byte[meters*7+7]; 
				framedata[0] = 0x00;  //运行标志  添加~1  删除~0
				framedata[1] = 0x10;  //表类型
				// cjqaddr
				for (int j = 0; j < 5; j++) {
					framedata[2 + j] = cjqaddr[4 - j];
				}
				
				String meters_this = "";   //本次添加的表的地址
				for(int j = 0;j < meters;j++){
					meters_this = maddrs[i*METER_BATCH+j] + ":" + meters_this ;
					byte[] maddr = StringUtil.string2Byte(maddrs[i*METER_BATCH+j]);
					for(int z = 0;z < 7;z++){
						framedata[7+7*j + z] =  maddr[6-z];
					}
				}
				jao.put("maddr", meters_this);
				
				Frame addmeter = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
						Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
						(byte)0x06, gprsaddr, framedata);
				logger.info("add meters : "+ addmeter.toString());
				boolean deleted = false;
				String singlereason = "";
				for(int z = 0;z<3 && !deleted;z++){
					out.write(addmeter.getFrame());
					//等待集中器收到的回应
					s.setSoTimeout(7000);
					byte[] data = new byte[100];
					int count = 0;
					while((count = in.read(data, 0, 100)) > 0){
						logger.info("delete meter receive : "+ StringUtil.byteArrayToHexStr(data, count));
						break;
					}
					
					if(Frame.checkFrame(Arrays.copyOf(data, 17))){
						logger.info("delete meter result : "+ StringUtil.byteArrayToHexStr(data, 17));
						if(data[14] == (byte)0x01){
							deleted = true;
						}else{
							deleted = false;
							singlereason = "帧错误";
						}
					}
					Thread.sleep(300);
				}
				jao.put("done", true);
				jao.put("reason", singlereason);
				logger.info("singledelete: "+ deleted + ";meteraddr: "+meters_this+";reason:"+singlereason);
			}
			done=true;
		} catch (Exception e) {
			done = false;
			reason = "配置异常";
			logger.error("gprsconfig error ! gprsaddr: "+gprs.getGprsaddr(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (s != null) {
					s.close();
				}
			} catch (Exception e) {
				logger.error("gprsconfig error release resources ! gprsaddr: "+gprs.getGprsaddr(), e);
			}
		}
		jo.put("done", done);
		jo.put("reason", reason);
		jo.put("result", ja);
		return jo.toString();
	}
	
	/**
	 * 检查从socket中接收到的数据    是否是一帧 
	 * @param deal
	 * @param middle  从socket中接收到的byte数
	 * @return 检查结果  -1~放弃   0~数据不够  1~帧正确
	 */
	public static int checkFrame(byte[] deal, int middle) {
		int frame_len = 0;  //帧的长度
		int ret = 0;   //检查结果  -1~放弃   0~数据不够  1~帧正确
		int header = 0;
		int data_len = 0;  //帧中数据长度
		
		if(header == 0){
			if(middle > 5){
				if(deal[0] == 0x68 && deal[5] == 0x68){
					if(deal[1] == deal[3] && deal[2] == deal[4]){
						data_len = (deal[1]&0xFF) | ((deal[2]&0xFF)<<8);
						data_len = data_len >> 2;
						header = 1;
					}
				}
				if(header == 0){
					//give up the data
					ret = -1;
				}
			}
		}
		if(header == 1){
			if(middle >= (data_len + 8)){
				frame_len = data_len+8;
				
				byte cs = 0;
				for(int k = 6;k < frame_len-2;k++){
					cs += deal[k];
				}
				if(cs == deal[frame_len-2] && deal[frame_len-1] == 0x16){
					//这一帧OK
					ret = 1;
				}else{
					//这一帧有错误  放弃
					ret = -1;
				}
			}else{
				//收到的数据还不够
				ret =  0;
			}
		}
		return ret;
	}
	

}
