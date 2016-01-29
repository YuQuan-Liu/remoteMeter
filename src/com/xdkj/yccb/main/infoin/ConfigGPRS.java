package com.xdkj.yccb.main.infoin;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;


import net.sf.json.JSONObject;

import com.xdkj.yccb.common.StringUtil;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.readme.dto.Frame;

public class ConfigGPRS {

	public static String querygprsslave(Gprs gprs) {
		
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		byte seq = 0;   //服务器给集中器发送的序列号
		
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
		

		boolean seq_syn = false;
		/**************************确定集中器的服务器的序列号******************************/
		seq_syn = synSEQ(s, out, in, seq, gprsaddr);
		if(!seq_syn){
			//序列号未同步
			try {
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
				if(s != null){
					s.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			jo.put("done", false);
			jo.put("reason", "序列号未同步");
			return jo.toString();
		}
		
		
		

		/************************************操作**************************************/
		seq++;
		seq = (byte) (seq&0x0F);
		
		
		Frame query = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
				Frame.AFN_QUERY, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|seq), 
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

	public static String configgprsslave(Gprs g, int gprsslave) {
		
		return null;
	}
	
	
	
	

	private static boolean synSEQ(Socket s, OutputStream out, InputStream in,
			byte seq, byte[] gprsaddr) {
		boolean timeout = false;
		boolean seq_syn = false;
		byte[] data = new byte[100];
		//~~~~~~~~~~~~~~~~~~~~~~~~~确定集中器的服务器的序列号
		for(int i = 0;i < 3 && !seq_syn;i++){
			byte[] framedata = new byte[0];
			
			Frame syn = new Frame(0, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
					Frame.AFN_READMETER, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR | seq), 
					(byte)0x05, gprsaddr, framedata);
			try {
				out.write(syn.getFrame());
				//6s内接收服务器的返回
				s.setSoTimeout(10000);
				try {
					while((in.read(data, 0, 100)) > 0){
						break;
					}
				} catch (SocketTimeoutException e) {
					timeout = true;
					e.printStackTrace();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
			if(!timeout){
				//6s内收到监听返回  判断是否是ack
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					Frame ack = new Frame(Arrays.copyOf(data, 17));
					if(ack.getFn() == 0x01){
						//序列号同步
						seq_syn = true;
					}
				}
			}
		}
		return seq_syn;
	}

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

}
