package com.xdkj.yccb.main.readme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.entity.RemoteExport;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.readme.dao.RemoteExportDao;
import com.xdkj.yccb.main.readme.export.ExportRead;
import com.xdkj.yccb.main.readme.service.MeterService;
import com.xdkj.yccb.main.readme.service.ReadService;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class ReadCtrl {

	@Autowired
	private NeighborService neighborService;
	@Autowired
	private ReadService readService;
	@Autowired
	private AdministratorDAO adminDao;
	@Autowired
	private ReadMeter readMeter;
	@Autowired
	private MeterService meterService;
	@Autowired
	private RemoteExportDao remoteExportDao;
	
	@RequestMapping(value="/readme/read/remotelist")
	public String readMeter(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		//小区
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		//导出格式
		List<RemoteExport> export_list = remoteExportDao.getList(admin.getWaterComId());
		model.addAttribute("export_list", export_list);
		return "/readme/readmeter";
	}
	
	@RequestMapping(value="/readme/read/listread",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListReadMeter(String n_id){
		
		return JSON.toJSONString(readService.getRemoteMeters(n_id));
	}
	
	@RequestMapping(value="/readme/read/readneighbor",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ReadNeighbor(HttpServletRequest request,String n_id){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		return readMeter.readNeighbor(neighborService.getNbrById(Integer.parseInt(n_id)), adminDao.getById(admin.getPid()));
		
	}
	
	@RequestMapping(value="/readme/read/readmeter",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ReadMeter(HttpServletRequest request,String m_id){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		return readMeter.readMeter(meterService.getMeterbyPID(m_id), adminDao.getById(admin.getPid()));
		
	}
	
	@RequestMapping(value="/readme/read/readneighbors",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ReadNeighbors(HttpServletRequest request){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		return readMeter.readNeighbors(adminDao.getById(admin.getPid()));
		
	}
	
	@RequestMapping(value="/readme/read/changeread",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ChangeRead(int m_id,int m_read){
		
		return meterService.addMeterRead(m_id, m_read);
		
	}
	
	@RequestMapping(value="/readme/read/checkreading",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ChangeRead(HttpServletRequest request,int readlogid){
		UserForSession admin = WebUtil.getCurrUser(request);
		return readService.checkReading(readlogid, admin.getPid());
		
	}
	
	@RequestMapping(value="/readme/read/listwaste",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String showWaste(int readlogid){
		
		return readService.showWaste(readlogid);
	}
	
	@RequestMapping(value="/readme/read/addwaste")
	public void addWaste(int wid,String reason){
		
		readService.addWaste(wid,reason);
	}
	
//	@RequestMapping(value="/readme/read/export")
//	public void addWaste(){
//		
//		try {
//			Class c = Class.forName("com.xdkj.yccb.main.readme.ExportDemo");
//			ExportDemo demo = (ExportDemo) c.newInstance();
//			demo.sayhello();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping(value="/readme/read/downloadsingle")
	public ModelAndView downloadsingle(HttpServletRequest request,HttpServletResponse response,int n_id, String n_name,int export_id){
		if(export_id == 0){
			Map map = new HashMap();
			map.put("n_id", n_id);
			map.put("n_name", n_name);
			map.put("list", readService.getRemoteMeters(n_id+""));
			return new ModelAndView("export_default", map);
		}else{
			RemoteExport export = remoteExportDao.getByID(export_id);
			try {
				List<Integer> nid_list = new ArrayList<>();
				nid_list.add(n_id);
				
				Class c = Class.forName(export.getClazz());
				ExportRead exportRead = (ExportRead) c.newInstance();
				exportRead.download(request,response,nid_list,n_name);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
		
	}
	
	@RequestMapping(value="/readme/read/downloadall")
	public ModelAndView downloadall(HttpServletRequest request,HttpServletResponse response,String n_name,int export_id){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		List<Integer> nid_list = new ArrayList<>();
		for(int i = 0;i < neighbor_list.size();i++){
			nid_list.add(neighbor_list.get(i).getPid());
		}
		
		if(export_id == 0){
			Map map = new HashMap();
			map.put("n_name", "全部记录");
			
			map.put("list", readService.getAllRemoteMeters(nid_list));
			return new ModelAndView("export_default", map);
		}else{
			RemoteExport export = remoteExportDao.getByID(export_id);
			try {
				Class c = Class.forName(export.getClazz());
				ExportRead exportRead = (ExportRead) c.newInstance();
				exportRead.download(request,response,nid_list,"全部记录");
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return null;
		}
		
	}
}
