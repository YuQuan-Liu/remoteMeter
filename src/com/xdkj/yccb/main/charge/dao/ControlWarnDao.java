package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;

public interface ControlWarnDao {

	List<ControlWarnView> getControlWarns(int n_id);

}
