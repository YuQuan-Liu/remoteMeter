package com.xdkj.yccb.main.readme.dao;

import com.xdkj.yccb.main.entity.Valveconflog;
import com.xdkj.yccb.main.entity.Valvelog;

public interface ValveConfLogDao {

	int addConfLog(Valveconflog conflog);

	/**
	 * 只能在单个阀门控制时  使用
	 * @param valvelogid
	 * @return
	 */
	Valveconflog getConfLogByLogID(int valvelogid);

	Valveconflog updateError(int conf_id, String reason);

	Valveconflog getConfLogByID(int conf_id);

	void addConfLogs(Object[] ids, Valvelog valvelog);

	int updateException(Valvelog valvelog);
}
