package com.xdkj.yccb.main.charge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.charge.service.ReadLogService;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.charge.service.WarnService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class SettleCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private ReadLogService readLogService;
	@Autowired
	private SettleService settleService;
	
	@RequestMapping(value="/charge/settle")
	public String readMeter(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/charge/settle";
	}
	
	@RequestMapping(value="/charge/settle/listreadlog",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listReadLog(int n_id){
		
		return readLogService.getReadLogToSettle(n_id);
	}
	
	@RequestMapping(value="/charge/settle/listsettle",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listSettle(int n_id){
		
		return JSON.toJSONString(settleService.getSettleData(n_id));
	}
	
	@RequestMapping(value="/charge/settle/settleall",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String settleAll(HttpServletRequest request,int n_id){
		UserForSession admin = WebUtil.getCurrUser(request);
		return settleService.settleAll(n_id,admin.getPid());
	}
	
	@RequestMapping(value="/charge/settle/settlesingle",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String settleSingle(HttpServletRequest request,int m_id){
		UserForSession admin = WebUtil.getCurrUser(request);
		return settleService.settleSingle(m_id,admin.getPid());
	}
}
