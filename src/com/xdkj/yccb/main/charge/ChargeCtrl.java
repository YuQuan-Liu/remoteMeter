package com.xdkj.yccb.main.charge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.charge.service.ChargeService;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.security.UserForSession;

/**
 * 收费
 * @author SMART
 *
 */
@Controller
public class ChargeCtrl {
	public static final String charge = "/charge/charge";
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private NeighborService neighborService;
	
	@RequestMapping(value = "/charge/charge",method = RequestMethod.GET)
	public String toCharge(HttpServletRequest request, Model model){
		//获取当前用户下的小区
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		return charge;
	}
	@RequestMapping(value="/charge/custinfo",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCustInfo(HttpServletRequest request){
		String nbrId = request.getParameter("nbrId");
		String custId = request.getParameter("custId");
		CustomerView cust = chargeService.getCustByNeibourAndCustId(nbrId, custId);
		return JSON.toJSONString(cust);
	}
	@RequestMapping(value="/charge/custMeters",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMeters(HttpServletRequest request){
		String custId = request.getParameter("custId");
		List<MeterView> list = custService.getMeterbyCid(custId); 
		return JSON.toJSONString(list);
		
	}
}
