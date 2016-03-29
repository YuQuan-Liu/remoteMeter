package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.SMSTemplate;

public interface SMSTemplateDao {
	
	/**
	 * 根据wcid获取所有的短信模板
	 * @param wcid
	 * @return
	 */
	List<SMSTemplate> getList(int wcid);

	/**
	 * 获取欠费通知的短信模板  qf==1为普通用户模板  qf==2企业模板
	 * @param pid
	 * @param qf
	 * @return
	 */
	SMSTemplate getQF(int pid,int qf);

}
