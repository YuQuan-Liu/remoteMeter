package com.xdkj.yccb.main.readme.service;

import java.util.List;

import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadService {

	public List<ReadView> getRemoteMeters(String n_id);

	public String checkReading(int readlogid, int adminid);

	public String checkControling(int valvelogid, int adminid);

	public List<ReadView> getNonRemoteMeters(String n_id);

	public String addNonRemoteRead(int m_id, int newread, int readlogid);

	public String showWaste(int readlogid);

	public void addWaste(int wid, String reason);

	/**
	 * 获取小区列表小的全部的远传表数据
	 * @param nid_list
	 * @return
	 */
	public List<ReadView> getAllRemoteMeters(List<Integer> nid_list);

	/**
	 * 添加抄表记录
	 * @param adminid
	 * @param n_id
	 * @return
	 */
	public String addreadlog(int adminid,int n_id);
	
}
