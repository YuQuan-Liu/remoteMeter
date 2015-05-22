package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadDao {

	List<ReadView> getRemoteMeters(String n_id);

	List<ReadView> getNonRemoteMeters(String n_id);
	

}
