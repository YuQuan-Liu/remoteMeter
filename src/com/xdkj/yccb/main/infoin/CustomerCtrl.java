package com.xdkj.yccb.main.infoin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.MeterkindView;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.MeterkindService;
import com.xdkj.yccb.main.adminor.service.PriceService;
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
				String excelPath = "D:/Excels/"+name.substring(name.lastIndexOf("\\")+1)+time;
				System.out.println(excelPath);
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
	public String addCustomer(CustomerView cv){
		return JSON.toJSONString(customerService.addCustomer(cv));
	}
	
	@RequestMapping(value="/infoin/customer/delete")
	@ResponseBody
	public String deleteCustomer(int cid){
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
		return JSON.toJSONString(customerService.updateCustomer(cv));
	}
	
	//更新某一行用户信息
	@RequestMapping(value="/infoin/customer/refresh",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refreshCustomer(int cid){
		return JSON.toJSONString(customerService.getCustomerViewbyCid(cid));
	}
	
	@RequestMapping(value="/infoin/meter/add")
	@ResponseBody
	public String addMeter(MeterView mv){
		return JSON.toJSONString(customerService.addMeter(mv));
	}
	@RequestMapping(value="/infoin/meter/delete")
	@ResponseBody
	public String deleteMeter(int mid){
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
	public String updateMeter(MeterView mv){
		return JSON.toJSONString(customerService.updateMeter(mv));
	}
}
