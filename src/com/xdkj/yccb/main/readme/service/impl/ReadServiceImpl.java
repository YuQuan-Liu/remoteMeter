package com.xdkj.yccb.main.readme.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.dto.ReadView;
import com.xdkj.yccb.main.readme.service.ReadService;

@Service
public class ReadServiceImpl implements ReadService {

	@Autowired
	private ReadDao readDao;
	@Autowired
	private ReadLogDao readLogDao;
	
	@Autowired
	private NeighborDAO neighborDao;
	@Override
	public List<ReadView> getMeters(String n_id) {
		
		List<ReadView> list = readDao.getMeters(n_id);
		return list;
	}
	
	@Override
	public String addNeighbor(String n_id,int adminid) {
		
		
		
		return null;
	}

}
