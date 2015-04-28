package com.xdkj.yccb.main.infoin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;

@Controller
public class NeighborCtrl {
	public static final String neighborList = "/infoin/neighborList";
	public static final String neighborAdd = "/infoin/neighborAdd";
	public static final String neighborUpdate = "/infoin/neighborUpdate";
	
	@Autowired
	private NeighborService neighborService;
	
	@RequestMapping(value="/infoin/neighbor/list")
	public String neighborList(){
		return neighborList;
	}
	
	@RequestMapping(value="/infoin/neighbor/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listContent(NeighborView nv,Neighbor nb,PageBase pb){
		int count = neighborService.getCount(nb, pb);
		return JsonDataUtil.getJsonData(neighborService.getList(nv, pb), count);
	}
	
	@RequestMapping(value="/infoin/neighbor/addPage")
	public String neighborAdd(){
		return neighborAdd;
	}
	
	@RequestMapping(value="/infoin/neighbor/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Neighbor nv){
		return neighborService.addNeighbor(nv);
	}
	@RequestMapping(value="/infoin/neighbor/updatePage")
	public String roleUpdate(HttpServletRequest request,@RequestParam("pid") String pid,Model model){
		model.addAttribute("role",neighborService.getNbrById(Integer.parseInt(pid)));
		return neighborUpdate;
	}
	
	@RequestMapping(value="/infoin/neighbor/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(Neighbor nv){
		return neighborService.updateNeighbor(nv);
	}
}
