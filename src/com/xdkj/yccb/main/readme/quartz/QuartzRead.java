package com.xdkj.yccb.main.readme.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.readme.ReadMeter;

@Component
public class QuartzRead implements Job{

	public static ReadMeter readMeter;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println(new Date());
		
		System.out.println(readMeter+"readmeter~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		readMeter.readMeter(m, admin);
	}

}
