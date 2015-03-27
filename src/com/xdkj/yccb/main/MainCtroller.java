package com.xdkj.yccb.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.xdkj.yccb.main.sys.service.AuthorityService;
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
	private AuthorityService authorityService;
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String homePage(HttpServletRequest request,HttpServletResponse response,Model model){
		
		UserForSession ufs =(UserForSession) request.getSession().getAttribute("curuser") ;
		if(null!=ufs){
			model.addAttribute("userInfo", ufs);
			
		}
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
