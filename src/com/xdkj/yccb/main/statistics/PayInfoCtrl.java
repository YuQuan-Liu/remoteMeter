package com.xdkj.yccb.main.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class PayInfoCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/statistics/payinfo")
	public String payInfo(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/statistics/payinfo";
	}
	
	@RequestMapping(value="/statistics/payinfo/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model){
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		Map map = new HashMap();
		List list = customerService.getMeterbyCid("2");
		map.put("list", list);
		map.put("hi","hello");
		return new ModelAndView("payinfo",map);
	}
}