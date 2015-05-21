package com.xdkj.yccb.main.readme.dao;

import com.xdkj.yccb.main.entity.Readmeterlog;

public interface ReadMeterLogDao {

	Readmeterlog getMaxReadMeterLog(int m_id);

	int addReadMeterLog(Readmeterlog newlog);

}
