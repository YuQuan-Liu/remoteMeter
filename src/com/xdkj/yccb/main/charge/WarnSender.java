package com.xdkj.yccb.main.charge;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.encoder.Base64Pwd;
import com.xdkj.yccb.main.charge.service.WarnService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.net.www.http.HttpClient;

@Component
public class WarnSender {
	
	@Autowired
	private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private WarnService warnService;
	
	public boolean sendWarnSingle(Watercompany wc,int cid){
		
		Customer c = customerDao.getCustomerByPid(cid);
		boolean done = false;
		switch(c.getWarnStyle()){
		case 1:
			done = sendSMS(wc,c);
			break;
		case 2:
			done = sendMail(wc,c);
			break;
		}
		//将提醒信息保存到数据库
		warnService.addWarnSingle(c,done);
		
		return done;
	}
	

	public void sendWarnAll(Watercompany wc, Object[] ids) {
		for(int i = 0;i < ids.length;i++){
			sendWarnSingle(wc, Integer.parseInt(ids[i].toString()));
//			System.out.println(Integer.parseInt(ids[i].toString()));
			
		}
		
	}
	
	
	private boolean sendMail(Watercompany wc, Customer c){
		//mail
		boolean done = false;
		try {
			mailSender.setHost(wc.getEmailHost());
			mailSender.setUsername(wc.getEmailUser());
			mailSender.setPassword(Base64Pwd.decode(wc.getEmailPassword()));
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			mailSender.setJavaMailProperties(props);
//		MimeMessagePreparator preparator = new MimeMessagePreparator() {
//			
//			@Override
//			public void prepare(MimeMessage mimeMessage) throws Exception {
//				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(c.getCustomerEmail()));
//				mimeMessage.setText("test");
//				mimeMessage.setFrom(wc.getEmailUser());
//			}
//		};
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(c.getCustomerEmail());
			msg.setSubject(wc.getCompanyName()+" 交费提醒");
			msg.setText("尊敬的"+c.getCustomerName()+"用戶：\r\n    您好，您的水费余额为"+c.getCustomerBalance().doubleValue()+"，请您及时交费。谢谢合作。");
			msg.setFrom(wc.getEmailUser());
			
			mailSender.send(msg);
			done = true;
		} catch (MailException e) {
			e.printStackTrace();
		}
		return done;
	}
	
	private boolean sendSMS(Watercompany wc, Customer c){
		// message
		boolean done = false;
		Map<String, String> para = new HashMap<String, String>();

		/**
		 * 目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		 */
		para.put("mob", c.getCustomerMobile());
		
		/**
		 * 微米账号的接口UID
		 */
		para.put("uid", "Hnq9MjyE1pBf");

		/**
		 * 微米账号的接口密码
		 */
		para.put("pas", "qg4nwa7k");

		/**
		 * 接口返回类型：json、xml、txt。默认值为txt
		 */
		para.put("type", "json");

		/**
		 * 短信内容。必须设置好短信签名，签名规范： <br>
		 * 1、短信内容一定要带签名，签名放在短信内容的最前面；<br>
		 * 2、签名格式：【***】，签名内容为三个汉字以上（包括三个）；<br>
		 * 3、短信内容不允许双签名，即短信内容里只有一个“【】”
		 * 
		 */
		para.put("con", "【"+wc.getCompanyName()+"】尊敬的"+c.getCustomerName()+"用户您好，您的水费余额为"+c.getCustomerBalance().doubleValue()+",请您及时交费。谢谢合作。");
		
		JSONObject jo = JSONObject.parseObject(HttpClientHelper.convertStreamToString(
				HttpClientHelper.get("http://api.weimi.cc/2/sms/send.html",
						para), "UTF-8"));
		
		if(jo.get("code").toString().equals("0")){
			done = true;
		}
		return done;
	}
	
	public static void main(String[] args) throws IOException {
		
		Watercompany wc = new Watercompany();
		Customer c = new Customer();
		wc.setCompanyName("西岛");
		wc.setEmailHost("smtp.163.com");
		wc.setEmailUser("avenger0422@163.com");
		wc.setEmailPassword("bGw4ODAzMTlsbA==");
		
		c.setCustomerEmail("582615540@qq.com");
		c.setCustomerBalance(new BigDecimal(100.00));
		c.setCustomerName("haha");
		new WarnSender().sendMail(wc, c);
	}


}
