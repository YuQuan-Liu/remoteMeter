package com.xdkj.yccb.main.statistics;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.infoin.CustomerCtrl;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class OweCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/statistics/owe")
	public String settleLog(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		  
		return "/statistics/owe";
	}
	
	@RequestMapping(value="/statistics/owe/listlou",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listLous(int n_id){
		
		return JSON.toJSONString(neighborService.getLous(n_id));
	}
	
	@RequestMapping(value="/statistics/owe/listdy",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listDys(int n_id,String lou){
		
		return JSON.toJSONString(neighborService.getDys(n_id,lou));
	}
	
	@RequestMapping(value="/statistics/owe/listowe",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listOwe(int n_id,String lou,int pre,double low,String dy){
		
		return JSON.toJSONString(customerService.getOwes(n_id,lou,dy,pre,low));
	}
	
	@RequestMapping(value="/statistics/owe/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model,
			int n_id,String lou,int pre,double low,String dy,String n_name) throws Exception{
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		//根据小区ID  时间  预付费标识  获取结算对应的扣费信息
		Map map = new HashMap();
		List<ControlWarnView> list = customerService.getOwes(n_id,lou,dy,pre,low);
		map.put("list", list);
		map.put("header",new String(n_name.getBytes("ISO8859_1"), "utf-8")+"水费欠费统计");
		map.put("payaddr","交费地址");
		
		return new ModelAndView("owe",map);
	}
}