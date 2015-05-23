package com.xdkj.yccb.main.charge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.ControlErrorDao;
import com.xdkj.yccb.main.charge.dto.ControlErrorView;
import com.xdkj.yccb.main.charge.service.ControlErrorService;
import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.readme.ValveControl;
import com.xdkj.yccb.main.readme.dao.ValveConfLogDao;

@Service
public class ControlErrorImpl implements ControlErrorService {

	@Autowired
	private ControlErrorDao controlErrorDao;
	@Autowired
	private ValveConfLogDao valveConfLogDao;
	@Override
	public List<ControlErrorView> getControlErrors(int n_id) {
		
		return controlErrorDao.getControlErrors(n_id);
	}
	@Override
	public String updateError(int conf_id,String reason) {
		Valveconflog valveconflog = valveConfLogDao.updateError(conf_id, reason);
		JSONObject jo = new JSONObject();
		jo.put("pid", valveconflog.getPid());
		jo.put("errorStatus", valveconflog.getErrorStatus());
		return jo.toJSONString();
	}

}
