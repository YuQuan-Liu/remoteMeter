package com.xdkj.yccb.main.adminor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.adminor.dao.MeterKindDao;
import com.xdkj.yccb.main.adminor.dto.MeterkindView;
import com.xdkj.yccb.main.adminor.service.MeterkindService;

@Service
public class MeterkindServiceImpl implements MeterkindService {

	@Autowired
	private MeterKindDao meterKindDao;
	@Override
	public List<MeterkindView> getList() {

		return meterKindDao.getList();
	}

}
