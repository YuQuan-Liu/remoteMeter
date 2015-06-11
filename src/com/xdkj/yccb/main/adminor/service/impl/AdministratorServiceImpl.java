package com.xdkj.yccb.main.adminor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.adminor.dao.AdminRoleDao;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Roles;
@Service
public class AdministratorServiceImpl implements AdministratorService {
	@Autowired
	private AdministratorDAO administratorDAO;
	@Autowired
	private AdminRoleDao adminRoleDao;

	@Override
	public List<AdminInfoView> getList(int wcid) {
		
		return administratorDAO.getListView(wcid);
	}

	@Override
	public Admininfo getById(String adminId) {
		Admininfo ad = new Admininfo();
		if(null!=adminId&&!"".equals(adminId)){
			ad = administratorDAO.getById(Integer.parseInt(adminId));
		}
		return ad;
	}

	@Override
	public String addAdmin(Admininfo adminInfo,int roleid) {
		int pid = administratorDAO.addAdmin(adminInfo);
		if(pid>0){
			//add the admin_role
			AdminRole adminrole = new AdminRole();
			adminrole.setAdmininfo(adminInfo);
			
			Roles roles = new Roles();
			roles.setPid(roleid);
			adminrole.setRoles(roles);
			adminRoleDao.addAdminRole(adminrole);
			return "succ";
		}
		return "fail";
	}

	@Override
	public boolean removeById(Integer adminId) {
		
		return administratorDAO.removeById(adminId);
	}
	@Override
	public boolean update(Admininfo adminInfo) {
		Admininfo ad = administratorDAO.getById(adminInfo.getPid());
		/*try {
			PropertyUtils.copyProperties(ad, adminInfo);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}*/
		administratorDAO.update(ad);
		return false;
	}

	@Override
	public int getTotalCount(Admininfo adInfo) {
		return administratorDAO.getTotalCount(adInfo);
	}

	@Override
	public Admininfo getByLoginName(String loginName, String password) {
		return administratorDAO.getByLoginName(loginName, password);
	}

	@Override
	public String checkLoginName(String name) {
		
		return administratorDAO.checkLoginName(name);
	}
}
