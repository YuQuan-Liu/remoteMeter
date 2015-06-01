package com.xdkj.yccb.main.charge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.type.JsonOperatorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.TransRMB;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.service.ReadLogService;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
import com.xdkj.yccb.main.statistics.service.MeterDeductionLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class PostPayCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	@Autowired
	private MeterDeductionLogService meterDeductionLogService;
	
	@RequestMapping(value="/charge/postpay")
	public String postPay(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/charge/postpay";
	}
	
	@RequestMapping(value="/charge/postpay/listpostpay",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listPostPay(int n_id, int settle_id){
		
		return JSON.toJSONString(settleService.getSettleDataPostPay(n_id,settle_id));
	}
	
	@RequestMapping(value="/charge/postpay/chargepostpay",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String chargepostpay(HttpServletRequest request,int[] mdl_ids){
		UserForSession admin = WebUtil.getCurrUser(request);
		return meterDeductionLogService.chargepostpay(admin.getPid(),mdl_ids);
	}
	
	@RequestMapping(value="/charge/postpay/printcharge")
	public ModelAndView postchargePrint(HttpServletRequest request,Model model,String ids) throws Exception{
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		List<PostCharge> list = meterDeductionLogService.getPostCharge(ids);

		PostCharge postCharge = null;
		for(int i = 0;list != null && i < list.size();i++){
			postCharge = list.get(i);
			postCharge.setCnAmount(TransRMB.transform(postCharge.getAmount()+""));
		}
		map.put("list", list);
		map.put("header","***后付费***");
		map.put("tel","13176868783");
		
		return new ModelAndView("postcharge",map);
	}
}