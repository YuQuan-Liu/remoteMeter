package com.xdkj.yccb.main.charge;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.xdkj.yccb.main.adminor.dao.SMSTemplateDao;
import com.xdkj.yccb.main.adminor.dao.SysParaDao;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.charge.dto.WarnPostPay;
import com.xdkj.yccb.main.charge.service.WarnService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.entity.SMSTemplate;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;

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
	private MeterDeductionLogDao meterDeductionLogDao;
	@Autowired
	private WarnService warnService;
	@Autowired
	private WaterCompanyService waterCompanyService;
	@Autowired
	private SysParaDao sysParaDao;
	@Autowired
	private SMSTemplateDao smsTemplateDao;
	/**
	 * 欠费提醒 单个
	 * @param wc
	 * @param cid
	 * @return
	 */
	public boolean sendWarnSingle(Watercompany wc,int cid){
		
		Customer c = customerDao.getCustomerByPid(cid);
		boolean done = false;
		String re = "";
		switch(c.getWarnStyle()){
		case 1:
			re = sendSMS(wc,c);
			if(re.equals("0")){
				done = true;
			}
			break;
		case 2:
			done = sendMail(wc,c);
			break;
		}
		//将提醒信息保存到数据库
		warnService.addWarnSingle(c,done,re,"");
		
		return done;
	}
	
	/**
	 * 提醒后付费交费 SMS
	 * @param wc
	 * @param mdlid
	 */
	public void sendWarnPostPay(Watercompany wc,int[] mdlid){
		
		
		List<WarnPostPay> list = meterDeductionLogDao.getWarnPostPays(mdlid);
		for(int i = 0;i < list.size();i++){
			boolean done = false;
			String re = sendSMSPostPayAll(wc,list.get(i));
			if(re.equals("0")){
				done = true;
			}
			//将提醒信息保存到数据库
			warnService.addWarnSinglePostPay(list.get(i),done,re);
		}
	}
	
	/**
	 * 提醒后付费交费
	 * @param wc
	 * @param c
	 * @return
	 */
	private String sendSMSPostPayAll(Watercompany wc, WarnPostPay c){
		// message
//		boolean done = false;
		Map<String, String> para = new HashMap<String, String>();

		if(c.getCustomerMobile()==null || c.getCustomerMobile().equals("") || c.getCustomerMobile().length() != 11){
			return "-999";
		}else{
			//检查今天发送几条了
			
			if(!warnService.todaySend(c.getCustomerMobile())){
				return "-999";
			}
		}
		
		if(c.getDemoney().compareTo(new BigDecimal(0)) != 1){
			//扣费金额  <= 0
			return "-998";
		}
		
		//目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		para.put("mob", c.getCustomerMobile());
		//接口返回类型：json、xml、txt。默认值为txt
		para.put("type", "json");
		
		if(wc.getSmsuid() == null){
			//默认
			//微米账号的接口UID
			para.put("uid", sysParaDao.getValue("xdkj_smsuid"));
    		//微米账号的接口密码
    		para.put("pas", sysParaDao.getValue("xdkj_smspas"));
		}else{
			//微米账号的接口UID
    		para.put("uid", wc.getSmsuid());
    		//微米账号的接口密码
    		para.put("pas", wc.getSmspas());
		}
		//短信模板cid，通过微米后台创建
		SMSTemplate qfTemplate = null;
		//判断使用企业模板还是普通用户模板
		switch (c.getHk()){
		case 4:
			qfTemplate = smsTemplateDao.getQF(wc.getPid(),2);
			break;
			default:
				qfTemplate = smsTemplateDao.getQF(wc.getPid(),1);
				break;
		}
		if(qfTemplate == null){
			//西岛默认
			//短信模板cid
			para.put("cid", sysParaDao.getValue("xdkj_smsqfcid"));
			//传入模板参数。  第一个%P% 为p1,后面的++
			para.put("p1", c.getCustomerName());
            para.put("p2", c.getDemoney().doubleValue()+"");
            para.put("p3", wc.getCompanyName());
		}else{
			//短信模板cid
            switch (c.getHk()){
			case 4:
//				//企业
//				//短信模板cid
				para.put("cid", qfTemplate.getCid());
				//传入模板参数。  第一个%P% 为p1,后面的++
				if(qfTemplate.getPara_cnt() == 1){
					para.put("p1", c.getC_num());
				}else{
					para.put("p1", c.getC_num());
		            para.put("p2", "-"+c.getDemoney().doubleValue());
				}
				break;
			default:
				//短信模板cid
				para.put("cid", qfTemplate.getCid());
				//传入模板参数。  第一个%P% 为p1,后面的++
				para.put("p1", c.getC_num());
	            para.put("p2", "-"+c.getDemoney().doubleValue());
				break;
			}
		}
		
		JSONObject jo = JSONObject.parseObject(HttpClientHelper.convertStreamToString(
				HttpClientHelper.get("http://api.weimi.cc/2/sms/send.html",
						para), "UTF-8"));
		
		return jo.get("code").toString();
	}
	

	/**
	 * 欠费提醒  全部
	 * @param wc
	 * @param ids
	 */
	public void sendWarnAll(Watercompany wc, Object[] ids) {
		for(int i = 0;i < ids.length;i++){
			sendWarnSingle(wc, Integer.parseInt(ids[i].toString()));
//			System.out.println(Integer.parseInt(ids[i].toString()));
		}
	}
	
	/**
	 * 提醒交费 Email
	 * @param wc
	 * @param c
	 * @return
	 */
	private boolean sendMail(Watercompany wc, Customer c){
		//mail
		boolean done = false;
		if(c.getCustomerEmail() == null || c.getCustomerEmail().equals("")){
			return false;
		}
		
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
	
	/**
	 * 提醒交费 SMS
	 * @param wc
	 * @param c
	 * @return
	 */
	private String sendSMS(Watercompany wc, Customer c){
		// message
//		boolean done = false;
		Map<String, String> para = new HashMap<String, String>();

		if(c.getCustomerMobile()==null || c.getCustomerMobile().equals("") || c.getCustomerMobile().length() != 11){
			return "-999";
		}else{
			//检查今天发送几条了
			
			if(!warnService.todaySend(c.getCustomerMobile())){
				return "-999";
			}
		}
		
		if(c.getCustomerBalance().compareTo(new BigDecimal(0)) != -1){
			//用户余额  >= 0
			return "-998";
		}
		
		//目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		para.put("mob", c.getCustomerMobile());
		//接口返回类型：json、xml、txt。默认值为txt
		para.put("type", "json");
		
		if(wc.getSmsuid() == null){
			//默认
			//微米账号的接口UID
			para.put("uid", sysParaDao.getValue("xdkj_smsuid"));
    		//微米账号的接口密码
    		para.put("pas", sysParaDao.getValue("xdkj_smspas"));
		}else{
			//微米账号的接口UID
    		para.put("uid", wc.getSmsuid());
    		//微米账号的接口密码
    		para.put("pas", wc.getSmspas());
		}
		//短信模板cid，通过微米后台创建
		SMSTemplate qfTemplate = null;
		//判断使用企业模板还是普通用户模板
		switch (c.getHousekind().getPid()){
		case 4:
			qfTemplate = smsTemplateDao.getQF(wc.getPid(),2);
			break;
			default:
				qfTemplate = smsTemplateDao.getQF(wc.getPid(),1);
				break;
		}
		
		if(qfTemplate == null){
			//西岛默认
			//短信模板cid
			para.put("cid", sysParaDao.getValue("xdkj_smsqfcid"));
			//传入模板参数。  第一个%P% 为p1,后面的++
			para.put("p1", c.getCustomerName());
            para.put("p2", c.getCustomerBalance().doubleValue()+"");
            para.put("p3", wc.getCompanyName());
		}else{
			switch (c.getHousekind().getPid()){
			case 4:
//				//企业
//				//短信模板cid
				para.put("cid", qfTemplate.getCid());
				//传入模板参数。  第一个%P% 为p1,后面的++
				if(qfTemplate.getPara_cnt() == 1){
					para.put("p1", c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
				}else{
					para.put("p1", c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
			        para.put("p2", c.getCustomerBalance().doubleValue()+"");
				}
				break;
			default:
				//短信模板cid
				para.put("cid", qfTemplate.getCid());
				//传入模板参数。  第一个%P% 为p1,后面的++
				para.put("p1", c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
		        para.put("p2", c.getCustomerBalance().doubleValue()+"");
				break;
			}
			
		}
        
		JSONObject jo = JSONObject.parseObject(HttpClientHelper.convertStreamToString(
				HttpClientHelper.get("http://api.weimi.cc/2/sms/send.html",
						para), "UTF-8"));
		
		return jo.get("code").toString();
	}

	/**
	 * 提醒通知  故障通知 温馨提示 SMS
	 * @param wcid
	 * @param cid
	 * @param para
	 * @param nbr_ids
	 */
	public void sendBreakDown(int wcid,String cid,String[] para,int[] nbr_ids){
		//选出小区中  今天没有发送过的合法的用户  一次发送短信
		/*select * from customer 
		where neighborid in (***) and CustomerMobile not in (
		select mobile from warnlog
		where ActionTime > curdate() and length(mobile) = 11
		) and length(CustomerMobile) = 11
		*/
		List<String> list = customerDao.getWarns(nbr_ids);
		for(int i = 0;i < list.size();i++){
			boolean done = false;
			String re = sendSMSBreakdown(wcid,cid,para,list.get(i));
			if(re.equals("0")){
				done = true;
			}
			//将提醒信息保存到数据库
			warnService.addWarnMobile(list.get(i),done,"re",cid);
		}
	}
	
	/**
	 * 提醒通知  故障通知 温馨提示 SMS
	 * @param wcid
	 * @param cid
	 * @param para_
	 * @param c
	 * @return
	 */
	private String sendSMSBreakdown(int wcid,String cid, String[] para_, String mobile) {
		Map<String, String> para = new HashMap<String, String>();

		
		//目标手机号码，多个以“,”分隔，一次性调用最多100个号码，示例：139********,138********
		para.put("mob", mobile);
		//接口返回类型：json、xml、txt。默认值为txt
		para.put("type", "json");
		
		
		Watercompany wc = waterCompanyService.getById(wcid+"");
		if(wc.getSmsuid() == null){
			//默认
			//微米账号的接口UID
			para.put("uid", sysParaDao.getValue("xdkj_smsuid"));
    		//微米账号的接口密码
    		para.put("pas", sysParaDao.getValue("xdkj_smspas"));
//    		//短信模板cid，通过微米后台创建
//            para.put("cid", "fxPLFfO74Vik");
		}else{
			//微米账号的接口UID
    		para.put("uid", wc.getSmsuid());
    		//微米账号的接口密码
    		para.put("pas", wc.getSmspas());
//    		//短信模板cid，通过微米后台创建
//            para.put("cid", "21ZHzcE6YZIB");
		}
		//短信模板cid，通过微米后台创建
		para.put("cid", cid);
		
		//传入模板参数。  第一个%P% 为p1,后面的++
		for(int i = 1;i <= para_.length;i++){
			para.put("p"+i, para_[i-1]);
		}
		
		JSONObject jo = JSONObject.parseObject(HttpClientHelper.convertStreamToString(
				HttpClientHelper.get("http://api.weimi.cc/2/sms/send.html",
						para), "UTF-8"));
		
		return jo.get("code").toString();
	}

	public static void main(String[] args) throws IOException {
		
//		Watercompany wc = new Watercompany();
//		Customer c = new Customer();
//		wc.setCompanyName("西岛");
//		wc.setEmailHost("smtp.163.com");
//		wc.setEmailUser("zffyxdkj@163.com");
//		wc.setEmailPassword("YWJjMTIzNDU2");
//		
//		c.setCustomerMobile("13176868783");
//		c.setCustomerEmail("582615540@qq.com");
//		c.setCustomerBalance(new BigDecimal(100.00));
//		c.setCustomerName("haha");
//		new WarnSender().sendMail(wc, c);
//		new WarnSender().sendSMS(wc, c);
		
		//test the BigDecimal compareTo 
		BigDecimal a = new BigDecimal(-1);
		if(a.compareTo(new BigDecimal(0)) != -1){
			System.out.println("!= -1");
		}
		System.out.println("== -1");
	}


}
