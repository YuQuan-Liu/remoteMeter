package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Readlog;

public interface ReadLogDao {
	
	public int addReadLog(Readlog readlog);

	List<Readlog> findadminReading(int adminid);

	public int addReadLogs(List<Readlog> readlogs);

	public Readlog getReadLogByID(int readlogid);
	
	public List<Readlog> getReadLogNeighbors(int readlogid,int adminid);

	/**
	 * 选出所有小区上次结算之后的所有成功的非抄单个表的readlog
	 * @param n_id
	 * @return
	 */
	public String getReadLogNeighborsNonSettle(String n_id);

	public void updateException(Readlog readlog, Admininfo admin, Exception e);

	/**
	 * 结算时
	 * @param n_id
	 * @return
	 */
	public Readlog getMaxReadlogNonSettle(int n_id);
	/**
	 * 结算过的  最大的抄表id
	 * @param n_id
	 * @return
	 */
	public Readlog getMaxReadlogSettle(int n_id);

	public void settleAll(int n_id, int adminid, int readlogid);

	public void settleSingle(int m_id, int adminid, int settlelogid);

}
