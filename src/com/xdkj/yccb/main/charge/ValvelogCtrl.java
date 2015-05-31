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
import com.xdkj.yccb.main.charge.service.ReadLogService;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class ValvelogCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	
	@RequestMapping(value="/charge/valvelog")
	public String valvelog(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/charge/valvelog";
	}
	
	@RequestMapping(value="/charge/valvelog/listsettleauto",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettleAuto(int n_id, int settle_id){
		
		return JSON.toJSONString(settleService.getSettleAuto(n_id,settle_id));
	}
	
}
