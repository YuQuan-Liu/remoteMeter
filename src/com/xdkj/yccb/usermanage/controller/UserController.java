package com.xdkj.yccb.usermanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xdkj.yccb.usermanage.entity.Users;
import com.xdkj.yccb.usermanage.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/index2")
	public String getUsers( HttpServletRequest request, HttpServletResponse response,String uname, Model model){
		
		//Users u = userService.findByUname(uname);  
		model.addAttribute("test","你好");
		
		 Subject currentUser = SecurityUtils.getSubject();
		 
		return "index";
		
	}

}
