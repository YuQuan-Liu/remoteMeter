package com.xdkj.yccb.main.charge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.main.charge.dao.ControlWarnDao;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.service.WarnService;

@Service
public class WarnServiceImpl implements WarnService {

	@Autowired
	private ControlWarnDao controlWarnDao;
	@Override
	public List<ControlWarnView> getControlWarns(int n_id) {
		
		return controlWarnDao.getControlWarns(n_id);
	}

}
