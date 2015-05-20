package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.dto.ReadView;

public interface ReadDao {

	List<ReadView> getMeters(String n_id);
	

}
