package com.xdkj.yccb.main.readme;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;
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
	
	@RequestMapping(value="/readme/read/remotelist")
	public String readMeter(HttpServletRequest request,Model model){
		UserForSession admin = WebUtil.getCurrUser(request);
		System.out.println(admin.getPid());
		List<NeighborView> neighbor_list = neighborService.getList(admin.getDepart_id(), admin.getWaterComId());
		model.addAttribute("neighbor_list", neighbor_list);
		
		return "/readme/readmeter";
	}
	
	@RequestMapping(value="/readme/read/listread",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ListReadMeter(String n_id){
		
		return JSON.toJSONString(readService.getMeters(n_id));
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
}
