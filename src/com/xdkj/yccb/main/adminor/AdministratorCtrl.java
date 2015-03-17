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
import com.xdkj.yccb.common.encoder.Md5PwdEncoder;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.Admininfo;
/**
 * 管理员controller
 * @author SGR
 *
 */
@Controller
public class AdministratorCtrl {
	@Autowired
	private AdministratorService adminstratorService;
	
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
	public String adminListContent(Admininfo adinfo,PageBase pageInfo){
		List<AdminInfoView> list = adminstratorService.getList(adinfo, pageInfo);
		int count = adminstratorService.getTotalCount(adinfo);
		return JsonDataUtil.getJsonData(list, count);
	}
	/**
	 * 添加管理员
	 * @return
	 */
	@RequestMapping(value="/admin/addPage",method = RequestMethod.GET)
	public String addPage(){
		return addAdminor;
	}
	@RequestMapping(value="admin/add",method = RequestMethod.POST)
	@ResponseBody
	public String add(Admininfo adinfo){
		Md5PwdEncoder md = new Md5PwdEncoder();
		adinfo.setLoginKey(md.encodePassword("111111"));
		return adminstratorService.addAdmin(adinfo);
	}
	@RequestMapping(value="admin/check",method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginName(String loginName){
		Admininfo ad = adminstratorService.getByLoginName(loginName, null);
		if(ad==null){
			return "true";
		}
		return "false";
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
}
