package com.xdkj.yccb.main.adminor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dto.WaterCompanyView;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.entity.Watercompany;
/**
 * 自来水公司controller
 * @author SGR
 *
 */
@Controller
public class WaterCompanyCtrl {
	
	public static final String waterComList = "/adminor/watComList";
	public static final String waterAdd = "adminor/addWatCom";
	@Autowired
	private WaterCompanyService waterCompanyService;
	@RequestMapping(value="/admin/watcom/list")
	public String waterCompanyList(){
		return waterComList;
	}
	@RequestMapping(value="/admin/watcom/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String waterCompanyListContent(Watercompany watcom,PageBase pageInfo){
		List<WaterCompanyView> list = waterCompanyService.getList(watcom, pageInfo);
		int totalCount = waterCompanyService.getTotalCount(watcom);
		return JsonDataUtil.getJsonData(list, totalCount);
	}
	@RequestMapping(value="admin/watcom/addPage",method=RequestMethod.GET)
	public String addPage(){
		return waterAdd;
		
	}
	@RequestMapping(value="admin/watcom/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(Watercompany watcom){
		return waterCompanyService.addWatcom(watcom);
		
	}
}
