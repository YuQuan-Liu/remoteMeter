package com.xdkj.yccb.main.charge;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.PriceService;
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
	public static final String meterCurve = "/charge/meterQX";
	public static final int logCount = 10;//显示收费记录条数
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private PriceService priceService; 
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
		//获取自来水公司下的单价
		List<PriceKindView> price_list = priceService.getList(admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		model.addAttribute("price_list", price_list);
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
	public String getCustInfo(HttpServletRequest request,String nbrId,String custId) throws UnsupportedEncodingException{
//		String nbrId = request.getParameter("nbrId");
//		String custId = new String( request.getParameter("custId").getBytes("ISO-8859-1") , "UTF-8");
//		CustomerView cust = chargeService.getCustByNeibourAndCustId(nbrId, custId);
		List<CustomerView> list = custService.getCustomerby(nbrId,custId);
		if(list.size() == 0){
			return "{}";
		}else{
			return JSON.toJSONString(list.get(0));
		}
		
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
		return JSON.toJSONString(custService.updateCustomerInfo(cv));
	}
	/**
	 * 预后付费转换
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/charge/updatePrepaySign")
	@ResponseBody
	public String changePay(int custId,int prePaySign){
		return chargeService.updatePayment(custId, prePaySign);
	}

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
	/**
	 * 开阀
	 * @param mId
	 * @return jsonString succ or fail 
	 */
	@RequestMapping(value ="/charge/openValue")
	@ResponseBody
	public String openValue(@RequestParam("meterId") String mId){
		return chargeService.changeValue(mId, "1");
	}
	
	@RequestMapping(value ="/charge/updatePrice")
	@ResponseBody
	public String updatePrice(@RequestParam("priceId") String priceId,
			@RequestParam("meterId") String meterId){
		return chargeService.updatePrice(meterId, priceId);
	}
	@RequestMapping(value ="/charge/meterCurve")
	public String meterCurve(){
		return meterCurve;
	}
	
	@RequestMapping(value="/cahrge/canclePay")
	@ResponseBody
	public String canclePay(@RequestParam("custPayId") String custPayId){
		return chargeService.cancleCustPay(custPayId);
	}
	@RequestMapping(value="/charge/cancleCost")
	@ResponseBody
	public String cancleCost(@RequestParam("meterDeLogId") String meterDeLogId){
		
		return chargeService.cancleCost(meterDeLogId);
	}
}
