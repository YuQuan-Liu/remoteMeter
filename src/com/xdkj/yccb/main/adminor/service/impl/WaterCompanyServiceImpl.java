package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.WaterCompanyDAO;
import com.xdkj.yccb.main.adminor.dto.WaterCompanyView;
import com.xdkj.yccb.main.adminor.service.WaterCompanyService;
import com.xdkj.yccb.main.entity.Watercompany;
@Service
public class WaterCompanyServiceImpl implements WaterCompanyService {
	@Autowired
	private WaterCompanyDAO waterCompanyDAO;
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
	public String addWatcom(Watercompany watcom) {
		int wid = waterCompanyDAO.addWatcom(watcom);
		if(wid>0){
			return "succ";
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
