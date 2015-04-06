package com.xdkj.yccb.main.adminor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Department;

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
	@RequestMapping(value="admin/dep/addPage",method=RequestMethod.GET)
	public String addPage(){
		return depAdd;
		
	}
	@RequestMapping(value="admin/dep/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Department dep){
		return departmentService.add(dep);
		
	}

}
