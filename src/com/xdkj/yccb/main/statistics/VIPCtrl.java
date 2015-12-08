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
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class VIPCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private MeterService meterService;
	
	@RequestMapping(value="/statistics/vip")
	public String vip(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		  
		return "/statistics/vip";
	}
	
	@RequestMapping(value="/statistics/vip/listvipdata",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listVIPMonitor(int n_id,String start,int module){
		/**
		 * 根据显示模式  显示出start时间对应的 小区下的所有的检测表的数据   和 D10的数据
		 * module == 1(月模式，即start月内每天的最后一次数据)
		 * module == 2(日模式，即start那一天每个小时内最后一次数据)
		 */
		return meterService.getVIPMonitor(n_id,start,module);
		
//		String x = "[{\"data\":[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3333,3333,3333,0,0,0,3333,0,0,0,3333,3334,3333],\"meteraddr\":\"55550000000001\"}," +
//				"{\"data\":[0,22,0,0,1,0,20,0,4440,0,0,0,0,0,0,0,0,0,3333,3333,3333,0,0,0,3333,0,0,0,3333,3334,3333],\"meteraddr\":\"5555000000033\"}," +
//				"{\"data\":[0,202,0,0,1,0,20,0,450,0,0,0,0,0,0,0,0,0,2222,3333,3333,0,0,0,3333,0,0,0,3333,3334,3333],\"meteraddr\":\"5555000000044\"}]";
//		return x;
	}
}