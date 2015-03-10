package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.PropertyAccessor;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.Admininfo;
@Service
public class AdministratorServiceImpl implements AdministratorService {
	@Autowired
	private AdministratorDAO administratorDAO;

	@Override
	public List<AdminInfoView> getList(Admininfo adInfo,PageBase pageInfo) {
		List<Admininfo> list = administratorDAO.getList(adInfo, pageInfo);
		List<AdminInfoView> listView = new ArrayList<AdminInfoView>() ;
		
			for (Admininfo ai : list) {
				AdminInfoView aiv = new AdminInfoView();
				try {
					PropertyUtils.copyProperties(aiv, ai);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
				listView.add(aiv);
			}
		return listView;
	}

	@Override
	public Admininfo getById(Integer adminId) {
		return null;
	}

	@Override
	public int addAdmin(Admininfo adminInfo) {
		return administratorDAO.addAdmin(adminInfo);
	}

	@Override
	public boolean removeById(Integer adminId) {
		return false;
	}

	@Override
	public boolean update(Admininfo adminInfo) {
		return false;
	}

	@Override
	public Integer getTotalCount(Admininfo adInfo) {
		return administratorDAO.getTotalCount(adInfo);
	}

	@Override
	public Admininfo getByLoginName(String loginName, String password) {
		return administratorDAO.getByLoginName(loginName, password);
	}
}
