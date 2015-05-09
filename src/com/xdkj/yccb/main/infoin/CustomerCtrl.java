package com.xdkj.yccb.main.infoin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.MeterkindView;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.MeterkindService;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class CustomerCtrl {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private MeterkindService meterKindService;
	
	@RequestMapping(value="/infoin/customer/list")
	public String customerList(HttpServletRequest request,Model model){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		return "/infoin/customer";
	}
	
	@RequestMapping(value="/infoin/customer/ListCustomer",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListCustomer(String n_id,String c_num){
		
		return JSON.toJSONString(customerService.getCustomerby(n_id,c_num));
	}
	
	@RequestMapping(value="/infoin/customer/ListMetersByCid",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListMetersByCid(String cpid){
		
		return JSON.toJSONString(customerService.getMeterbyCid(cpid));
	}
	
	@RequestMapping(value="/infoin/customer/addPage")
	public String addCustomerPage(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		//小区
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		//单价
		List<PriceKindView> pk_list = priceService.getList(admin.getWaterComId());
		model.addAttribute("pk_list", pk_list);
		
		//水表类型
		List<MeterkindView> mk_list = meterKindService.getList();
		model.addAttribute("mk_list", mk_list);
		
		return "/infoin/customerAdd";
	}
	
	@RequestMapping(value="/infoin/customer/addsPage")
	public String addCustomerPages(){
		return "/infoin/customerAdds";
	}
	
	@RequestMapping(value="/infoin/customer/add")
	@ResponseBody
	public String addCustomer(CustomerView cv){
		return JSON.toJSONString(customerService.addCustomer(cv));
	}
	
	@RequestMapping(value="/infoin/meter/add")
	@ResponseBody
	public String addMeter(MeterView mv){
		System.out.println(mv);
		return JSON.toJSONString(customerService.addMeter(mv));
	}
	
}