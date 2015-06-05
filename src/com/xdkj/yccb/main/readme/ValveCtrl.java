package com.xdkj.yccb.main.readme;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.readme.service.ReadService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class ValveCtrl {
	
	@Autowired
	private AdministratorDAO adminDao;
	@Autowired
	private ValveControl valveControl;
	@Autowired
	private MeterService meterService;
	@Autowired
	private ReadService readService;
	
	@RequestMapping(value="/readme/valve/valvecontrol",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ValveControl(HttpServletRequest request,String m_id,int control){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		return valveControl.valveControl(meterService.getMeterbyPID(m_id), adminDao.getById(admin.getPid()),control);
		
	}

	@RequestMapping(value="/readme/valve/valvecontrolall",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ValveControlAll(HttpServletRequest request,String[] m_ids){
		
		JSONObject jo = new JSONObject();
		if(m_ids == null){
			jo.put("function", "valve");
			jo.put("pid", "0");
			jo.put("result", "fail");
			jo.put("reason", "无记录");
			return jo.toJSONString();
		}
		
		UserForSession admin = WebUtil.getCurrUser(request);
		Object[] ids = new HashSet<>(Arrays.asList(m_ids)).toArray();
		return valveControl.valveControlAll(ids, adminDao.getById(admin.getPid()));
		
	}
	
	@RequestMapping(value="/readme/valve/checkcontroling",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ChangeRead(HttpServletRequest request,int valvelogid){
		UserForSession admin = WebUtil.getCurrUser(request);
		return readService.checkControling(valvelogid, admin.getPid());
		
	}
}
