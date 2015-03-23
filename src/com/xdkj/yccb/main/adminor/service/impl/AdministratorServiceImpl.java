package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		List<AdminInfoView> listView2 = new ArrayList<AdminInfoView>() ;
		long l1 = System.currentTimeMillis();
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
				/*try {
					PropertyUtils.copyProperties(aiv, ai);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
				//密码保护
				aiv.setLoginKey(null);*/
				listView.add(aiv);
			}
			long l2 = System.currentTimeMillis();
			for (Admininfo ai : list) {
				AdminInfoView aiv = new AdminInfoView();
				try {
					PropertyUtils.copyProperties(aiv, ai);
				} catch (IllegalAccessException | InvocationTargetException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
				//密码保护
				aiv.setLoginKey(null);
				listView2.add(aiv);
			}
			long l3 = System.currentTimeMillis();
			System.err.println("==普通花费==="+(l2-l1)+"===util花费==="+(l3-l2));
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
	@Transactional
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
