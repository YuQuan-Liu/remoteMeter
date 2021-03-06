package com.xdkj.yccb.main.infoin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.logger.ActionLog;
import com.xdkj.yccb.main.readme.quartz.QuartzManager;
import com.xdkj.yccb.security.UserForSession;

@Controller
public class NeighborCtrl {
	public static final String neighborList = "/infoin/neighborList";
	public static final String neighborAdd = "/infoin/neighborAdd";
	public static final String neighborUpdate = "/infoin/neighborUpdate";
	public static final String gprsAdd = "/infoin/gprsAdd";
	public static final String gprsUpdate = "/infoin/gprsUpdate";
	
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private AdministratorDAO administratorDAO;
	private static final Logger logger = LoggerFactory.getLogger(NeighborCtrl.class);
	
	@RequestMapping(value="/infoin/neighbor/list")
	public String neighborList(){
		return neighborList;
	}
	
	@RequestMapping(value="/infoin/neighbor/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listContent(HttpServletRequest request){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		return JSON.toJSONString(neighborService.getList(admin.getDepart_id(), admin.getWaterComId()));
	}
	
	@RequestMapping(value="/infoin/neighbor/addPage")
	public String neighborAdd(){
		return neighborAdd;
	}
	
	@RequestMapping(value="/infoin/neighbor/searchn_name",method=RequestMethod.POST)
	@ResponseBody
	public String checkn_name(HttpServletRequest request,String n_name){
		UserForSession admin = WebUtil.getCurrUser(request);
		return JSON.toJSONString(neighborService.checkn_name(admin.getWaterComId(),n_name));
	}
	
	@RequestMapping(value="/infoin/neighbor/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(HttpServletRequest request,Neighbor nv){
		UserForSession admin = WebUtil.getCurrUser(request);
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 10, "neighborname:"+nv.getNeighborName()).toString());
		
		return neighborService.addNeighbor(admin.getPid(),admin.getWaterComId(),admin.getDepart_id(),nv);
	}
	
	@RequestMapping(value="/infoin/neighbor/updatePage")
	public String roleUpdate(HttpServletRequest request,@RequestParam("pid") String pid,Model model){
		model.addAttribute("neighbor",neighborService.getNbrById(Integer.parseInt(pid)));
		return neighborUpdate;
	}
	
	@RequestMapping(value="/infoin/neighbor/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(HttpServletRequest request,Neighbor nv,@RequestParam("wcid") String wcid){
		UserForSession admin = WebUtil.getCurrUser(request);
		Admininfo a = administratorDAO.getById(admin.getPid());
		Watercompany wc = new Watercompany();
		wc.setPid(Integer.parseInt(wcid));
		nv.setWatercompany(wc);
		
//		String r = neighborService.updateNeighbor(a,nv);
//		
//		if(nv.getTimerSwitch() == 0){
//			QuartzManager.removeNeighbor(neighborService.getNbrById(nv.getPid()));
//		}else{
//			QuartzManager.modifyNeighborJobTime(neighborService.getNbrById(nv.getPid()), a);
//		}
		
		return neighborService.updateNeighbor(a,nv);
	}
	
	@RequestMapping(value="/infoin/neighbor/delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(HttpServletRequest request,int pid){
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 11, "neighborid:"+pid).toString());
		
		return neighborService.deleteNbrById(pid);
	}
	
	@RequestMapping(value="/infoin/neighbor/gprsListContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String gprsListContent(@RequestParam("pid") String pid){
		return JSON.toJSONString(neighborService.getGprsByNbrId(Integer.parseInt(pid)));
	}
	
	@RequestMapping(value="/infoin/neighbor/deleteGprsById",method=RequestMethod.POST)
	@ResponseBody
	public String deleteGprsById(HttpServletRequest request,@RequestParam("pid") int pid){
		
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 13, "gprsid:"+pid).toString());
				
		return neighborService.deleteGprsById(pid);
	}
	
	@RequestMapping(value="/infoin/neighbor/addPageGprs")
	public String addPageGprs(@RequestParam("neighborid") String neighborid,Model model){
		model.addAttribute("neighborid",neighborid);
		return gprsAdd;
	}
	
	@RequestMapping(value="/infoin/neighbor/searchgprs_addr",method=RequestMethod.POST)
	@ResponseBody
	public String checkGPRSAddr(String gprsaddr){
		return JSON.toJSONString(neighborService.checkGPRSAddr(gprsaddr));
	}
	
	@RequestMapping(value="/infoin/neighbor/addGprs",method=RequestMethod.POST)
	@ResponseBody
	public String addGprs(HttpServletRequest request,@RequestParam("neighborid") String neighborid,Gprs gs){
		Neighbor nbr = new Neighbor();
		nbr.setPid(Integer.parseInt(neighborid));
		gs.setNeighbor(nbr);
		
		//log
		logger.info(new ActionLog(WebUtil.getCurrUser(request).getPid(), 12, "gprsaddr:"+gs.getGprsaddr()).toString());
		return neighborService.addGprs(gs);
	}
	
	@RequestMapping(value="/infoin/neighbor/updatePageGprs")
	public String updatePageGprs(HttpServletRequest request,@RequestParam("pid") String pid,Model model){
		model.addAttribute("gprs",neighborService.getGprsById(Integer.parseInt(pid)));
		return gprsUpdate;
	}
	
	@RequestMapping(value="/infoin/neighbor/updateGprs",method=RequestMethod.POST)
	@ResponseBody
	public String updateGprs(@RequestParam("neighborid") String neighborid,Gprs gs){
		Neighbor nbr = new Neighbor();
		nbr.setPid(Integer.parseInt(neighborid));
		gs.setNeighbor(nbr);
		return neighborService.updateGprs(gs);
	}
	
	@RequestMapping(value="/infoin/neighbor/configPageGprs")
	public String configPageGprs(HttpServletRequest request,@RequestParam("pid") String pid,Model model){
		model.addAttribute("gprs",neighborService.getGprsById(Integer.parseInt(pid)));
		model.addAttribute("collector_list",neighborService.getCollectorsByGid(Integer.parseInt(pid)));
		return "/infoin/gprsConfig";
	}
	
	@RequestMapping(value="/infoin/neighbor/listgprsmeters",method=RequestMethod.POST)
	@ResponseBody
	public String listgprsmeters(int pid,String caddr){
		
		return JSON.toJSONString(neighborService.listgprsmeters(pid,caddr));
	}
	
	@RequestMapping(value="/infoin/neighbor/querygprsslave",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String querygprsslave(int pid){
		Gprs g = neighborService.getGprsById(pid);
		return ConfigGPRS.querygprsslave(g);
	}
	
	@RequestMapping(value="/infoin/neighbor/configgprsslave",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String configgprsslave(int pid,int gprsslave){
		Gprs g = neighborService.getGprsById(pid);
		return ConfigGPRS.configgprsslave(g,gprsslave);
	}
	
	@RequestMapping(value="/infoin/neighbor/querycjqs",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String querycjqs(int pid){
		Gprs g = neighborService.getGprsById(pid);
		
		String result = "";
		switch(g.getGprsprotocol()){
		case 2:
			result = ConfigGPRS.querycjqs(g);
			break;
		case 5:
			result = ConfigGPRS.querycjqsV2(g);
			break;
		}
		return result;
	}
	
	@RequestMapping(value="/infoin/neighbor/addcjq",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addcjq(int pid,String caddr){
		Gprs g = neighborService.getGprsById(pid);
		
		String result = "";
		switch(g.getGprsprotocol()){
		case 2:
			result = ConfigGPRS.addcjq(g,caddr);
			break;
		case 5:
			result = ConfigGPRS.addcjqV2(g,caddr);
			break;
		}
		return result;
	}
	
	@RequestMapping(value="/infoin/neighbor/deleteAllmeters",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteAllmeters(int pid){
		Gprs g = neighborService.getGprsById(pid);
		return ConfigGPRS.deleteAllmeters(g);
	}
	
	@RequestMapping(value="/infoin/neighbor/readJZQdata",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String readJZQdata(int pid,String caddr){
		Gprs g = neighborService.getGprsById(pid);
		
		String result = "";
		switch(g.getGprsprotocol()){
		case 2:
			result = ConfigGPRS.readJZQdata(g,caddr);
			break;
		case 5:
			result = ConfigGPRS.readJZQdataV2(g,caddr);
			break;
		}
		return result;
	}

	@RequestMapping(value="/infoin/neighbor/jzqaddmeters",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String jzqaddmeters(int pid,final String caddr,final String[] maddrs){
		final Gprs g = neighborService.getGprsById(pid);
		ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				JSONObject result = null;
				switch(g.getGprsprotocol()){
				case 2:
					result = ConfigGPRS.jzqaddmeters(g,caddr,maddrs);
					break;
				case 5:
					result = ConfigGPRS.jzqaddmetersV2(g,caddr,maddrs);
					break;
				}
				logger.info("add meters : "+ result);
				ConfigGPRS.configGPRSResult.put(g.getGprsaddr(), result);
				ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 100);
				
				try {
					Thread.sleep(7000);
					if(ConfigGPRS.configGPRSStatus.get(g.getGprsaddr()) == 100){
						ConfigGPRS.configGPRSResult.put(g.getGprsaddr(), new JSONObject());
						ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 0);
					}
				} catch (InterruptedException e) {
				}
				
			}
		}).start();
		
		JSONObject jo = new JSONObject();
		jo.put("done", true);
		return jo.toJSONString();
	}
	
	@RequestMapping(value="/infoin/neighbor/jzqdeletemeters",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String jzqdeletemeters(int pid,final String caddr,final String[] maddrs){
		final Gprs g = neighborService.getGprsById(pid);
		ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 0);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JSONObject result = null;
				switch(g.getGprsprotocol()){
				case 2:
					result = ConfigGPRS.jzqdeletemeters(g,caddr,maddrs);
					break;
				case 5:
					result = ConfigGPRS.jzqdeletemetersV2(g,caddr,maddrs);
					break;
				}
				logger.info("delete meters : "+ result);
				ConfigGPRS.configGPRSResult.put(g.getGprsaddr(), result);
				ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 100);
				
				try {
					Thread.sleep(7000);
					if(ConfigGPRS.configGPRSStatus.get(g.getGprsaddr()) == 100){
						ConfigGPRS.configGPRSResult.put(g.getGprsaddr(), new JSONObject());
						ConfigGPRS.configGPRSStatus.put(g.getGprsaddr(), 0);
					}
				} catch (InterruptedException e) {
				}
			}
		}).start();
		
		JSONObject jo = new JSONObject();
		jo.put("done", true);
		return jo.toJSONString();
	}
	
	
	@RequestMapping(value="/infoin/neighbor/checkgprsconfig",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String checkConfiging(HttpServletRequest request,String gprsaddr){
		JSONObject jo = new JSONObject();
		
		int status = ConfigGPRS.configGPRSStatus.get(gprsaddr);
		logger.info("check configing gprsaddr: "+gprsaddr+"; status: "+status);
		if(status == 100){
			JSONObject result = ConfigGPRS.configGPRSResult.get(gprsaddr);
			logger.info("check configing gprsaddr: "+gprsaddr+"; result: "+result);
			jo.put("done", true);
			jo.put("result", result.getJSONArray("result"));
			jo.put("good", result.getInteger("good"));
			jo.put("error", result.getInteger("error"));
			ConfigGPRS.configGPRSResult.put(gprsaddr, new JSONObject());
			ConfigGPRS.configGPRSStatus.put(gprsaddr, 0);
		}else{
			jo.put("done", false);
			jo.put("result", "");
		}
		return jo.toJSONString();
		
	}
	
}
