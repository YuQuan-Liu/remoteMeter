package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.common.encoder.Base64Pwd;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dao.WaterCompanyDAO;
import com.xdkj.yccb.main.adminor.dto.WaterCompanyView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Watercompany;
@Service
public class WaterCompanyServiceImpl implements WaterCompanyService {
	@Autowired
	private WaterCompanyDAO waterCompanyDAO;
	@Autowired
	private AdministratorService administratorService;
	
	@Override
	public List<WaterCompanyView> getList(Watercompany watcom, PageBase pageInfo) {
		List<Watercompany> list = waterCompanyDAO.getList(watcom, pageInfo);
		List<WaterCompanyView> listView = new ArrayList<WaterCompanyView>();
		for (Watercompany wa : list) {
			WaterCompanyView wc = new WaterCompanyView();
			try {
				PropertyUtils.copyProperties(wc, wa);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
			listView.add(wc);
		}
		list=null;
		return listView;
	}

	@Override
	public int getTotalCount(Watercompany watcom) {
		return waterCompanyDAO.getTotalCount(watcom);
	}

	@Override
	public String addWatcom(Watercompany watcom,String adminName,String loginName) {
		watcom.setEmailPassword(Base64Pwd.encode(watcom.getEmailPassword()));
		int wid = waterCompanyDAO.addWatcom(watcom);
		if(wid>0){
			//add the super admin
			Admininfo admin = new Admininfo();
			admin.setAdminAddr("");
			admin.setAdminEmail("");
			admin.setAdminMobile("");
			admin.setAdminName(adminName);
			admin.setAdminTel("");
			admin.setDepartment(null);
			admin.setLoginKey("96e79218965eb72c92a549dd5a330112");
			admin.setLoginName(loginName);
			admin.setNoWc(0);
			admin.setValid("1");
			admin.setWatercompany(watcom);
			
			//自来水公司自动添加的角色的pid 为1
			return administratorService.addAdmin(admin, 2);
		}
		return "fail";
	}

	@Override
	public Watercompany getById(String pid) {
		if(null!=pid&&!"".equals(pid)){
			return waterCompanyDAO.getById(Integer.parseInt(pid));
		}
		return null;
	}

	@Override
	public String update(Watercompany watcom) {
		
		return waterCompanyDAO.updateWC(watcom);
	}

	

}
