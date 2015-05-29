package com.xdkj.yccb.main.readme.dao;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.entity.Readmeterlog;

public interface ReadMeterLogDao {

	Readmeterlog getMaxReadMeterLog(int m_id);

	Readmeterlog addReadMeterLog(Readmeterlog newlog);

	Map addReadMeterLogs(List<Readmeterlog> list);

}
