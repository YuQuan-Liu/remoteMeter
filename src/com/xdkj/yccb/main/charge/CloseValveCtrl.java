package com.xdkj.yccb.main.charge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dao.WaterCompanyDAO;
import com.xdkj.yccb.main.charge.service.ControlErrorService;
import com.xdkj.yccb.main.charge.service.WarnService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.readme.service.ReadService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class CloseValveCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private WarnService warnService;
	@Autowired
	private ControlErrorService controlErrorService;
	@Autowired
	private WarnSender warnSender;
	@Autowired
	private WaterCompanyDAO waterCompanyDAO;
	
	@RequestMapping(value="/charge/closevalve")
	public String readMeter(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/charge/closevalve";
	}
	

	@RequestMapping(value="/charge/valve/listcontrol",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListControl(int n_id){
		
		return JSON.toJSONString(warnService.getControlWarns(n_id));
	}
	
	

	@RequestMapping(value="/charge/valve/listerror",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListError(int n_id){
		
		return JSON.toJSONString(controlErrorService.getControlErrors(n_id));
	}
	
	@RequestMapping(value="/charge/valve/warnsingle",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String sendWarnSingle(HttpServletRequest request,int c_id){
		UserForSession admin = WebUtil.getCurrUser(request);
		warnSender.sendWarnSingle(waterCompanyDAO.getById(admin.getWaterComId()),c_id);
		return "";
	}
	
	@RequestMapping(value="/charge/valve/resolveError",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String resolveError(int conf_id,String reason){
		return controlErrorService.updateError(conf_id, reason);
	}
}
