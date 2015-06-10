package com.xdkj.yccb.main.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.sys.dao.AuthorityDAO;
import com.xdkj.yccb.main.sys.dao.RoleAuthorityDAO;
import com.xdkj.yccb.main.sys.dao.RoleDAO;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.AuthorityService;
import com.xdkj.yccb.main.sys.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private RoleAuthorityDAO roleAuthorityDAO;
	@Autowired
	private AuthorityDAO authorityDAO;

	@Override
	public List<RoleView> getList(int wcid) {
		List<Roles> list = roleDAO.getList(wcid);
		List<RoleView> listView = new ArrayList<RoleView>();
		for (Roles roles : list) {
			RoleView r = new RoleView();
			r.setPid(roles.getPid());
			r.setRemark(roles.getRemark());
			r.setRoleName(roles.getRoleName());
			r.setSystemRole(roles.getSystemRole());
			r.setValid(roles.getValid());
			r.setWatercompany(roles.getWatercompany().getCompanyName());
			r.setWcid(roles.getWatercompany().getPid()+"");
			listView.add(r);
		}
		list = null;
		return listView;
	}

	@Override
	public String addRole(RoleView rv,String childauth,int wcid) {
		Set<RoleAuthority> auset = new HashSet<RoleAuthority>();
		String [] ids = childauth.split(",");
		Roles r = new Roles();
		for (String id : ids){
			RoleAuthority ra = new RoleAuthority();
			ra.setAuthority(new Authority(Integer.parseInt(id)));
			ra.setRoles(r);
			auset.add(ra);
		}
		r.setRoleAuthorities(auset);
		r.setRoleName(rv.getRoleName());
		r.setRemark(rv.getRemark());
		r.setSystemRole("0");
		r.setWatercompany(new Watercompany(wcid));
		r.setValid("1");
		int rid = roleDAO.add(r);
		if(rid>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String delete(RoleView rv) {
		return null;
	}

	@Override
	public int getCount(RoleView rv, PageBase pb) {
		return roleDAO.getTotalCount(rv, pb);
	}

	@Override
	public Roles getRoleInfo(String pid) {
		Roles r = roleDAO.getById(Integer.parseInt(pid));		
		return r;
	}
	
	@Override
	public String updateRole(String childauth,int pid) {
		
		//获取角色下 有效的权限
		List<Authority> auths = authorityDAO.getAuthsByRole(pid);
		String [] ids = childauth.split(",");
		
		List<String> c_ids = Arrays.asList(ids);
		
		if("".equals(childauth)){
			//delete all
		}else{
			List<String> auths_id = new ArrayList<>();
			Authority a = null;
			for(int i = 0;i < auths.size();i++){
				a = auths.get(i);
				auths_id.add(a.getPid()+"");
			}
			List<String> add= new ArrayList<>();
			List<String> delete = new ArrayList<>();
			
			for(int i = 0;i < ids.length;i++){
				if(!auths_id.contains(ids[i])){
					add.add(ids[i]);
				}
			}
			
			for(int i = 0;i < auths_id.size();i++){
				if(!c_ids.contains(auths_id.get(i))){
					delete.add(auths_id.get(i));
				}
			}
			
			if(add.size() > 0){
				roleAuthorityDAO.add(pid,add);
			}
			if(delete.size() > 0){
				roleAuthorityDAO.delete(pid,delete);
			}
		}
		
		return "succ";
	}
	
	
	@Override
	public String getAuthTreeJson(HttpServletRequest request) {
		List<Authority> list = authorityDAO.getList(0);
		RequestContext requestContext = new RequestContext(request);
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

	@Override
	public String checkname(int wcid, String name) {
		
		return roleDAO.checkname(wcid,name);
	}

	@Override
	public String getAuthTreeJson(HttpServletRequest request, int pid) {
		//获取角色下 有效的权限
		List<Authority> auths = authorityDAO.getAuthsByRole(pid);
		
		List<Authority> list = authorityDAO.getList(0);
		RequestContext requestContext = new RequestContext(request);
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
				if(auths.contains(auc)){
					o.put("checked", "true");
				}
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
