package com.xdkj.yccb.main.readme.service;

import java.util.List;

import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadService {

	public List<ReadView> getMeters(String n_id);

	public String addNeighbor(String n_id,int adminid);

	
}
