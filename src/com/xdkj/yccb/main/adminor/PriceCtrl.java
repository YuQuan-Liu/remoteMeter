package com.xdkj.yccb.main.adminor;

import java.util.Arrays;
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
import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.BasicpriceValues;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.entity.Pricekind;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.logger.ActionLogService;
import com.xdkj.yccb.security.UserForSession;
/**
 * 单价
 * @author SGR
 *
 */
@Controller
public class PriceCtrl {
	@Autowired
	private PriceService priceService;
	@Autowired
	private ActionLogService actionLogService;
	
	public static final String priceList = "/adminor/priceList";
	public static final String priceAdd = "/adminor/priceAdd";
	public static final String priceDetail = "/adminor/priceDetail";
	
	@RequestMapping(value="/admin/price/list",method=RequestMethod.GET)
	public String priceList(){
		return priceList;
	}
	
	@RequestMapping(value = "/admin/price/priceAddPage",method = RequestMethod.GET)
	public String priceAdd(){
		return priceAdd;
	}
	
	@RequestMapping(value = "/admin/price/priceDetailPage",method = RequestMethod.GET)
	public String priceDetail(@RequestParam("priceId") int pid,Model model){
		model.addAttribute("price", priceService.getById(pid));
		return priceDetail;
	}
	
	@RequestMapping(value="/admin/price/listContent",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String priceListContent(HttpServletRequest request){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<PriceKindView> list = priceService.getList(admin.getWaterComId());
		return JSON.toJSONString(list);
	}
	/**
	 * 获取单价下基本单价信息
	 * @param pid 单价id
	 * @return 基本单价 json数据
	 */
	@RequestMapping(value = "/admin/price/priceListContent",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String basicPriceListContent(@RequestParam("priceId") int pid){
		return JSON.toJSONString(priceService.getListByPriceKindId(pid));
	}
	
	@RequestMapping(value="/admin/price/addprice")
	@ResponseBody
	public String addPriceKind(Pricekind pk,BasicpriceValues bpv,HttpServletRequest request){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 7, "pricekindname:"+pk.getPriceKindName());
		
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		pk.setWatercompany(new Watercompany(wcid));
		return priceService.addPriceKind(pk,bpv);
	}
	
	@RequestMapping(value="/admin/price/checkpkname")
	@ResponseBody
	public String checkpkname(String pkname,HttpServletRequest request){
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(priceService.checkPKname(pkname,wcid));
	}

	@RequestMapping(value="/admin/price/deletepk",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deletePK(HttpServletRequest request,int pid){
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 8, "pricekindid:"+pid);
		return JSON.toJSONString(priceService.deletePK(pid));
	}
	
	@RequestMapping(value="/admin/price/pricechange")
	public String pricechange(Model model,HttpServletRequest request){
		UserForSession admin = WebUtil.getCurrUser(request);
		//单价
		List<PriceKindView> pk_list = priceService.getList(admin.getWaterComId());
		model.addAttribute("pk_list", pk_list);
				
		return "/adminor/priceChange";
	}
	
	@RequestMapping(value="/admin/price/changepk",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changepk(HttpServletRequest request,int old_,int new_){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 9, "old_:"+old_+"new_:"+new_);
		return JSON.toJSONString(priceService.changepk(old_,new_));
	}
}
