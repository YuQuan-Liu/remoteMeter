package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.SMSTemplateDao;
import com.xdkj.yccb.main.entity.SMSTemplate;

@Repository
public class SMSTemplateImpl extends HibernateDAO<SMSTemplate> implements SMSTemplateDao {

	@Override
	public List<SMSTemplate> getList(int wcid) {
		String hql = "from SMSTemplate sms where sms.watercompany.pid = :wcid and sms.qf = 0";
		Query q = getSession().createQuery(hql).setInteger("wcid", wcid);
		return q.list();
	}

	@Override
	public SMSTemplate getQF(int wcid) {
		String hql = "from SMSTemplate sms where sms.qf = 1 and sms.watercompany.pid = "+wcid;
		Query q = getSession().createQuery(hql);
		return (SMSTemplate) q.uniqueResult();
	}

	
}
