package com.xdkj.yccb.main.readme;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.xdkj.yccb.main.adminor.dao.SysParaDao;
import com.xdkj.yccb.main.entity.NonRemoteExport;
import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.entity.RemoteExport;
import com.xdkj.yccb.main.infoin.NeighborCtrl;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.logger.ActionLog;
import com.xdkj.yccb.main.readme.dao.NonRemoteExportDao;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.export.ExportRead;
import com.xdkj.yccb.main.readme.import_.NonRemoteUpload;
import com.xdkj.yccb.main.readme.import_.impl.NonRemoteReadUpload;
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
	@Autowired
	private NonRemoteExportDao nonremoteExportDao;
	@Autowired
	private ReadDao readDao;
	@Autowired
	private SysParaDao sysParaDao;
	private static final Logger logger = LoggerFactory.getLogger(NeighborCtrl.class);
	
	@RequestMapping(value="/readme/read/unremotelist")
	public String nonRemoteList(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		//导出格式
		List<NonRemoteExport> export_list = nonremoteExportDao.getList(admin.getWaterComId());
		model.addAttribute("export_list", export_list);
		
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
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response,int n_id, String n_name,int export_id){
		if(export_id == 0){
			Map map = new HashMap();
			map.put("n_id", n_id);
			map.put("n_name", n_name);
			map.put("list", readService.getNonRemoteMeters(n_id+""));
			return new ModelAndView("export_nonremote_default", map);
		}else{
			NonRemoteExport nonexport = nonremoteExportDao.getByID(export_id);
			try {
				List<Integer> nid_list = new ArrayList<>();
				nid_list.add(n_id);
				
				Class c = Class.forName(nonexport.getClazzdown1());
				ExportRead exportRead = (ExportRead) c.newInstance();
				
				exportRead.download(request,response,nid_list,n_name,readDao);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
		
	}
	
	@RequestMapping(value="/readme/nonremote/downloadthis")
	public ModelAndView downloadthis(HttpServletRequest request,HttpServletResponse response,int n_id, String n_name,int export_id){
		
		if(export_id == 0){
			Map map = new HashMap();
			map.put("n_id", n_id);
			map.put("n_name", n_name);
			map.put("list", readService.getNonRemoteMeters(n_id+""));
			return new ModelAndView("export_nonremote_default", map);
		}else{
			NonRemoteExport nonexport = nonremoteExportDao.getByID(export_id);
			try {
				List<Integer> nid_list = new ArrayList<>();
				nid_list.add(n_id);
				
				Class c = Class.forName(nonexport.getClazzdown2());
				ExportRead exportRead = (ExportRead) c.newInstance();
				
				exportRead.download(request,response,nid_list,n_name,readDao);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
	}
	
	@RequestMapping(value="/readme/nonremote/uploadPage")
	public String uploadPage(){
		return "/readme/uploadnonremote";
	}
	
	@RequestMapping(value="/readme/nonremote/upload")
	@ResponseBody
	public String upload(HttpServletRequest request, String name,MultipartFile file, int readlogid,int export_id,int n_id){
		JSONObject jo = new JSONObject();
//		String realPath = request.getServletContext().getRealPath("/WEB-INF/yccb/readme/Excels");
		String excelPath = sysParaDao.getValue("server_UpPath")+Calendar.getInstance().getTimeInMillis()+name.substring(name.lastIndexOf("\\")+1);
		if(!file.isEmpty()){
			try {
				byte[] bytes = file.getBytes();
				//log
				logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 21, "excelPath:"+excelPath).toString());
				
				File f = new File(excelPath);//new File(realPath+"\\"+name.substring(name.lastIndexOf("\\")+1));
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
	            stream.write(bytes);
	            stream.close();
	            
	            List<Readmeterlog> list = null;
	            if(export_id == 0){
					//read excel;
	            	Class c = Class.forName("com.xdkj.yccb.main.readme.import_.impl.NonRemoteReadUpload");
	            	NonRemoteUpload upload = (NonRemoteUpload) c.newInstance();
	                list = upload.read(excelPath, readlogid,n_id,meterService);
				}else{
					//read dbf;
					NonRemoteExport nonexport = nonremoteExportDao.getByID(export_id);
					
					Class c = Class.forName(nonexport.getClazzup());
					
					NonRemoteUpload upload = (NonRemoteUpload) c.newInstance();
	                list = upload.read(excelPath, readlogid,n_id,meterService);
				}
	            Map result = meterService.addMeterReads(list);
                jo.put("done", true);
                jo.put("success", result.get("success"));
                jo.put("reason", result.get("reason"));
			} catch (Exception e) {
				e.printStackTrace();
				jo.put("done", false);
        		jo.put("reason", e.getMessage());
			}
		}
		return jo.toJSONString();
	}
	
}
