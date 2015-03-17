package com.xdkj.yccb.main.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
