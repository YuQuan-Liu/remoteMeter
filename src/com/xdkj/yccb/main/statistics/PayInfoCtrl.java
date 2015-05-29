package com.xdkj.yccb.main.statistics;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.json.JSONString;
import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class PayInfoCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private PayLogService payLogService;
	
	@RequestMapping(value="/statistics/payinfo")
	public String payInfo(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/statistics/payinfo";
	}
	
	@RequestMapping(value="/statistics/payinfo/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model,
			int n_id,String start,String end,String n_name,int pre) throws Exception{
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		List list = payLogService.getCustomerPayLogs(n_id,start,end,pre);
		map.put("list", list);
		map.put("header",new String(n_name.getBytes("ISO8859_1"), "utf-8")+start+"~"+end+"收费统计");
		
		List admin_sum = payLogService.getAdminSum(n_id,start,end,pre);
		map.put("adminsum", new JRBeanCollectionDataSource(admin_sum));
		map.put("sub_dir", request.getServletContext().getRealPath("/WEB-INF/yccb/reports/")+"\\");
		
		return new ModelAndView("payinfo",map);
	}
}