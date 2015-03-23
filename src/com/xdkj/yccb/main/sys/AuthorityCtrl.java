package com.xdkj.yccb.main.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xdkj.yccb.common.JsonDataUtil;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.sys.dto.AuthorityView;
import com.xdkj.yccb.main.sys.service.AuthorityService;

@Controller
public class AuthorityCtrl {
	@Autowired
	private AuthorityService authorityService;
	public static final String authList = "/sys/authList";
	public static final String authAdd = "/sys/authAdd";
	@RequestMapping(value = "/sys/auth/list",method = RequestMethod.GET)
	public String authList(){
		return authList;
	}
	@RequestMapping(value = "/sys/auth/listContent",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String listContent(Authority au,PageBase pageInfo,HttpServletRequest request){
		int total = authorityService.getTotalCount(au);
		List<AuthorityView> list = authorityService.getList(au,pageInfo);
		return JsonDataUtil.getJsonData(list, total);
	}
	
	@RequestMapping(value="/sys/auth/addPage",method = RequestMethod.GET)
	public String addPage(Model model){
		List<AuthorityView > list = authorityService.getParentAuth();
		model.addAttribute("auList", list);
		return authAdd;
	}
	
	@RequestMapping(value = "/sys/auth/add",method = RequestMethod.POST)
	@ResponseBody
	public String add (Authority au){
		return authorityService.add(au);
	}
	/**
	 * 获取权限菜单树
	 * @param request
	 * @return json字符串
	 */
	@RequestMapping(value="/sys/auth/tree",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAuthTree(HttpServletRequest request){
		
		return authorityService.getAuthTreeJson(request);
	}
}