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
@RequestMapping(value="/admin")
public class AdministratorCtrl {
	@Autowired
	private AdministratorService adminstratorService;
	
	public static final String adminorList = "/yccb/adminor/adminList";//管理员列表页面
	public static final String addAdminor = "/yccb/adminor/addAdmin";//管理员添加页面
	public static final String updateAdminor = "/yccb/adminor/updateAdmin";//管理员添加页面
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String adminList(HttpServletRequest request, HttpServletResponse response, Model model){
		
		return adminorList;
	}
	/**
	 * easy-ui获取json数据,指定produces防止中文乱码
	 */
	@RequestMapping(value="/adminlistContent",produces="application/json;charset=UTF-8")
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
	@RequestMapping(value="/add",method = RequestMethod.GET)
	public String addAdminor(){
		return addAdminor;
	}
	@RequestMapping(value="addAdmin",method = RequestMethod.POST)
	@ResponseBody
	public String addAdmin(Admininfo adinfo){
		Md5PwdEncoder md = new Md5PwdEncoder();
		adinfo.setLoginKey(md.encodePassword("111111"));
		return adminstratorService.addAdmin(adinfo);
	}
	@RequestMapping(value="check",method = RequestMethod.POST)
	@ResponseBody
	public String checkLoginName(String loginName){
		Admininfo ad = adminstratorService.getByLoginName(loginName, null);
		if(ad==null){
			return "true";
		}
		return "false";
	}
	@RequestMapping(value="/update",method = RequestMethod.GET)
	public String updateAdminor(String pid,Model model){
		Admininfo ad = adminstratorService.getById(pid);
		model.addAttribute("adInfo", ad);
		return updateAdminor;
		
	}
}
