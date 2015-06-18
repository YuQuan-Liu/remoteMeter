package com.xdkj.yccb.main.logger;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Actiontype;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Useractionlog;

@Repository
public class ActionLogDao extends HibernateDAO {

	public void addActionlog(int adminid, int type, String remark) {
		Useractionlog log = new Useractionlog();
		log.setActionTime(new Date());
		Actiontype actiontype = new Actiontype();
		actiontype.setPid(type);
		log.setActiontype(actiontype);
		log.setRemark(remark);
		Admininfo admin = new Admininfo();
		admin.setPid(adminid);
		log.setAdmininfo(admin);
		
		this.getHibernateTemplate().save(log);
		
	}

	
}
