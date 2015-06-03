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
public class SettleLogWaterCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/statistics/settlelogwater")
	public String settleLogwater(){
		
		return "/statistics/settlelogwater";
	}
	
	@RequestMapping(value="/statistics/settlelogwater/listsettlewater",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettlewater(HttpServletRequest request,int year){
		/**
		 * 选出当前管理员下的小区  year 对应的全部结算过的用水统计
		 */
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		return neighborService.getSettledwater(neighbor_list,year);
	}
	
	@RequestMapping(value="/statistics/settlelogwater/draw",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String drawSettled(HttpServletRequest request,int year,int n_id){
		/**
		 * 当前小区  year 对应的全部结算过的用水统计
		 */
		return neighborService.getDrawSettledwater(n_id,year);
	}
}