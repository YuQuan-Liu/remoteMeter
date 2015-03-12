package com.xdkj.yccb.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WarnCtrl {
	
	@RequestMapping(value="/unauth")
	public String unauth(){
		
		return "unauth";
	}

}
