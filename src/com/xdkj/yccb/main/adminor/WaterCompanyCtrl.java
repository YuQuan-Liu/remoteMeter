package com.xdkj.yccb.main.adminor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 自来水公司controller
 * @author SGR
 *
 */
@Controller
public class WaterCompanyCtrl {
	public static final String waterComList = "/adminor/watComList";
	@RequestMapping(value="/admin/watcom/list")
	public String waterCompanyList(){
		return waterComList;
	}
	@RequestMapping(value="/admin/watcom/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String waterCompanyListContent(){
		
		return null;
	}
	

}
