package com.xdkj.yccb.main.charge;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.processors.JsonBeanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.TransRMB;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.charge.dto.CustomerpaylogView;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.service.ChargeService;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.logger.ActionLogService;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;
import com.xdkj.yccb.security.UserForSession;

/**
 * 收费
 * @author SMART
 *
 */
@Controller
public class ChargeCtrl {
	public static final String charge = "/charge/charge";
	public static final int logCount = 10;//显示收费记录条数
	@Autowired
	private ChargeService chargeService;
	@Autowired
	private CustomerService custService;
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private PriceService priceService; 
	@Autowired
	private WaterCompanyService waterCompanyService;
	@Autowired
	private ActionLogService actionLogService;
	
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
		//界面用到了撤销权限
		model.addAttribute("userInfo", admin);
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
	public String changePay(HttpServletRequest request,int custId,int prePaySign){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 22, "cid:"+custId+"~prepaySign:"+prePaySign);
		
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
	public String payInfo(@RequestParam("custId") int custId){
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
	public String costInfo(@RequestParam("custId") int custId){
		return JSON.toJSONString(chargeService.getMList(custId, logCount));
	}
	
	
	@RequestMapping(value ="/charge/updatePrice")
	@ResponseBody
	public String updatePrice(HttpServletRequest request,@RequestParam("priceId") String priceId,
			@RequestParam("meterId") String meterId){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 23, "mid:"+meterId+"~newpriceId:"+priceId);
		
		return chargeService.updatePrice(meterId, priceId);
	}
	
	@RequestMapping(value ="/charge/updateDeread")
	@ResponseBody
	public String updateDeread(HttpServletRequest request,int deread,int m_id,int old){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 31, "mid:"+m_id+"~deread:"+deread+"~old:"+old);
		
		return chargeService.updateDeread(m_id, deread);
	}
	
	@RequestMapping(value="/cahrge/canclePay")
	@ResponseBody
	public String canclePay(HttpServletRequest request,@RequestParam("custPayId") String custPayId){
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 25, "custPayId:"+custPayId);
		
		return chargeService.cancleCustPay(custPayId);
	}
	@RequestMapping(value="/charge/cancleCost")
	@ResponseBody
	public String cancleCost(HttpServletRequest request,@RequestParam("meterDeLogId") String meterDeLogId){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 26, "meterDeLogId:"+meterDeLogId);
		
		return chargeService.cancleCost(meterDeLogId);
	}
	
	@RequestMapping(value="/charge/pay")
	@ResponseBody
	public String pay(HttpServletRequest request,int c_id,BigDecimal amount){
		UserForSession admin = WebUtil.getCurrUser(request);
		return chargeService.addpay(admin.getPid(),c_id,amount);
	}
	
	@RequestMapping(value="/charge/charge/printcharge")
	public ModelAndView chargePrint(HttpServletRequest request,Model model,int cplid) throws Exception{
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		
		CustomerpaylogView paylogview = chargeService.getPaylog(cplid);
		if(paylogview != null){
			map.put("c_num", paylogview.getC_num());
			map.put("customerId", paylogview.getCustomerId());
			map.put("customerName", paylogview.getCustomerName());
			map.put("customerAddr", paylogview.getCustomerAddr());
			map.put("adminName", paylogview.getAdminName());
			map.put("amount", paylogview.getAmount().doubleValue()+"");
			map.put("cnAmount", TransRMB.transform(paylogview.getAmount().toString()));
			map.put("customerBalance", paylogview.getCustomerBalance().doubleValue()+"");
		}
		//fake data to the datasourse    in the jasper we should use field instead of parameter
		List list = new ArrayList();
		list.add(paylogview);
		map.put("list", list);
		UserForSession admin = WebUtil.getCurrUser(request);
		Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
		map.put("header",wc.getCompanyName()+"收费单");
		map.put("tel",wc.getTelephone());
		
		return new ModelAndView("charge",map);
	}
	
	@RequestMapping(value="/charge/charge/printdetailcharge")
	public ModelAndView detailPrint(HttpServletRequest request,Model model,int cid,int cplid) throws Exception{
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		

		//根据交费记录  获取  本次交费记录  上一次交费记录  的信息  获取时间   打印详单使用
		List<Customerpaylog> paylogs = chargeService.getPaylogLimit2(cid,cplid);
		if(paylogs.size() == 1){
			//如果只有一条  则添加一个当前日期当选择扣费信息的结束
			Customerpaylog cpl = new Customerpaylog();
			cpl.setActionTime(new Date());
			paylogs.add(cpl);
		}
		List<SettledView> list = new ArrayList<>();
		
		BigDecimal sumDemoney = new BigDecimal(0);
		/**
		 * 获取用户下  两条交费记录之间的扣费信息
		 */
		list = chargeService.getMeterDeLog(cid,paylogs.get(0).getActionTime(),paylogs.get(1).getActionTime());
		SettledView view = null;
		for(int i = 0;i < list.size();i++){
			view = list.get(i);
			sumDemoney = sumDemoney.add(view.getDemoney());
		}
		
		//当前交费记录之后的所有的扣费信息
		List<SettledView> listsumde = chargeService.getMeterDeLog(cid,paylogs.get(0).getActionTime(),new Date());
		BigDecimal sumafterde = new BigDecimal(0);;
		for(int i = 0;i < listsumde.size();i++){
			view = listsumde.get(i);
			sumafterde = sumafterde.add(view.getDemoney());
		}
		
		BigDecimal sumafterpay = chargeService.sumAfterPay(cid,cplid);
		

		CustomerView cv = custService.getCustomerViewbyCid(cid);
		BigDecimal lastBalance = cv.getCustomerBalance();   //上次交费余额
		lastBalance = lastBalance.add(sumafterde).subtract(paylogs.get(0).getAmount()).subtract(sumafterpay);
		
		BigDecimal thisBalance = lastBalance.add(paylogs.get(0).getAmount()).subtract(sumDemoney);
		
		map.put("sumDemoney", sumDemoney.doubleValue());
		map.put("list",list);
		map.put("amount", paylogs.get(0).getAmount().doubleValue());
		map.put("lastBalance", lastBalance.doubleValue());
		map.put("thisBalance", thisBalance.doubleValue());
		map.put("c_num", cv.getC_num());
		map.put("customerName", cv.getCustomerName());
		map.put("customerAddr", cv.getCustomerAddr());
		
		UserForSession admin = WebUtil.getCurrUser(request);
		Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
		map.put("header",wc.getCompanyName()+"扣费详单");
		
		return new ModelAndView("predetail",map);
	}
	
	@RequestMapping(value="/charge/charge/draw",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String draw(int mid){
		/**
		 * 当前表具今年对应的全部的扣费读数
		 */
		return chargeService.getDrawMeter(mid);
	}
}
