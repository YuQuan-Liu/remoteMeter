package com.xdkj.yccb.main.statistics;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.xdkj.yccb.main.charge.dto.QYSettledView;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.dto.StepYL;
import com.xdkj.yccb.main.statistics.service.PayLogService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class SettleLogCtrl {
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private SettleService settleService;
	
	@RequestMapping(value="/statistics/settlelog")
	public String settleLog(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		  
		return "/statistics/settlelog";
	}
	
	@RequestMapping(value="/statistics/settlelog/listsettled",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettled(int n_id, int settle_id,int pre){
		
		return JSON.toJSONString(settleService.getSettledAll(n_id,settle_id,pre));
	}
	
	@RequestMapping(value="/statistics/settlelog/listsettledyl",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listsettledyl(int n_id, int settle_id,int pre){
		
		return JSON.toJSONString(settleService.getSettledYL(n_id,settle_id,pre));
	}
	
	@RequestMapping(value="/statistics/settlelog/listjietiyl",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listjietiyl(int n_id, int settle_id,int pre){
		List<StepYL> step = new ArrayList<StepYL>();
		step.add(settleService.getJietiYL(n_id,settle_id,pre));
		return JSON.toJSONString(step);
	}
	
	@RequestMapping(value="/statistics/settlelog/print")
	public ModelAndView payInfoPrint(HttpServletRequest request,Model model,
			int n_id,String settle_time,int settle_id,String n_name,int pre) throws Exception{
//		UserForSession admin = WebUtil.getCurrUser(request);
//		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
//		model.addAttribute("neighbor_list", neighbor_list);
//		
		//根据小区ID  时间  预付费标识  获取结算对应的扣费信息
		String nname = new String(n_name.getBytes("ISO8859_1"), "utf-8");
		if(nname.equals("企业")){
			Map map = new HashMap();
			List<QYSettledView> list = settleService.getQYSettledAll(n_id,settle_id);
			
			int sumgy = 0;
			int sumjz = 0;
			int sumjm = 0;
			int summinus = 0;
			QYSettledView view = null;
			for(int i = 0;list != null && i < list.size();i++){
				view = list.get(i);
				int yl = 0;
				if(view.getChangend() > 0){
					yl = view.getChangend()-view.getLastderead()+view.getMeterread()-view.getMinusderead()-view.getTovirtual();
					view.setRemark(view.getRemark()+view.getChangend()+"换表");
				}else{
					yl = view.getMeterread()-view.getLastderead()-view.getMinusderead()-view.getTovirtual();
				}
				if(view.getMinusderead()>0){
					summinus += view.getMinusderead();
					view.setRemark(view.getRemark()+"减免"+view.getMinusderead());
				}
				if(view.getTovirtual()>0){
					view.setRemark(view.getRemark()+"虚表"+view.getTovirtual());
				}
				switch (view.getPkid()){
				case 2:
					//2.9  居民
					view.setJm(yl);
					sumjm += yl;
					break;
				case 3:
					//3.9 工业
					view.setGy(yl);
					sumgy += yl;
					break;
				case 4:
					//10.1 建筑
					view.setJz(yl);
					sumjz += yl;
					break;
				case 12:
					//5 工业
					view.setGy(yl);
					sumgy += yl;
					break;
				case 14:
					//16.2 建筑
					view.setJz(yl);
					sumjz += yl;
					break;
				case 15:
					//3.2 居民
					view.setJm(yl);
					sumjm += yl;
					break;
				case 13:
					//3.4 居民
					view.setJm(yl);
					sumjm += yl;
					break;
					default:
						view.setRemark(view.getRemark()+"单价异常");
						break;
				}
			}
			map.put("count", list.size());
			map.put("sumjm", sumjm);
			map.put("sumgy", sumgy);
			map.put("sumjz", sumjz);
			map.put("summinus", summinus);
			map.put("list", list);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date settletime = df.parse(settle_time);
			map.put("header","用户审批的计划用水量和"+(settletime.getMonth()+1)+"月份实际用水量");
			
			return new ModelAndView("qydeductionlog",map);
		}else{
			Map map = new HashMap();
			List<SettledView> list = settleService.getSettledAll(n_id,settle_id,pre);
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
			map.put("header",nname+settle_time+"扣费~"+prestr+"扣费统计");
			
			List settlesum = settleService.getSettledYL(n_id,settle_id,pre);
			map.put("settlesum", new JRBeanCollectionDataSource(settlesum));
			map.put("sub_dir", request.getServletContext().getRealPath("/WEB-INF/yccb/reports/")+"\\");
			
			List<NeighborBalance> neighborBalance = neighborService.getNeighborBalance(n_id);
			map.put("neighborbalance", new JRBeanCollectionDataSource(neighborBalance));
			return new ModelAndView("deductionlog",map);
		}
	}
	
	
	
	
	
}