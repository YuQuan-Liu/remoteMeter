package com.xdkj.yccb.main.adminor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Watercompany;
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
	@Autowired
	private DepartmentService departmentService;
	@RequestMapping(value="/admin/dep/list")
	public String depList(){
		return depList;
	}
	@RequestMapping(value="/admin/dep/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String depListContent(DepartmentView dv,PageBase pageInfo){
		List<DepartmentView> list = departmentService.getList(dv, pageInfo);
		int totalCount = departmentService.getTotalCount(dv);
		return JsonDataUtil.getJsonData(list, totalCount);
	}
	@RequestMapping(value="/admin/dep/addPage",method=RequestMethod.GET)
	public String addPage(HttpServletRequest request, HttpServletResponse response, Model model){
		return depAdd;
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
	@RequestMapping(value="/admin/dep/nbrlistContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String depNbrListContent(HttpServletRequest request){
		String depId = request.getParameter("depId");
		return departmentService.getNbrByCurrUser(WebUtil.getCurrUser(request), depId);
	}
	
	@RequestMapping(value="/admin/dep/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Department dep,HttpServletRequest request){
		//小区
		String neighbors = request.getParameter("neighbors");
		//自来水公司id
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		dep.setWatercompany(new Watercompany(wcid));
		return departmentService.add(dep,neighbors);
	}

}
