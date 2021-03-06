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
public class WasteCtrl {
	
	@Autowired
	private NeighborService neighborService;
	
	@RequestMapping(value="/statistics/waste")
	public String waste(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		  
		return "/statistics/waste";
	}
	
	@RequestMapping(value="/statistics/waste/listwastedata",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getWaste(int n_id,int year){
		/**
		 * 选出当前小区 本年度对应的所有的扣费记录对应抄表记录的水损分析 小区总表  每个月最后的扣费记录  画图
		 */
		return neighborService.getWaste(n_id,year);
	}
	
	@RequestMapping(value="/statistics/waste/listsettledyl",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSettledyl(int n_id,int year){
		/**
		 * 选出当前小区 本年度对应的所有的扣费记录对应抄表记录的水损分析  填表
		 */
		return JSON.toJSONString(neighborService.getSettledyl(n_id,year));
	}
	
}