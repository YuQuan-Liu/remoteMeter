package com.xdkj.yccb.main.readme.service;

import java.util.List;

import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadService {

	public List<ReadView> getMeters(String n_id);

	public String checkReading(int readlogid, int adminid);

	public String checkControling(int valvelogid, int adminid);
	
}
