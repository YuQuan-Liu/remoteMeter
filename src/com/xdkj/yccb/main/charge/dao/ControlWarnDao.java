package com.xdkj.yccb.main.charge.dao;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;

public interface ControlWarnDao {

	List<ControlWarnView> getControlWarns(int n_id);

	void addWarnLog(Customer c, boolean done);

}
