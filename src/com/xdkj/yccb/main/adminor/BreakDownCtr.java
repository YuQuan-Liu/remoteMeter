package com.xdkj.yccb.main.adminor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.common.encoder.Md5PwdEncoder;
import com.xdkj.yccb.main.adminor.dao.SMSTemplateDao;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.charge.WarnSender;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.SMSTemplate;

@Controller
public class BreakDownCtr {
	@Autowired
	private SMSTemplateDao smsTemplateDao;
	@Autowired
	private AdministratorService adminService;
	@Autowired
	private WarnSender warnSender;
	
	@RequestMapping(value="/admin/breakdown/list")
	public String breakdownList(HttpServletRequest request,Model model){
		List<SMSTemplate> templates = smsTemplateDao.getList(WebUtil.getCurrUser(request).getWaterComId());
		model.addAttribute("templates", templates);
		return "/adminor/breakdownList";
	}
	
	@RequestMapping(value="/admin/breakdown/sendsms",method=RequestMethod.POST)
	@ResponseBody
	public String sendSMS(HttpServletRequest request,String cid,int[] nbr_ids,String[] para,String pwd){
		
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		Admininfo admininfo = adminService.getById(WebUtil.getCurrUser(request).getPid()+"");
		Admininfo sadmininfo = adminService.getById(admininfo.getSid()+"");
		
		String result = "0";
		if(new Md5PwdEncoder().encodePassword(pwd).equals(sadmininfo.getLoginKey())){
			//选出小区中  今天没有发送过的合法的用户  一次发送短信
			warnSender.sendBreakDown(wcid,cid, para, nbr_ids);
			result = "1";
		}else{
			result = "2";
		}
		return result;
	}
}
