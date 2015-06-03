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
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class LouCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	
	@RequestMapping(value="/statistics/lou")
	public String lou(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		  
		return "/statistics/lou";
	}
	
	@RequestMapping(value="/statistics/lou/listlou",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listLous(int n_id){
		
		return JSON.toJSONString(neighborService.getLous(n_id));
	}
	
	
	@RequestMapping(value="/statistics/lou/listsettled",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettled(int n_id, int settle_id,int pre, String lou){
		
		return JSON.toJSONString(settleService.getLouSettledAll(n_id,settle_id,pre,lou));
	}
	
	@RequestMapping(value="/statistics/lou/listsettledyl",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettledyl(int n_id, int settle_id,int pre, String lou){
		
		return JSON.toJSONString(settleService.getLouSettledYL(n_id,settle_id,pre,lou));
	}
	
	@RequestMapping(value="/statistics/lou/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model,
			int n_id,String settle_time,int settle_id,String n_name,int pre, String lou) throws Exception{
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		//根据小区ID  时间  预付费标识  获取结算对应的扣费信息
		Map map = new HashMap();
		List<SettledView> list = settleService.getLouSettledAll(n_id,settle_id,pre,lou);
		map.put("list", list);
		String prestr = "";
		switch (pre) {
		case 1:
			prestr = "预付费";
			break;
		case 2:
			prestr = "全部";
			break;
		case 0:
			prestr = "后付费";
			break;
		default:
			break;
		}
		map.put("header",new String(n_name.getBytes("ISO8859_1"), "utf-8")+lou+"号楼"+settle_time+"扣费~"+prestr+"统计");
		
		List settlesum = settleService.getLouSettledYL(n_id,settle_id,pre,lou);
		map.put("lou", lou);
		map.put("settlesum", new JRBeanCollectionDataSource(settlesum));
		map.put("sub_dir", request.getServletContext().getRealPath("/WEB-INF/yccb/reports/")+"\\");
		
		return new ModelAndView("deductionlog",map);
	}
}