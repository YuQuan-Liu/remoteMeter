package com.xdkj.yccb.main.charge.service;

import java.util.List;

import com.xdkj.yccb.main.charge.dto.ControlErrorView;

public interface ControlErrorService {

	List<ControlErrorView> getControlErrors(int n_id);

	String updateError(int conf_id,String reason);

}
