package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlErrorView;
import com.xdkj.yccb.main.entity.Valveconflog;

public interface ControlErrorDao {

	List<ControlErrorView> getControlErrors(int n_id);
	
}
