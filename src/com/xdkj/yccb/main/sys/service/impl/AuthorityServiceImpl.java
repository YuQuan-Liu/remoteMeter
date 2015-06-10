package com.xdkj.yccb.main.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dao.AuthorityDAO;
import com.xdkj.yccb.main.sys.dao.RoleDAO;
import com.xdkj.yccb.main.sys.dto.AuthorityView;
import com.xdkj.yccb.main.sys.service.AuthorityService;
@Service
public class AuthorityServiceImpl implements AuthorityService {
	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private RoleDAO roleDAO;

	@Override
	public String getAuthTreeJson(HttpServletRequest request) {
		List<Authority> list = authorityDAO.getList(0);
		RequestContext requestContext = new RequestContext(request);
		String json = "";
		JSONArray ja = new JSONArray();
		for (Authority au : list) {
			JSONObject parent = new JSONObject();
			JSONArray children = new JSONArray();
			List<Authority> clist = authorityDAO.getList(au.getPid());
			for (Authority auc : clist) {
				JSONObject o = new JSONObject();
				o.put("id", auc.getPid());
				o.put("text", requestContext.getMessage(auc.getAuthorityCode()));
				o.put("url", auc.getActUrl());
//				if(auids.contains(String.valueOf(auc.getPid()))){
//					o.put("checked", "true");
//				}
				children.add(o);
			}
			parent.put("id", au.getPid());
			parent.put("text", requestContext.getMessage(au.getAuthorityCode()));
			parent.put("state", "closed");
			parent.put("url", au.getActUrl());
			parent.put("children", children);
			ja.add(parent);
		}
		
		return ja.toString();
	}

}
