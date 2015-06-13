package com.xdkj.yccb.main.readme.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.readme.ReadMeter;

@Component
public class QuartzReadNeighbor implements Job{

	public static ReadMeter readMeter;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap map = context.getJobDetail().getJobDataMap();
		Neighbor n = (Neighbor) map.get("n");
		Admininfo admin = (Admininfo) map.get("admin");
		
		readMeter.readNeighbor(n, admin);
	}

}
