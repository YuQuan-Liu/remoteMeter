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

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.common.encoder.Md5PwdEncoder;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.adminor.dto.DepartmentView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.adminor.service.DepartmentService;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.RoleService;
/**
 * 管理员controller
 * @author SGR
 *
 */
@Controller
public class AdministratorCtrl {
	@Autowired
	private AdministratorService adminstratorService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;
	
	public static final String adminorList = "/adminor/adminList";//管理员列表页面
	public static final String addAdminor = "/adminor/adminAdd";//管理员添加页面
	public static final String updateAdminor = "/adminor/adminUpdate";//管理员添加页面
	
	@RequestMapping(value="/admin/list",method = RequestMethod.GET)
	public String adminList(HttpServletRequest request, HttpServletResponse response, Model model){
		
		return adminorList;
	}
	/**
	 * easy-ui获取json数据,指定produces防止中文乱码
	 */
	@RequestMapping(value="/admin/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String adminListContent(HttpServletRequest request){
		
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		return JSON.toJSONString(adminstratorService.getList(wcid));
	}
	/**
	 * 添加管理员
	 * @return
	 */
	@RequestMapping(value="/admin/addPage")
	public String addPage(HttpServletRequest request, HttpServletResponse response, Model model){
		
		//注入权限    管辖片区   自来水公司
		int wcid = WebUtil.getCurrUser(request).getWaterComId();
		//权限
		List<RoleView> role_list = roleService.getList(wcid);
		model.addAttribute("role_list", role_list);
		//片区
		List<DepartmentView> dep_list = departmentService.getList(wcid);
		model.addAttribute("dep_list", dep_list);
		
		model.addAttribute("wcid", wcid);
		return addAdminor;
	}
	
	@RequestMapping(value="admin/admin/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(Admininfo admin,int roleid){
		admin.setLoginKey("96e79218965eb72c92a549dd5a330112");
		
		return adminstratorService.addAdmin(admin,roleid);
	}
	@RequestMapping(value="/admin/admin/checkloginname",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String checkLoginName(String name){
		
		return JSON.toJSONString(adminstratorService.checkLoginName(name));
	}
	
	
	@RequestMapping(value="admin/updatePage",method = RequestMethod.GET)
	public String updatePage(String pid,Model model){
		Admininfo ad = adminstratorService.getById(pid);
		model.addAttribute("adInfo", ad);
		return updateAdminor;
	}
	@RequestMapping(value="admin/update",method = RequestMethod.POST)
	public String update(Admininfo ad){
		adminstratorService.update(ad);
		return null;
	}
	
	@RequestMapping(value="/admin/admin/delete",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delete(int pid){
		
		return JSON.toJSONString(adminstratorService.removeById(pid)+"");
	}
}
