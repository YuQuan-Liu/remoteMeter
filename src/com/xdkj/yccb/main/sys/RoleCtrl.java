package com.xdkj.yccb.main.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.RoleService;

@Controller
public class RoleCtrl {
	public static final String roleList = "/sys/roleList";
	public static final String roleAdd = "/sys/roleAdd";
	@Autowired
	private RoleService roleService;
	@RequestMapping(value="/sys/role/list")
	public String roleList(){
		return roleList;
	}
	@RequestMapping(value="/sys/role/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listContent(RoleView rv,PageBase pb){
		
		int count = roleService.getCount(rv, pb);
		return JsonDataUtil.getJsonData(roleService.getList(rv, pb), count);
	}
	@RequestMapping(value="/sys/role/add")
	public String roleAdd(){
		return roleAdd;
	}
}
