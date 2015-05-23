package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;

public interface WarnService {

	List<ControlWarnView> getControlWarns(int n_id);

}
