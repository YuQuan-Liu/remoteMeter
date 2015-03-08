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
		//if(null != list&&list.size() > 0){
			for (Admininfo ai : list) {
				AdminInfoView aiv = new AdminInfoView();
				aiv.setPid(ai.getPid());
				//aiv.setWatercompany(ai.getWatercompany());
				//aiv.setDepartment(ai.getDepartment());
				aiv.setAdminName(ai.getAdminName());
				aiv.setLoginName(ai.getLoginName());
				aiv.setLoginKey(ai.getLoginKey());
				aiv.setAdminEmail(ai.getAdminEmail());
				aiv.setAdminAddr(ai.getAdminAddr());
				aiv.setAdminMobile(ai.getAdminMobile());
				aiv.setAdminTel(ai.getAdminTel());
				aiv.setNoWc(ai.getNoWc());
				aiv.setValid(ai.getValid());
				aiv.setRemark(ai.getRemark());
				listView.add(aiv);
			}
		//}
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

}
