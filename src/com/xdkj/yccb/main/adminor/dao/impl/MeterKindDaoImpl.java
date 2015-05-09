package com.xdkj.yccb.main.adminor.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.MeterKindDao;
import com.xdkj.yccb.main.adminor.dto.MeterkindView;
import com.xdkj.yccb.main.entity.Meterkind;

@Repository
public class MeterKindDaoImpl extends HibernateDAO implements MeterKindDao {
	
	
	@Override
	public List<MeterkindView> getList() {
		
		String hql = "from Meterkind mk where valid = 1 ";
		List<Meterkind> list = getSession().createQuery(hql).list();
		
		if(list == null){
			return null;
		}
		List<MeterkindView> lv = new ArrayList<>();
		MeterkindView mkv = null;
		for(Meterkind mk : list){
			mkv = new MeterkindView();
			try {
				BeanUtils.copyProperties(mkv, mk);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lv.add(mkv);
		}
		return lv;
		
		
	}

}
