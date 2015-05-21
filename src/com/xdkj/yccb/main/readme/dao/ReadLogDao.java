package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Readlog;

public interface ReadLogDao {
	
	public int addReadLog(Readlog readlog);

	List<Readlog> findadminReading(int adminid);

	public int addReadLogs(List<Readlog> readlogs);

	public Readlog getReadLogByID(int readlogid);
	
	public List<Readlog> getReadLogNeighbors(int readlogid,int adminid);

}
