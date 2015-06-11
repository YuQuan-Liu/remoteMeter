package com.xdkj.yccb.main.adminor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.encoder.Md5PwdEncoder;
import com.xdkj.yccb.main.adminor.dao.AdminRoleDao;
import com.xdkj.yccb.main.adminor.dao.AdministratorDAO;
import com.xdkj.yccb.main.adminor.dto.AdminInfoView;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Department;
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
	public boolean update(Admininfo admin) {
		Admininfo ad = administratorDAO.getById(admin.getPid());
		ad.setAdminAddr(admin.getAdminAddr());
		ad.setAdminEmail(admin.getAdminEmail());
		ad.setAdminMobile(admin.getAdminMobile());
		ad.setLoginName(admin.getLoginName());
		ad.setAdminTel(admin.getAdminTel());
		
		return administratorDAO.update(ad);
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

	@Override
	public AdminInfoView getAdminViewById(int pid) {
		
		return administratorDAO.getViewByid(pid);
	}

	@Override
	public String changepwd(int pid, String old_, String new_) {
		//就密码是否相同
		Md5PwdEncoder md5 = new Md5PwdEncoder();
		String oldpwd = md5.encodePassword(old_);
		String newpwd = md5.encodePassword(new_);
		if(administratorDAO.checkoldpwd(pid,oldpwd)){
			//相同 更新密码
			administratorDAO.updatePwd(pid,newpwd);
			return "true";
		}
		return "false";
	}

	@Override
	public String resetpwd(int pid) {
		
		return administratorDAO.resetpwd(pid);
	}

	@Override
	public String changerole(int pid, int rid) {
		
		return adminRoleDao.updateRole(pid,rid);
	}

	@Override
	public String changedep(int pid, int did) {
		
		return administratorDAO.updateDep(pid,did);
	}
}
