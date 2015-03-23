package com.xdkj.yccb.main.sys.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Roles;
import com.xdkj.yccb.main.sys.dao.RoleDAO;
import com.xdkj.yccb.main.sys.dto.RoleView;
import com.xdkj.yccb.main.sys.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDAO roleDAO;

	@Override
	public List<RoleView> getList(RoleView rv, PageBase pb) {
		List<Roles> list = roleDAO.getList(rv, pb);
		List<RoleView> listView = new ArrayList<RoleView>();
		for (Roles roles : list) {
			RoleView r = new RoleView();
			try {
				PropertyUtils.copyProperties(r, roles);
				listView.add(r);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return listView;
	}

	@Override
	public String addRole(Roles r) {
		
		return "fail";
	}

	@Override
	public String delete(Roles r) {
		return null;
		
	}

	@Override
	public int getCount(RoleView rv, PageBase pb) {
		return 0;
	}

}
