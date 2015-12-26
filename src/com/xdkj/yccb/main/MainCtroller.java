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

import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.security.UserForSession;
/**
 * 首页controller
 * @author SGR
 *
 */
@Controller
public class MainCtroller {
	
	@Autowired
	private AdministratorService administratorService;
	
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String homePage(HttpServletRequest request,HttpServletResponse response,Model model){
		
		UserForSession admin = WebUtil.getCurrUser(request);
		model.addAttribute("userInfo", admin);
		
		return "/main";
	}
	@RequestMapping(value="/c/customer",method = RequestMethod.GET)
	public String customer(HttpServletRequest request,HttpServletResponse response,Model model){
		
		Customer c = (Customer) request.getSession(false).getAttribute("customer");
		model.addAttribute("c", c);
		
		return "/customerinfo";
	}
	@RequestMapping(value="/usermenu",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMenu(HttpServletRequest request,HttpServletResponse response,Model model){
		 //从后台代码获取国际化信息
	    RequestContext requestContext = new RequestContext(request);
	    String menus = requestContext.getMessage("main.welcome");
		return menus;
	}
	
	@RequestMapping(value="/intro",method = RequestMethod.GET)
	public String introl(){
		
		return "/intro";
	}
}
