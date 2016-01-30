package com.xdkj.yccb.main.infoin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.StringUtil;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.readme.dto.Frame;

public class ConfigGPRS {
	
	/**
	 * 登录集中器
	 * @param s
	 * @param out
	 * @param in
	 * @param gprsaddr
	 * @return
	 */
	private static boolean loginListener(Socket s, OutputStream out, InputStream in,
			byte[] gprsaddr) {
		byte[] data = new byte[100];
		boolean timeout = false;
		boolean read = false;
		
		Frame login = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_LINE), 
				Frame.AFN_LOGIN, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x01, gprsaddr, new byte[0]);
		
		//~~~~~~~~~~~~~~~~~~~~~~~登录监听服务器
		try {
			out.write(login.getFrame());
			//6s内接收服务器的返回
			s.setSoTimeout(6000);
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
				e.printStackTrace();
			}
			
			if(!timeout){
				//6s内收到监听返回  判断是否是ack
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					Frame login_result = new Frame(Arrays.copyOf(data, 17));
					if(login_result.getFn() == 0x01){
						//online  集中器在线
						read = true;
					}
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return read;
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}

		/************************************操作**************************************/
		
		Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x0C, gprsaddr, new byte[0]);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[100];
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 18))){
					if(data[15] == (byte)0xAA){
						jo.put("slave", "MBUS");
					}
					if(data[15] == (byte)0xBB){
						jo.put("slave", "采集器");
					}
					if(data[15] == (byte)0xFF){
						jo.put("slave", "485");
					}
					jo.put("done", true);
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "等待超时");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
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
		Frame query = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x0C, gprsaddr, framedata);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[100];
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					if(data[14] == (byte)0x01){
						jo.put("done", true);
					}else{
						jo.put("done", false);
						jo.put("reason", "帧异常");
					}
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "等待超时");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}
		
		/************************************操作**************************************/
		
		Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x07, gprsaddr, new byte[0]);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[512];
			int count = 0;  //记录收到了的数据个数
			try {
				while((count = in.read(data, 0, 512)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, count))){
					String show = "";
					for(int i = 0;i < (count-8-9)/6;i++){
						byte[] cjqaddrbytes = new byte[6];
						for(int k = 0;k < 6;k++){
							cjqaddrbytes[5-k] = data[15+6*i+k];
						}
						//get the addr 
						String cjqaddrstr = "";
						for(int k = 0;k < 6;k++){
//									this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
							cjqaddrstr = cjqaddrstr+String.format("%02x", cjqaddrbytes[k]&0xFF)+" ";
						}
						show = show + cjqaddrstr+"\r\n";
					}
					jo.put("done", true);
					jo.put("cjqs",show);
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "等待超时");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}
		
		/************************************操作**************************************/
		
		byte[] framedata = new byte[7];
		framedata[0] = 0x55;
		
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		for(int i = 0;i < 6;i++){
			framedata[1+i] = cjqaddr[5-i];
		}
		
		
		
		Frame query = new Frame(7, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x07, gprsaddr, framedata);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[100];
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					if(data[14] == (byte)0x01){
						jo.put("done", true);
					}else{
						jo.put("done", false);
						jo.put("reason", "帧异常");
					}
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "等待超时");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}
		
		/************************************操作**************************************/
		
		byte[] framedata = new byte[1];
		framedata[0] = (byte) 0xAA;
		
		Frame query = new Frame(1, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x07, gprsaddr, framedata);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[100];
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					if(data[14] == (byte)0x01){
						jo.put("done", true);
					}else{
						jo.put("done", false);
						jo.put("reason", "帧异常");
					}
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "等待超时,删除时间可能过长,请稍后查询确认！");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
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
		
		JSONArray ja = new JSONArray();
		jo.put("result", ja);
		jo.put("caddr", caddr);
		jo.put("done", true);
		Frame query = new Frame(10, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x06, gprsaddr, framedata);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(15000);
			boolean timeout = false;
			boolean rcv_over = false;
			byte[] data = new byte[256];
			byte[] middle_data = new byte[256];
			int count = 0;
			while(!rcv_over && !timeout){
				
				try {
					byte[] deal = new byte[512];
					int middle = 0;
					while((count = in.read(data, 0, 256)) > 0){
						for(int k = 0;k < count;k++){
							deal[middle+k] = data[k];
						}
						middle = middle + count;
						int ret = checkFrame(deal, middle);
						switch(ret){
						case 0:
							//数据不够  继续接收
							break;
						case -1:
							//这一帧错误
							middle = 0;  //重新开始接收
							break;
						case 1:
							//这一帧正确处理
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
			//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
									maddrstr = maddrstr+String.format("%02x", maddrbytes[k]&0xFF);
								}
								
								String cjqaddrstr = "";
								for(int k = 0;k < 6;k++){
			//								this.addrstr = this.addrstr+Integer.toHexString(addr[i]&0xFF);
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
				} catch (SocketTimeoutException e) {
					timeout = true;
				}
				
			}
			if(timeout){
				jo.put("done", false);
				jo.put("reason", "超时");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}
		
		/************************************操作**************************************/
		/******添加采集器********/
		
		byte[] framedata_cjq = new byte[7];
		framedata_cjq[0] = 0x55;
		
		byte[] cjqaddr = StringUtil.string2Byte(caddr);
		for(int i = 0;i < 6;i++){
			framedata_cjq[1+i] = cjqaddr[5-i];
		}
		
		Frame query = new Frame(7, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR), 
				(byte)0x07, gprsaddr, framedata_cjq);
		try {
			out.write(query.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			boolean timeout = false;
			byte[] data = new byte[100];
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					if(data[14] == (byte)0x01){
						jo.put("done", true);
					}else{
						jo.put("done", false);
						jo.put("reason", "采集器添加失败");
					}
				}
			}else{
				jo.put("done", false);
				jo.put("reason", "采集器添加失败");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if((boolean) jo.get("done")){
			/*************Add meters*********************/
			byte[] framedata_meter = new byte[18];
			Frame addmeter = null;
			JSONArray ja = new JSONArray();
			jo.put("result", ja);
			for(int i = 0;i < maddrs.length;i++){
				JSONObject jao = new JSONObject();
				jao.put("maddr", maddrs[i]);
				ja.add(jao);
				
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
				
				addmeter = new Frame(18, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
						Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
						(byte)0x06, gprsaddr, framedata_meter);
				
				boolean added = false;
				for(int z = 0;z<3 && !added;z++){
					try {
						out.write(addmeter.getFrame());
						//等待集中器收到的回应
						s.setSoTimeout(3000);
						boolean timeout = false;
						byte[] data = new byte[100];
						try {
							while((in.read(data, 0, 100)) > 0){
								break;
							}
						} catch (SocketTimeoutException e) {
							timeout = true;
						}
						
						if(!timeout){
							//判断接收到的集中器的回应
							if(Frame.checkFrame(Arrays.copyOf(data, 17))){
								if(data[14] == (byte)0x01){
									jao.put("done", true);
									added = true;
								}else{
									jao.put("done", false);
									jao.put("reason", "帧错误");
								}
							}
						}else{
							jao.put("done", false);
							jao.put("reason", "超时");
						}
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
				}
				
//				if(jao.getBoolean("done")){
//					System.out.println(maddrs[i]+jao.getString("done"));
//				}else{
//					System.out.println(maddrs[i]+jao.getString("done")+jao.getString("reason"));
//				}
				
			}
//			System.out.println(ja.toJSONString());
		}else{
			//Fail return 
		}
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		JSONObject jo = new JSONObject();
		try {
			s = new Socket(gprs.getIp(),gprs.getPort());
			out = s.getOutputStream();
			in = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//如果没有连接成功 
		if(s == null){
			//没有连接到监听程序  
			jo.put("done", false);
			jo.put("reason", "连接监听异常");
			return jo.toString();
		}
		
		/**************************登录监听服务器******************************/
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if (!read) {
			// 集中器不在线
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
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "集中器不在线");
			return jo.toString();
		}
		
		/************************************操作**************************************/
		/*******Delete meters********/
		byte[] framedata_meter = new byte[18];
		Frame deletemeter = null;
		JSONArray ja = new JSONArray();
		jo.put("result", ja);
		jo.put("done", true);
		for(int i = 0;i < maddrs.length;i++){
			JSONObject jao = new JSONObject();
			jao.put("maddr", maddrs[i]);
			ja.add(jao);
			
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
			
			deletemeter = new Frame(18, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_CONFIG, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|Frame.SEQ_CON), 
					(byte)0x06, gprsaddr, framedata_meter);
			
			boolean deleted = false;
			for(int z = 0;z<3 && !deleted;z++){
				try {
					out.write(deletemeter.getFrame());
					//等待集中器收到的回应
					s.setSoTimeout(3000);
					boolean timeout = false;
					byte[] data = new byte[100];
					try {
						while((in.read(data, 0, 100)) > 0){
							break;
						}
					} catch (SocketTimeoutException e) {
						timeout = true;
					}
					
					if(!timeout){
						//判断接收到的集中器的回应
						if(Frame.checkFrame(Arrays.copyOf(data, 17))){
							if(data[14] == (byte)0x01){
								jao.put("done", true);
								deleted = true;
							}else{
								jao.put("done", false);
								jao.put("reason", "帧错误");
							}
						}
					}else{
						jao.put("done", false);
						jao.put("reason", "超时");
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
				}
			}
			
			if(jao.getBoolean("done")){
				System.out.println(maddrs[i]+jao.getString("done"));
			}else{
				System.out.println(maddrs[i]+jao.getString("done")+jao.getString("reason"));
			}
			
		}
//		System.out.println(ja.toJSONString());
		
		try {
			if(out!=null){
				out.close();
			}
			if(in!=null){
				in.close();
			}
			if(s!=null){
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
