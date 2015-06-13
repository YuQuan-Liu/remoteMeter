package com.xdkj.yccb.main.readme;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.infoin.UploadCustomer;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.readme.service.ReadService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class NonRemoteCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private ReadService readService;
	@Autowired
	private AdministratorDAO adminDao;
	@Autowired
	private MeterService meterService;
	
	@Autowired
	private ReadLogDao readlogDao;
	
	@RequestMapping(value="/readme/read/unremotelist")
	public String nonRemoteList(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/readme/nonremote";
	}
	
	@RequestMapping(value="/readme/nonremote/listnonremote",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListNonRemote(String n_id){
		
		return JSON.toJSONString(readService.getNonRemoteMeters(n_id));
	}
	
	@RequestMapping(value="/readme/nonremote/readloglist",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListNonSettleReadlog(String n_id){
		
		return readlogDao.getReadLogNeighborsNonSettle(n_id);
	}
	
	@RequestMapping(value="/readme/nonremote/addnonremote",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String AddNonRemote(int m_id,int newread,int readlogid){
		
		return readService.addNonRemoteRead(m_id, newread, readlogid);
	}
	
	@RequestMapping(value="/readme/nonremote/addreadlog",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addreadlog(HttpServletRequest request,int n_id){
		UserForSession admin = WebUtil.getCurrUser(request);
		return readService.addreadlog(admin.getPid(),n_id);
	}
	
	@RequestMapping(value="/readme/nonremote/download")
	public ModelAndView download(int n_id, String n_name){
		Map map = new HashMap();
		map.put("n_id", n_id);
		map.put("n_name", n_name);
		map.put("list", readService.getNonRemoteMeters(n_id+""));
		return new ModelAndView("export_default", map);
	}
	
	@RequestMapping(value="/readme/nonremote/uploadPage")
	public String addCustomerPages(){
		return "/readme/uploadnonremote";
	}
	
	@RequestMapping(value="/readme/nonremote/upload")
	@ResponseBody
	public String uploadExcel(HttpServletRequest request, String name,MultipartFile file, int readlogid){
		JSONObject jo = new JSONObject();
		String realPath = request.getServletContext().getRealPath("/WEB-INF/yccb/readme/Excels");
		if(!file.isEmpty()){
			try {
				byte[] bytes = file.getBytes();
				String excelPath = "D:/Excels/"+name.substring(name.lastIndexOf("\\")+1)+Calendar.getInstance().getTimeInMillis();
				File f = new File(excelPath);//new File(realPath+"\\"+name.substring(name.lastIndexOf("\\")+1));
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
                stream.write(bytes);
                stream.close();
                
                //read excel;
                List<Readmeterlog> list = UploadNonRemoteRead.readExcel(excelPath, readlogid);
                
                UserForSession admin = WebUtil.getCurrUser(request);
                Map result = meterService.addMeterReads(list);
                jo.put("done", true);
                jo.put("success", result.get("success"));
                jo.put("reason", result.get("reason"));
                
			} catch (IOException e) {
				e.printStackTrace();
				jo.put("done", false);
        		jo.put("reason", e.getMessage());
			}
		}
		return jo.toJSONString();
	}
	
}
