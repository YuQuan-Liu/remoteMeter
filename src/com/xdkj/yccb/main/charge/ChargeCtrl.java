package com.xdkj.yccb.main.charge;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	//public static final String chargePay = "/charge/payInfo";
	public static final int logCount = 10;//显示收费记录条数
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private NeighborService neighborService;
	/**
	 * 跳转收费页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/charge/charge",method = RequestMethod.GET)
	public String toCharge(HttpServletRequest request, Model model){
		//获取当前用户下的小区
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		return charge;
	}
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/charge/custinfo",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCustInfo(HttpServletRequest request) throws UnsupportedEncodingException{
		String nbrId = request.getParameter("nbrId");
		String custId = new String( request.getParameter("custId").getBytes("ISO-8859-1") , "UTF-8");
		CustomerView cust = chargeService.getCustByNeibourAndCustId(nbrId, custId);
		return JSON.toJSONString(cust);
	}
	/**
	 * 根据用户信息获取表信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/charge/custMeters",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMeters(HttpServletRequest request){
		String custId = request.getParameter("custId");
		List<MeterView> list = custService.getMeterbyCid(custId); 
		return JSON.toJSONString(list);
	}
	/**
	 * 更新用户信息
	 * @param cv
	 * @return
	 */
	@RequestMapping(value="/charge/updateCustInfo",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateCustInfo(CustomerView cv){
		return JSON.toJSONString(custService.updateCustomer(cv));
	}
	/**
	 * 预后付费转换
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/charge/updatePrepaySign")
	@ResponseBody
	public String changePay(HttpServletRequest request){
		String prePaySign = request.getParameter("prePaySign");
		String custId = request.getParameter("custId");
		return chargeService.updatePayment(custId, prePaySign);
	}
	/**
	 * Description: 收费弹窗 显示缴费信息 扣费信息
	 * @param custId
	 * @return 页面
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	/*@RequestMapping(value="/charge/chargePay")
	public String payInfoPage(@RequestParam("custId")String custId,Model model){
		model.addAttribute("custId", custId);
		return chargePay;
	}*/
	/**
	 * Description: 收费记录
	 * @param custId
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	@RequestMapping(value="/charge/payInfoContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String payInfo(@RequestParam("custId") String custId){
		return JSON.toJSONString(chargeService.getCList(custId, logCount));
	}
	/**
	 * Description: 扣费记录
	 * @param custId
	 * @return
	 * @author SongWei
	 * @date 2015-5-24
	 * @version 1.0
	 */
	@RequestMapping(value="/charge/costInfoContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String costInfo(@RequestParam("custId") String custId){
		return JSON.toJSONString(chargeService.getMList(custId, logCount));
	}
}
