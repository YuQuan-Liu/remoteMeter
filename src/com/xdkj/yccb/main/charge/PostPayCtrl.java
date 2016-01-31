package com.xdkj.yccb.main.charge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.TransRMB;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.WaterCompanyDAO;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.charge.dto.PostCharge;
import com.xdkj.yccb.main.charge.dto.QYCharge;
import com.xdkj.yccb.main.charge.dto.QYDetail;
import com.xdkj.yccb.main.charge.dto.QYMeters;
import com.xdkj.yccb.main.charge.service.ReadLogService;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
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
	private WarnSender warnSender;
	@Autowired
	private WaterCompanyDAO waterCompanyDAO;
	@Autowired
	private MeterDeductionLogService meterDeductionLogService;
	@Autowired
	private WaterCompanyService waterCompanyService;
	@Autowired
	private CustomerDao customerDao;
	
	
	@RequestMapping(value="/charge/postpay")
	public String postPay(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/charge/postpay";
	}
	
	@RequestMapping(value="/charge/postpay/listpostpay",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listPostPay(int n_id, int settle_id,String lou){
		
		return JSON.toJSONString(settleService.getSettledDataPostPay(n_id,settle_id,lou));
	}
	
	@RequestMapping(value="/charge/postpay/chargepostpay",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String chargepostpay(HttpServletRequest request,int[] mdl_ids){
		UserForSession admin = WebUtil.getCurrUser(request);
		return meterDeductionLogService.chargepostpay(admin.getPid(),mdl_ids);
	}
	
	@RequestMapping(value="/charge/postpay/warnpostpay",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String warnpostpay(HttpServletRequest request,int[] mdl_ids){
		
		JSONObject jo = new JSONObject();
		if(mdl_ids == null){
			jo.put("done", false);
			jo.put("reason", "无记录");
			return jo.toJSONString();
		}
		UserForSession admin = WebUtil.getCurrUser(request);
		warnSender.sendWarnPostPay(waterCompanyDAO.getById(admin.getWaterComId()), mdl_ids);
		jo.put("done", true);
		return jo.toJSONString();
	}
	
	@RequestMapping(value="/charge/postpay/printcharge")
	public ModelAndView postchargePrint(HttpServletRequest request,Model model,String ids) throws Exception{
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		List<PostCharge> list = meterDeductionLogService.getPostCharge(ids);

		if(list != null && list.size() >0){
			PostCharge postCharge = list.get(0);
			if(postCharge.getHkid() == 4){
				int settlelogid = postCharge.getSettlelogid();
				//企业
				List<Customer> clist = meterDeductionLogService.getCustomers(ids);
				List<QYCharge> qylist = new ArrayList<QYCharge>();
				for(int i = 0;clist != null && i < clist.size();i++){
					
					Customer c = clist.get(i);
//					System.out.println(c.getLouNum()+":"+c.getDyNum()+":"+c.getHuNum());
					
					//get the meter under the cid
					List<QYMeters> qymeters = meterDeductionLogService.getMeters(c.getPid(),settlelogid);
					int pkid_ = 0;
					HashMap<Integer,List<QYMeters>> pkid_map = new HashMap();
					//将qymeters中的表根据单价分组
					List<QYMeters> qymeters_map = null;
					for(int j=0;j<qymeters.size();j++){
						QYMeters qymeter_ = qymeters.get(j);
						if(pkid_ != qymeter_.getPkid()){
							pkid_ = qymeter_.getPkid();
							qymeters_map = new ArrayList<QYMeters>();
							pkid_map.put(pkid_, qymeters_map);
						}
						qymeters_map.add(qymeter_);
					}
					for(Entry<Integer, List<QYMeters>> entry: pkid_map.entrySet()){
						QYCharge qy = new QYCharge();
						qy.setCustomerName(c.getCustomerName());
						qy.setC_num(c.getLouNum().trim()+"-"+c.getDyNum().trim()+"-"+c.getHuNum().trim());
						List<QYMeters> qymeters_ = entry.getValue();
						qy.setMeters(new JRBeanCollectionDataSource(qymeters_));
						
						//get the detail under the cid
						int yl = 0;
						for(int j = 0;qymeters_ != null && j < qymeters_.size();j++){
							QYMeters meter = qymeters_.get(j);
							if(meter.getChangend() > 0){
								yl += meter.getChangend()-meter.getLast()+meter.getThis_()-meter.getMinusderead()-meter.getTovirtual();
							}else{
								yl += meter.getThis_()-meter.getLast()-meter.getMinusderead()-meter.getTovirtual();
							}
						}
						
						List<QYDetail> qydetail = meterDeductionLogService.getDetails(entry.getKey(),yl);
						qy.setDetail(new JRBeanCollectionDataSource(qydetail));
						BigDecimal sumdemoney = new BigDecimal(0);
						for(int j = 0;qydetail != null && j < qydetail.size();j++){
							QYDetail detail = qydetail.get(j);
							sumdemoney = sumdemoney.add(detail.getDemoney());
						}
						qy.setSumdemoney(sumdemoney);
						qylist.add(qy);
					}
				}
				map.put("list", qylist);
				UserForSession admin = WebUtil.getCurrUser(request);
				Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
				map.put("header",wc.getCompanyName()+"水费备款单");
				map.put("sub_dir", request.getServletContext().getRealPath("/WEB-INF/yccb/reports/")+"\\");
				return new ModelAndView("qycharge",map);
			}else{
				//普通
				for(int i = 0;list != null && i < list.size();i++){
					postCharge = list.get(i);
					postCharge.setCnDemoney(TransRMB.transform(postCharge.getDemoney()+""));
				}
				map.put("list", list);
				UserForSession admin = WebUtil.getCurrUser(request);
				Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
				map.put("header",wc.getCompanyName()+"收费单");
				map.put("tel",wc.getTelephone());
				
				return new ModelAndView("postcharge",map);
			}
		}
		return null;
		
	}
	
	@RequestMapping(value="/charge/postpay/printchargeall")
	public ModelAndView postchargePrintAll(HttpServletRequest request,Model model,int n_id,int settle_id,String lou) throws Exception{
		
		//根据小区ID  时间  预付费标识  获取用户的交费信息
		Map map = new HashMap();
		List<PostCharge> list = meterDeductionLogService.getPostChargeLou(n_id,settle_id,lou);

		if(list != null && list.size() >0){
			PostCharge postCharge = list.get(0);
			if(postCharge.getHkid() == 4){
				int settlelogid = postCharge.getSettlelogid();
				//企业
				List<Customer> clist = customerDao.getCustomerList(n_id+"",lou);
				List<QYCharge> qylist = new ArrayList<QYCharge>();
				for(int i = 0;clist != null && i < clist.size();i++){
					Customer c = clist.get(i);
					//get the meter under the cid
					List<QYMeters> qymeters = meterDeductionLogService.getMeters(c.getPid(),settlelogid);
					int pkid_ =  0;
					HashMap<Integer,List<QYMeters>> pkid_map = new HashMap();
					//将qymeters中的表根据单价分组
					List<QYMeters> qymeters_map = null;
					for(int j=0;j<qymeters.size();j++){
						QYMeters qymeter_ = qymeters.get(j);
						if(pkid_ != qymeter_.getPkid()){
							pkid_ = qymeter_.getPkid();
							qymeters_map = new ArrayList<QYMeters>();
							pkid_map.put(pkid_, qymeters_map);
						}
						qymeters_map.add(qymeter_);
					}
					for(Entry<Integer, List<QYMeters>> entry: pkid_map.entrySet()){
						QYCharge qy = new QYCharge();
						qy.setCustomerName(c.getCustomerName());
						qy.setC_num(c.getLouNum().trim()+"-"+c.getDyNum().trim()+"-"+c.getHuNum().trim());
						List<QYMeters> qymeters_ = entry.getValue();
						qy.setMeters(new JRBeanCollectionDataSource(qymeters_));
						
						//get the detail under the cid
						int yl = 0;
						for(int j = 0;qymeters_ != null && j < qymeters_.size();j++){
							QYMeters meter = qymeters_.get(j);
							if(meter.getChangend() > 0){
								yl += meter.getChangend()-meter.getLast()+meter.getThis_()-meter.getMinusderead()-meter.getTovirtual();
							}else{
								yl += meter.getThis_()-meter.getLast()-meter.getMinusderead()-meter.getTovirtual();
							}
						}
						
						List<QYDetail> qydetail = meterDeductionLogService.getDetails(entry.getKey(),yl);
						qy.setDetail(new JRBeanCollectionDataSource(qydetail));
						BigDecimal sumdemoney = new BigDecimal(0);
						for(int j = 0;qydetail != null && j < qydetail.size();j++){
							QYDetail detail = qydetail.get(j);
							sumdemoney = sumdemoney.add(detail.getDemoney());
						}
						qy.setSumdemoney(sumdemoney);
						qylist.add(qy);
					}
				}
				map.put("list", qylist);
				UserForSession admin = WebUtil.getCurrUser(request);
				Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
				map.put("header",wc.getCompanyName()+"水费备款单");
				map.put("sub_dir", request.getServletContext().getRealPath("/WEB-INF/yccb/reports/")+"\\");
				return new ModelAndView("qycharge",map);
			}else{
				for(int i = 0;list != null && i < list.size();i++){
					postCharge = list.get(i);
					postCharge.setCnDemoney(TransRMB.transform(postCharge.getDemoney()+""));
				}
				map.put("list", list);
				UserForSession admin = WebUtil.getCurrUser(request);
				Watercompany wc = waterCompanyService.getById(admin.getWaterComId()+"");
				map.put("header",wc.getCompanyName()+"收费单");
				map.put("tel",wc.getTelephone());
				
				return new ModelAndView("postcharge",map);
			}
		}
		return null;
	}
}
