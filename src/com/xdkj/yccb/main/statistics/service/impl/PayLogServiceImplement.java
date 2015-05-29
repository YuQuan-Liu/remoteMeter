package com.xdkj.yccb.main.statistics.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.statistics.dto.AdminSum;
import com.xdkj.yccb.main.statistics.dto.PayInfo;
import com.xdkj.yccb.main.statistics.service.PayLogService;

@Service
public class PayLogServiceImplement implements PayLogService {

	@Autowired
	private CustompaylogDAO custompaylogDAO;
	
	@Override
	public List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre) {
		
		return custompaylogDAO.getCustomerPayLogs(n_id, start, end, pre);
	}

	@Override
	public List<AdminSum> getAdminSum(int n_id, String start, String end, int pre) {
			
		return custompaylogDAO.getAdminSum(n_id, start, end, pre);
	}

}
