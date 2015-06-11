package com.xdkj.yccb.main.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.RoleService;

@Controller
public class RoleCtrl {
	public static final String roleList = "/sys/roleList";
	public static final String roleAdd = "/sys/roleAdd";
	public static final String roleUpdate = "/sys/roleUpdate";
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value="/sys/role/list")
	public String roleList(){
		return roleList;
	}
	@RequestMapping(value="/sys/role/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listContent(HttpServletRequest request){
		
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(roleService.getList(wcid));
	}
	
	@RequestMapping(value="/sys/role/addPage")
	public String roleAdd(){
		return roleAdd;
	}
	
	/**
	 * 获取权限菜单树
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value="/sys/role/checkname",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String checkname(HttpServletRequest request,String name){
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(roleService.checkname(wcid,name));
	}
	
	/**
	 * 获取权限菜单树
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value="/sys/role/tree",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAuthTree(HttpServletRequest request){
		return roleService.getAuthTreeJson(request);
	}
	
	/**
	 * 获取权限菜单树
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value="/sys/role/tree_detail",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAuthTreeDetail(HttpServletRequest request,int pid){
		return roleService.getAuthTreeJson(request,pid);
	}
	
	/**
	 * 添加角色
	 * @param rv 角色dto
	 * @param chAuth 子集权限
	 * @param pAuth 父级权限
	 * @return
	 */
	@RequestMapping(value="/sys/role/add",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String add(HttpServletRequest request,RoleView rv,String childauth){
		
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(roleService.addRole(rv, childauth,wcid));
	}
	@RequestMapping(value="/sys/role/updatePage")
	public String roleUpdate(HttpServletRequest request,@RequestParam("pid") String pid,Model model){
		model.addAttribute("role",roleService.getRoleInfo(pid));
		return roleUpdate;
	}
	
	@RequestMapping(value="/sys/role/update",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String update(String childauth,int pid){
		return JSON.toJSONString(roleService.updateRole(childauth, pid));
	}
	
}
