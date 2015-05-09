package com.xdkj.yccb.main.infoin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.infoin.service.NeighborService;

@Controller
public class NeighborCtrl {
	public static final String neighborList = "/infoin/neighborList";
	public static final String neighborAdd = "/infoin/neighborAdd";
	public static final String neighborUpdate = "/infoin/neighborUpdate";
	public static final String gprsAdd = "/infoin/gprsAdd";
	public static final String gprsUpdate = "/infoin/gprsUpdate";
	
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
		model.addAttribute("neighbor",neighborService.getNbrById(Integer.parseInt(pid)));
		return neighborUpdate;
	}
	
	@RequestMapping(value="/infoin/neighbor/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(Neighbor nv,@RequestParam("wcid") String wcid){
		Watercompany wc = new Watercompany();
		wc.setPid(Integer.parseInt(wcid));
		nv.setWatercompany(wc);
		return neighborService.updateNeighbor(nv);
	}
	
	@RequestMapping(value="/infoin/neighbor/delete",method=RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestParam("pids") String pids){
		return neighborService.deleteNbrById(pids);
	}
	
	@RequestMapping(value="/infoin/neighbor/gprsListContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String gprsListContent(@RequestParam("pid") String pid){
		return JSON.toJSONString(neighborService.getGprsByNbrId(Integer.parseInt(pid)));
	}
	
//	@RequestMapping(value="/infoin/neighbor/gprscombo",produces="application/json;charset=UTF-8")
//	@ResponseBody
//	public String gprsComboListContent(@RequestParam("n_id") String n_id){
//		return JSON.toJSONString(neighborService.getGprsComboByNbrId(Integer.parseInt(n_id)));
//	}
	
	@RequestMapping(value="/infoin/neighbor/deleteGprsById",method=RequestMethod.POST)
	@ResponseBody
	public String deleteGprsById(@RequestParam("pid") String pid){
		return neighborService.deleteGprsById(pid);
	}
	
	@RequestMapping(value="/infoin/neighbor/addPageGprs")
	public String addPageGprs(@RequestParam("neighborid") String neighborid,Model model){
		model.addAttribute("neighborid",neighborid);
		return gprsAdd;
	}
	
	@RequestMapping(value="/infoin/neighbor/addGprs",method=RequestMethod.POST)
	@ResponseBody
	public String addGprs(@RequestParam("neighborid") String neighborid,Gprs gs){
		Neighbor nbr = new Neighbor();
		nbr.setPid(Integer.parseInt(neighborid));
		gs.setNeighbor(nbr);
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
}
