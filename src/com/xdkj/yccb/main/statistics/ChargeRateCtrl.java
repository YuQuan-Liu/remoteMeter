package com.xdkj.yccb.main.statistics;


import java.util.ArrayList;
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
import com.xdkj.yccb.main.statistics.dto.ChargeRate;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class ChargeRateCtrl {
	
	@Autowired
	private NeighborService neighborService;
	
	@RequestMapping(value="/statistics/chargerate")
	public String settleLogwater(){
		
		return "/statistics/chargerate";
	}
	
	@RequestMapping(value="/statistics/chargerate/listchargerate",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettlewater(HttpServletRequest request,int year){
		/**
		 * 选出当前管理员下的小区  year  收费统计信息
		 */
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		return JSON.toJSONString(neighborService.getChargeRate(neighbor_list,year));
	}
	
	@RequestMapping(value="/statistics/chargerate/listchargeratedraw",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String drawchargerate(HttpServletRequest request,int year){
		/**
		 * 当前小区  year 对应的全部结算过的用水统计
		 */
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		return neighborService.getDrawChargerate(neighbor_list,year);
	}
	
	@RequestMapping(value="/statistics/chargerate/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model,int year) throws Exception{
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		List<ChargeRate> list_chargerate = neighborService.getChargeRate(neighbor_list,year);
		map.put("list", list_chargerate);
//		map.put("header","收费率统计");
		
		return new ModelAndView("chargerate",map);
	}
}