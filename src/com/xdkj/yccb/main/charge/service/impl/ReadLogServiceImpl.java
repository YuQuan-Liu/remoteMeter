package com.xdkj.yccb.main.charge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.charge.service.ReadLogService;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;

@Service
public class ReadLogServiceImpl implements ReadLogService {

	@Autowired
	private ReadLogDao readLogDao;
	@Override
	public String getReadLogToSettle(int n_id) {
		
		return readLogDao.getReadLogNeighborsNonSettle(n_id+"");
	}

}
