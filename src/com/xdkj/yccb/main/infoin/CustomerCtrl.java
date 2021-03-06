package com.xdkj.yccb.main.infoin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.SysParaDao;
import com.xdkj.yccb.main.adminor.dto.MeterkindView;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.MeterkindService;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.infoin.service.impl.CustomerServiceImpl;
import com.xdkj.yccb.main.logger.ActionLog;
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
	@Autowired
	private SysParaDao sysParaDao;
	private static final Logger logger = LoggerFactory.getLogger(CustomerCtrl.class);
	
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
	
	@RequestMapping(value="/infoin/customer/addPages")
	public String addCustomerPages(){
		return "/infoin/customerAdds";
	}
	
	@RequestMapping(value="/infoin/customer/upload")
	@ResponseBody
	public String uploadExcel(HttpServletRequest request, String name,MultipartFile file){
		JSONObject jo = new JSONObject();
		String realPath = request.getServletContext().getRealPath("/WEB-INF/yccb/infoin/Excels");
//		System.out.println(name.substring(name.lastIndexOf("\\") + 1));
		if(!file.isEmpty()){
			try {
				byte[] bytes = file.getBytes();
				long time = Calendar.getInstance().getTimeInMillis();
				String excelPath = sysParaDao.getValue("server_UpPath")+time+name.substring(name.lastIndexOf("\\")+1);
				
				//log
				logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 18, "excelPath:"+excelPath).toString());
				
//				System.out.println(excelPath);
				File f = new File(excelPath);//new File(realPath+"\\"+name.substring(name.lastIndexOf("\\")+1));
				
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
                stream.write(bytes);
                stream.close();
                
                //read excel;
                Map map = UploadCustomer.readExcel(excelPath);
                if(map == null){
            		jo.put("done", false);
            		jo.put("reason", "读取数据异常");
                }else{
                	UserForSession admin = WebUtil.getCurrUser(request);
                	map.put("wcid", admin.getWaterComId());
                	Map result = customerService.addCustomers(map);
                	jo.put("done", true);
                	jo.put("success", result.get("success"));
                	jo.put("reason", result.get("reason"));
                	jo.put("c_add", result.get("c_add"));
                	jo.put("c_added", result.get("c_added"));
                	jo.put("m_add", result.get("m_add"));
                	jo.put("m_added", result.get("m_added"));
                	jo.put("c_nums", result.get("c_nums"));
                	jo.put("m_addrs", result.get("m_addrs"));
                }
			} catch (IOException e) {
				e.printStackTrace();
				jo.put("done", false);
        		jo.put("reason", e.getMessage());
			}
		}
		return jo.toJSONString();
	}
	
	@RequestMapping(value="/infoin/customer/add")
	@ResponseBody
	public String addCustomer(HttpServletRequest request,CustomerView cv){
		
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 14, "cnum:"+cv.getC_num()).toString());
		
		return JSON.toJSONString(customerService.addCustomer(cv,WebUtil.getCurrUser(request).getPid()));
	}
	
	@RequestMapping(value="/infoin/customer/delete")
	@ResponseBody
	public String deleteCustomer(HttpServletRequest request,int cid){

		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 15, "cpid:"+cid).toString());
		 
		return customerService.deleteCustomer(cid);
	}
	
	@RequestMapping(value="/infoin/customer/updatePage")
	public String updateCustomerPage(int cid,Model model){
		
		CustomerView cv = customerService.getCustomerViewbyCid(cid);
		model.addAttribute("cv", cv);
		return "/infoin/customerUpdate";
	}
	@RequestMapping(value="/infoin/customer/update")
	@ResponseBody
	public String updateCustomer(CustomerView cv){
		return customerService.updateCustomer(cv);
	}
	
	//更新某一行用户信息
	@RequestMapping(value="/infoin/customer/refresh",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refreshCustomer(int cid){
		return JSON.toJSONString(customerService.getCustomerViewbyCid(cid));
	}
	
	@RequestMapping(value="/infoin/meter/add")
	@ResponseBody
	public String addMeter(HttpServletRequest request,MeterView mv){
		UserForSession admin = WebUtil.getCurrUser(request);
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 16, "maddr:"+mv.getCollectorAddr()+"-"+mv.getMeterAddr()).toString());
		
		return JSON.toJSONString(customerService.addMeter(admin.getPid(),mv));
	}
	@RequestMapping(value="/infoin/meter/delete")
	@ResponseBody
	public String deleteMeter(HttpServletRequest request,int mid){
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 17, "mpid:"+mid).toString());
		
		return customerService.deleteMeter(mid);
	}
	@RequestMapping(value="/infoin/meter/updatePage")
	public String updateMeterPage(HttpServletRequest request,int mid,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		
		MeterView mv = customerService.getMeterViewbyMid(mid);
		model.addAttribute("mv", mv);
		
		//单价
		List<PriceKindView> pk_list = priceService.getList(admin.getWaterComId());
		model.addAttribute("pk_list", pk_list);
		
		//水表类型
		List<MeterkindView> mk_list = meterKindService.getList();
		model.addAttribute("mk_list", mk_list);
		
		return "/infoin/meterUpdate";
	}
	@RequestMapping(value="/infoin/meter/update")
	@ResponseBody
	public String updateMeter(HttpServletRequest request,MeterView mv){
		UserForSession admin = WebUtil.getCurrUser(request);
		return customerService.updateMeter(admin.getPid(),mv);
	}
	
	@RequestMapping(value="/infoin/customer/check_capid",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String check_capid(String c_apid){
		return JSON.toJSONString(customerService.check_capid(c_apid));
	}
	
	@RequestMapping(value="/infoin/customer/check_mapid",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String check_mapid(String m_apid){
		return JSON.toJSONString(customerService.check_mapid(m_apid));
	}
	
	@RequestMapping(value="/infoin/customer/check_maddr",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String check_maddr(String maddr,String caddr,int gprs_id){
		
		return JSON.toJSONString(customerService.check_maddr(maddr,caddr,gprs_id));
	}
	
	@RequestMapping(value="/infoin/customer/changemeter",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changemeter(HttpServletRequest request,String new_maddr,int end,int meterid){
		
//		System.out.println(new_maddr+"~"+new_read+"~"+meterid);
//		return JSON.toJSONString();
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 27, "changemeter~mid:"+meterid+"newaddr:"+new_maddr+"end:"+end).toString());
		
		return customerService.changemeter(new_maddr,end,meterid);
	}
	
	@RequestMapping(value="/infoin/customer/adjustmeter",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String adjustmeter(HttpServletRequest request,String gaddr,String caddr,String maddr,int customerid){
		
//		System.out.println(new_maddr+"~"+new_read+"~"+meterid);
//		return JSON.toJSONString();
		
		return customerService.adjustmeter(WebUtil.getCurrUser(request).getPid(),gaddr,caddr,maddr,customerid);
	}
}
