package com.xdkj.yccb.main.adminor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.infoin.service.NeighborService;
import com.xdkj.yccb.main.logger.ActionLogService;
/**
 * 片区
 * @author SGR
 *
 */
@Controller
public class DepartmentCtrl {
	
	public static final String depList = "/adminor/depList";
	public static final String depAdd = "adminor/depAdd";
	public static final String depUpdate = "adminor/depUpdate";
	public static final String depDetail="adminor/depDetail";
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private NeighborService neighborService;
	@Autowired
	private ActionLogService actionLogService;
	
	@RequestMapping(value="/admin/dep/list")
	public String depList(){
		return depList;
	}
	@RequestMapping(value="/admin/dep/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String depListContent(HttpServletRequest request){
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(departmentService.getList(wcid));
	}
	@RequestMapping(value="/admin/dep/addPage",method=RequestMethod.GET)
	public String addPage(HttpServletRequest request, HttpServletResponse response, Model model){
		return depAdd;
	}
	@RequestMapping(value="/admin/dep/updatePage",method=RequestMethod.GET)
	public String updatePage(@RequestParam("depId") String depId,Model model){
		model.addAttribute("dep", departmentService.getById(depId));
		return depUpdate;
	}
	/**
	 * 获取用户所在自来水公司下的小区
	 * <p>Description: depId非空表示修改片区回填小区数据</p> 
	 * @param depId 片区
	 * @param request
	 * @return
	 * @author SongW
	 * @date 2015-5-6
	 * @version 1.0
	 */
	@RequestMapping(value="/admin/dep/listallNbr",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listallNbr(HttpServletRequest request){
		List<Neighbor> list = neighborService.getallNbrBywcid(WebUtil.getCurrUser(request).getWaterComId());
		JSONArray ja = new JSONArray();
		Neighbor n = null;
		for(int i = 0;i < list.size();i++){
			n = list.get(i);
			JSONObject jo = new JSONObject();
			jo.put("nid", n.getPid());
			jo.put("name", n.getNeighborName());
			jo.put("addr", n.getNeighborAddr());
			ja.add(jo);
		}
		return ja.toJSONString();
	}
	
	@RequestMapping(value="/admin/dep/add",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String add(String name,String remark,int[] nbr_ids,HttpServletRequest request){
		
		//自来水公司id
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 5, Arrays.toString(nbr_ids));
		
		return JSON.toJSONString(departmentService.add(wcid,name,remark,nbr_ids));
	}
	
	@RequestMapping(value="/admin/dep/checkdepname",method=RequestMethod.POST)
	@ResponseBody
	public String checkDepName(String name,HttpServletRequest request){
		
		//自来水公司id
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(departmentService.checkdepname(wcid,name));
	}
	
	@RequestMapping(value="/admin/dep/detail")
	public String depDetail(@RequestParam("depId")String depId, Model model,HttpServletRequest request){
		if(depId.equals("0")){
			Department dep = new Department();
			dep.setPid(0);
			dep.setDepartmentName("全部小区");
			dep.setRemark("");
			model.addAttribute("dep", dep);
		}else{
			model.addAttribute("dep", departmentService.getById(depId));
		}
		
		
		//自来水下的全部小区
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		model.addAttribute("neighbor_list", neighborService.getallNbrBywcid(wcid));
		
		return depDetail;
	}
	
	@RequestMapping(value="/admin/dep/nbrTabContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String depNbrListTabContent(HttpServletRequest request,int depId){
		//片区对应的全部小区
//		String depId = request.getParameter("depId");
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(neighborService.getList(depId, wcid));
	}
	
	@RequestMapping(value="/admin/dep/deletedep",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deletedep(HttpServletRequest request,int pid){
		
		//log
		actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 6, "depid:"+pid);
		
		return JSON.toJSONString(departmentService.deletedep(pid));
	}
	
	@RequestMapping(value="/admin/dep/deletedetail",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deletedetail(int dep_id,int n_id){
		
		return JSON.toJSONString(departmentService.deletedetail(dep_id,n_id));
	}
	
	@RequestMapping(value="/admin/dep/adddetail",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String adddetail(int dep_id,int n_id){
		
		return JSON.toJSONString(departmentService.adddetail(dep_id,n_id));
	}
}
