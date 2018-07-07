package com.xdkj.yccb.main.readme.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xdkj.yccb.common.StringUtil;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.readme.dto.Frame;

public class QuartzCleanValve implements Job{
	private static final Logger logger = LoggerFactory.getLogger(QuartzCleanValve.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap map = context.getJobDetail().getJobDataMap();
		Gprs gprs = (Gprs) map.get("gprs");
		
		cleanValve(gprs);
	}

	private void cleanValve(Gprs gprs) {
		
		Socket s = null;
		OutputStream out = null;
		InputStream in = null;
		boolean finished = false;  //阀门开关是否成功  默认不成功
		String reason = "";  //开关失败原因
		byte seq = 0;   //服务器给集中器发送的序列号
		
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
			reason = "连接监听异常";
			logger.info("阀门控制:"+gprs.getGprsaddr()+reason);
			return;
			
		}
		
		//~~~~~~~~~~~~~~~~~~~~~~~登录监听服务器
		byte[] gprsaddr = StringUtil.string2Byte(gprs.getGprsaddr());
		boolean read = false;
		read = loginListener(s, out, in, gprsaddr);
		if(!read){
			//集中器不在线
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
			reason = "集中器不在线";
			logger.info("阀门控制:"+gprs.getGprsaddr()+reason);
			return;
		}
		
		boolean seq_syn = false;
		//~~~~~~~~~~~~~~~~~~~~~~~~~确定集中器的服务器的序列号
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
			reason = "序列号未同步";
			logger.info("阀门控制:"+gprs.getGprsaddr()+reason);
			return;
		}
		
		
		/**********************************阀门控制**************************************/
		int ack_timeout_count = 0;  //抄表指令未收到确认计数   指令重发次数
		for(int i = 0;i < 3 && !finished;i++){
			
			if(!finished){
				seq++;
				seq = (byte) (seq&0x0F);
			}
			
			//the data in the frame
			byte[] framedata = new byte[10];
			framedata[0] = 0x10;
			for(int k= 1;k <= 7;k++){
//				framedata[k] = meteraddr[6-(k-1)];
				framedata[k] = 0x00;
			}
			framedata[8] = 0x00;
			framedata[9] = 0x00;
			byte action = 0x04;  //清理阀门
			
			boolean read_ack = false;  //集中器收到控制指令
			read_ack = sendControlFrame(s, out, in, seq, action, gprsaddr, framedata);
			
			if(!read_ack){
				//没有收到集中器的收到指令确认  3s后重新发送指令
				ack_timeout_count++;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}else{
				finished = true;
			}
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
		
	}

	private boolean sendControlFrame(Socket s, OutputStream out,
			InputStream in, byte seq, byte action, byte[] gprsaddr,
			byte[] framedata) {
		boolean timeout = false;
		boolean read_ack = false;
		byte[] data = new byte[100];
		try {
			
//			Frame readgprs = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_SECOND), 
//					Frame.AFN_READMETER, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|seq), 
//					(byte)0x04, gprsaddr, framedata);
			Frame readgprs = new Frame(framedata.length, (byte)(Frame.ZERO | Frame.PRM_MASTER |Frame.PRM_M_FIRST), 
					Frame.AFN_CONTROL, (byte)(Frame.ZERO|Frame.SEQ_FIN|Frame.SEQ_FIR|seq), 
					action, gprsaddr, framedata);
			
			out.write(readgprs.getFrame());
			//等待集中器收到的回应
			s.setSoTimeout(10000);
			try {
				while((in.read(data, 0, 100)) > 0){
					break;
				}
			} catch (SocketTimeoutException e) {
				timeout = true;
				e.printStackTrace();
			}
			
			if(!timeout){
				//判断接收到的集中器的回应
				if(Frame.checkFrame(Arrays.copyOf(data, 17))){
					Frame ack_result = new Frame(Arrays.copyOf(data, 17));
					
					if(ack_result.getFn() == 0x01){
						//集中器收到发出去的指令
						read_ack = true;
					}
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return read_ack;
	}

	private boolean synSEQ(Socket s, OutputStream out, InputStream in,
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

	private boolean loginListener(Socket s, OutputStream out, InputStream in,
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
