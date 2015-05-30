package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;

public interface WarnService {

	List<ControlWarnView> getControlWarns(int n_id);

	void addWarnSingle(Customer c, boolean done);

}
