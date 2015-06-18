package com.xdkj.yccb.main.logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionLogService {

	@Autowired
	private ActionLogDao actionLogDao;
	public void addActionlog(int adminid,int type,String remark) {
		actionLogDao.addActionlog(adminid,type,remark);
	}
}
