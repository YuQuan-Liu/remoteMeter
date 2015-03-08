package com.xdkj.yccb.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 首页controller
 * @author SGR
 *
 */
@Controller
public class MainCtroller {
	public static final String homePage = "index";
	
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String homePage(){
		
		return homePage;
	}

}
