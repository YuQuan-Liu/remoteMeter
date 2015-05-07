package com.xdkj.yccb.main.adminor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
			aiv.setAdminAddr(ai.getAdminAddr());
			aiv.setAdminEmail(ai.getAdminEmail());
			aiv.setAdminMobile(ai.getAdminMobile());
			aiv.setAdminName(ai.getAdminName());
			aiv.setAdminTel(ai.getAdminTel());
			aiv.setLoginName(ai.getLoginName());
			aiv.setNoWc(ai.getNoWc());
			aiv.setPid(ai.getPid());
			aiv.setRemark(ai.getRemark());
			aiv.setValid(ai.getValid());
			listView.add(aiv);
		}
		list=null;
		return listView;
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
	public String addAdmin(Admininfo adminInfo) {
		int pid = administratorDAO.addAdmin(adminInfo);
		if(pid>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public boolean removeById(Integer adminId) {
		return false;
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
}
