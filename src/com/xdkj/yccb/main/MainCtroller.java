package com.xdkj.yccb.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.security.UserForSession;
/**
 * 首页controller
 * @author SGR
 *
 */
@Controller
public class MainCtroller {
	public static final String homePage = "/main";
	
	@Autowired
	private AdministratorService administratorService;
	
	
	@RequestMapping(value="/resource/index",method = RequestMethod.GET)
	public String homePage(HttpServletRequest request,HttpServletResponse response,Model model){
		
		Admininfo adInfo = administratorService.getByLoginName("admin", "96e79218965eb72c92a549dd5a330112");
		UserForSession ufs = new UserForSession();
		ufs.setPid(adInfo.getPid());
		ufs.setLoginName(adInfo.getLoginName());
		ufs.setAdminName(adInfo.getAdminName());
		ufs.setAdminEmail(adInfo.getAdminEmail());
		ufs.setAdminMobile(adInfo.getAdminMobile());
		ufs.setWaterComId(adInfo.getWatercompany().getPid());
		
		//管理员没有片区   将0存到Session中
		if(null == adInfo.getDepartment()){
			ufs.setDepart_id(0);
		}else{
			ufs.setDepart_id(adInfo.getDepartment().getPid());
		}
		
		List<AdminRole> adminRole = new ArrayList<AdminRole>(adInfo.getAdminRoles());
		Set<RoleAuthority> ras = adminRole.get(0).getRoles().getRoleAuthorities();
		Map<String, String> menus = new HashMap<String, String>();
		for (RoleAuthority roleAuthority : ras) {
			menus.put(roleAuthority.getAuthority().getAuthorityCode(), "t");
		}
		ufs.setMenus(menus);
		model.addAttribute("userInfo", ufs);
		
		request.getSession().setAttribute("curuser", ufs);
		
//		UserForSession ufs =(UserForSession) request.getSession().getAttribute("curuser") ;
//		if(null!=ufs){
//			model.addAttribute("userInfo", ufs);
//			
//		}
		
		
		
		
		//String jsons = authorityService.getAuthTreeJson(request);
		//model.addAttribute("menu", jsons);
		return homePage;
	}
	@RequestMapping(value="/usermenu",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMenu(HttpServletRequest request,HttpServletResponse response,Model model){
		 //从后台代码获取国际化信息
	    RequestContext requestContext = new RequestContext(request);
	    String menus = requestContext.getMessage("main.welcome");
		return menus;
	}
}
