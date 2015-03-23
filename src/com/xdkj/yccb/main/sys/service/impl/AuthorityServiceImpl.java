package com.xdkj.yccb.main.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.sys.dao.AuthorityDAO;
import com.xdkj.yccb.main.sys.dto.AuthorityView;
import com.xdkj.yccb.main.sys.service.AuthorityService;
@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDAO authorityDAO;

	@Override
	public List<AuthorityView> getList(Authority au,PageBase pageInfo) {
		List<Authority> list = authorityDAO.getAllList(au, pageInfo);
		List<AuthorityView> listView = new ArrayList<AuthorityView>();
		for (Authority auy : list) {
			AuthorityView av = new AuthorityView();
			av.setPid(auy.getPid());
			av.setAuthorityCode(auy.getAuthorityCode());
			av.setActUrl(auy.getActUrl());
			av.setPname(auy.getAu().getRemark());
			av.setPpid(auy.getPpid());
			av.setRemark(auy.getRemark());
			listView.add(av);
		}
		return listView;
	}

	@Override
	public String add(Authority au) {
		int id = authorityDAO.add(au);
		if(id>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String update(Authority au) {
		return null;
	}

	@Override
	public String delete(String ids) {
		return null;
	}

	@Override
	public int getTotalCount(Authority au) {
		return authorityDAO.getTotalCount(au);
	}

	@Override
	public List<AuthorityView> getParentAuth() {
		List<Authority> l = authorityDAO.getAuthByPpid(0);
		List<AuthorityView> list = new ArrayList<AuthorityView>();
		for (Authority au : l) {
			AuthorityView av = new AuthorityView();
			av.setPid(au.getPid());
			av.setPname(au.getAu().getRemark());
			av.setRemark(au.getRemark());
			av.setPpid(au.getPpid());
			list.add(av);
		}
		return list;
		
	}

	@Override
	public String getAuthTreeJson(HttpServletRequest request) {
		//一级菜单
		List<Authority> list = authorityDAO.getList(0);
		String json = "";
		for (Authority au : list) {
			 RequestContext requestContext = new RequestContext(request);
			JSONArray jarr = new JSONArray();
			List<Authority> clist = authorityDAO.getList(au.getPid());
			for (Authority auc : clist) {
				JSONObject o = new JSONObject();
				o.put("id", auc.getPid());
				 String menus = requestContext.getMessage(auc.getAuthorityCode());
				o.put("text", menus);
				o.put("url", auc.getActUrl());
				//o.put("state", "closed");
				jarr.add(o);
			}
			String j1 = jarr.toString();
			json+="{\"id\":\""+au.getPid()+"\",\"state\":\"closed\",\"text\":\""
			+requestContext.getMessage(au.getAuthorityCode())+"\",\"url\":\""+
					au.getActUrl()+"\",\"children\":"+j1+"},";
		}
		json = json.substring(0, json.lastIndexOf(","));
		json = "["+json+"]";
		return json;
	}

}
