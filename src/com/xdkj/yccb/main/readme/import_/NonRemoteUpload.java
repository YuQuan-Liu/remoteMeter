package com.xdkj.yccb.main.readme.import_;

import java.util.List;

import com.xdkj.yccb.main.entity.Readmeterlog;
import com.xdkj.yccb.main.readme.service.MeterService;

public interface NonRemoteUpload {
	public List<Readmeterlog> read(String excelPath,int readlogid,int n_id,MeterService meterService);

}
