package com.xdkj.yccb.main.statistics.service;

import java.util.List;

import com.xdkj.yccb.main.statistics.dto.AdminSum;
import com.xdkj.yccb.main.statistics.dto.PayInfo;

public interface PayLogService {

	List<PayInfo> getCustomerPayLogs(int n_id, String start, String end, int pre);

	List<AdminSum> getAdminSum(int n_id, String start, String end, int pre);

}
