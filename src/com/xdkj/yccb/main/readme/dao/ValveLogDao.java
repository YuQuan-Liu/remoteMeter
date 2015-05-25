package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Valvelog;

public interface ValveLogDao {

	public List<Valvelog> findadminValveing(Integer adminid);

	public int addValveLog(Valvelog valvelog);

	public Valvelog getValveLogByID(int valvelogid);

	/**
	 * 返回异常影响的阀门操作的数目
	 * @param valvelog
	 * @param e
	 * @return
	 */
	public int updateControlException(Valvelog valvelog, Exception e);

}
