package com.xdkj.yccb.main.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.entity.Watercompany;
import com.xdkj.yccb.main.sys.dao.RoleAuthorityDAO;
import com.xdkj.yccb.main.sys.dao.RoleDAO;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private RoleAuthorityDAO roleAuthorityDAO;

	@Override
	public List<RoleView> getList(RoleView rv, PageBase pb) {
		List<Roles> list = roleDAO.getList(rv, pb);
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
		return listView;
	}

	@Override
	public String addRole(RoleView rv,String chAuth,String pAuth) {
		Set<RoleAuthority> auset = new HashSet<RoleAuthority>();
		String [] ids = (chAuth+pAuth).split(",");
		Roles r = new Roles();
		for (String id : ids) {
			RoleAuthority ra = new RoleAuthority();
			if(null!=id&&!"".equals(id)){
				ra.setAuthority(new Authority(Integer.parseInt(id)));
			}
			ra.setRoles(r);
			auset.add(ra);
		}
		
		r.setRoleAuthorities(auset);
		r.setRoleName(rv.getRoleName());
		r.setRemark(rv.getRemark());
		r.setSystemRole(rv.getSystemRole());
		r.setWatercompany(new Watercompany(Integer.parseInt(rv.getWcid())));
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
	public String updateRole(RoleView rv, String chAuth, String pAuth) {
		String info = "succ";
		Roles r = roleDAO.getById(rv.getPid());
		Set<RoleAuthority> set = r.getRoleAuthorities();
		List<Integer> roleAuthIds = new ArrayList<Integer>();
		for (RoleAuthority ra : set) {
			roleAuthIds.add(ra.getPid());
		}
		if(roleAuthIds.size()>0){
			roleAuthorityDAO.delete(roleAuthIds);
		}
		Set<RoleAuthority> auset = new HashSet<RoleAuthority>();
		String [] ids = (chAuth+pAuth).split(",");
		for (String id : ids) {
			RoleAuthority ra = new RoleAuthority();
			if(null!=id&&!"".equals(id)){
				ra.setAuthority(new Authority(Integer.parseInt(id)));
			}
			ra.setRoles(r);
			auset.add(ra);
		}
		r.setSystemRole(rv.getSystemRole());
		r.setRoleAuthorities(auset);
		r.setRoleName(rv.getRoleName());
		r.setRemark(rv.getRemark());
		r.setWatercompany(new Watercompany(Integer.parseInt(rv.getWcid())));
		roleDAO.update(r);
		return info;
	}

}
