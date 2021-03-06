package com.xdkj.yccb.main.readme.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;

@Component
public class QuartzManager {
	private static final Logger logger = LoggerFactory.getLogger(QuartzManager.class);
	
	public static Scheduler sche;
	
	public static void addJobNeighbor(Neighbor n,Admininfo admin) {
		JobDataMap map = new JobDataMap();
		map.put("n", n);
		map.put("admin", admin);
		
		JobDetail jobDetail = JobBuilder.newJob(QuartzReadNeighbor.class)
				.withIdentity(n.getPid()+"j", "neighbor")
				.usingJobData(map)
				.build();
		
		String timer = n.getTimer();
		String[] day_hour = timer.split("-");  //0-几号    1-几点
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(n.getPid()+"t", "neighbor")
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 "+day_hour[1]+" "+day_hour[0]+" * ?"))
				.build();
		try {
			logger.info("定时添加：nid:"+n.getPid()+"name:"+n.getNeighborName()+"timer:"+timer+"adminid:"+admin.getPid());
			sche.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void addJobMeter(Meter m,Neighbor n,Admininfo admin) {
		JobDataMap map = new JobDataMap();
		map.put("m", m);
		map.put("n", n);
		map.put("admin", admin);
		
		JobDetail jobDetail = JobBuilder.newJob(QuartzReadMeter.class)
				.withIdentity(m.getPid()+"j", "meter")
				.usingJobData(map)
				.build();
		
		int mins = Integer.parseInt(m.getTimer());
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(m.getPid()+"t", "meter")
				.startNow()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(mins).repeatForever())
				.build();
		try {
			logger.info("定时添加：mid:"+m.getPid()+"mins:"+mins+"adminid:"+admin.getPid());
			sche.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
//	public static void addJob() {
//		
//		JobDetail jobDetail = JobBuilder.newJob(QuartzRead.class)
//				.withIdentity("j", "meter")
//				.build();
//		
//		Trigger trigger = TriggerBuilder.newTrigger()
//				.withIdentity("t", "meter")
//				.startNow()
//				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever())
//				.build();
//		try {
//			sche.scheduleJob(jobDetail, trigger);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public static void modifyMeterJobTime(Meter m,Neighbor n,Admininfo admin){
		try {
			SimpleTrigger tri = (SimpleTrigger) sche.getTrigger(TriggerKey.triggerKey(m.getPid()+"t", "meter"));
			int mins = Integer.parseInt(m.getTimer());
			if(tri != null){
				removeMeter(m);
//				tri.getTriggerBuilder().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(mins).repeatForever());
//				sche.resumeTrigger(TriggerKey.triggerKey(m.getPid()+"t", "meter"));
			}
			addJobMeter(m,n, admin);
			logger.info("定时修改：mid:"+m.getPid()+"mins:"+mins+"adminid:"+admin.getPid());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void modifyNeighborJobTime(Neighbor n,Admininfo admin){
		try {
			CronTrigger tri = (CronTrigger) sche.getTrigger(TriggerKey.triggerKey(n.getPid()+"t", "neighbor"));
			String timer = n.getTimer();
			if(tri != null){
				removeNeighbor(n);
//				String[] day_hour = timer.split("-");  //0-几号    1-几点
//				
//				tri.getTriggerBuilder().withSchedule(CronScheduleBuilder.cronSchedule("0 0 "+day_hour[1]+" "+day_hour[0]+" * ?"));
//				
//				sche.resumeTrigger(TriggerKey.triggerKey(n.getPid()+"t", "neighbor"));
			}
			addJobNeighbor(n, admin);
			
			logger.info("定时修改：nid:"+n.getPid()+"name:"+n.getNeighborName()+"timer:"+timer+"adminid:"+admin.getPid());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeNeighbor(Neighbor n){
		
		try {
			sche.pauseTrigger(TriggerKey.triggerKey(n.getPid()+"t", "neighbor"));
			sche.unscheduleJob(TriggerKey.triggerKey(n.getPid()+"t", "neighbor"));
			sche.deleteJob(JobKey.jobKey(n.getPid()+"j", "neighbor"));
			logger.info("定时删除：nid:"+n.getPid()+"name:"+n.getNeighborName());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeMeter(Meter m){
		
		try {
			sche.pauseTrigger(TriggerKey.triggerKey(m.getPid()+"t", "meter"));
			sche.unscheduleJob(TriggerKey.triggerKey(m.getPid()+"t", "meter"));
			sche.deleteJob(JobKey.jobKey(m.getPid()+"j", "meter"));
			logger.info("定时删除：mid:"+m.getPid());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void addJobCleanValve(Gprs gprs) {
		JobDataMap map = new JobDataMap();
		map.put("gprs", gprs);
		
		JobDetail jobDetail = JobBuilder.newJob(QuartzCleanValve.class)
				.withIdentity(gprs.getPid()+"j", "gprs")
				.usingJobData(map)
				.build();
		
		//每个月的5号  20号 的凌晨1点
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(gprs.getPid()+"t", "gprs")
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 5,20 * ?"))
				.build();
//		Trigger trigger = TriggerBuilder.newTrigger()
//				.withIdentity(gprs.getPid()+"t", "gprs")
//				.startNow()
//				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(2).repeatForever())
//				.build();
		
		
		try {
			sche.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	public static void removeJobCleanValve(Gprs gprs) {
		try {
			sche.pauseTrigger(TriggerKey.triggerKey(gprs.getPid()+"t", "gprs"));
			sche.unscheduleJob(TriggerKey.triggerKey(gprs.getPid()+"t", "gprs"));
			sche.deleteJob(JobKey.jobKey(gprs.getPid()+"j", "gprs"));
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	public static void modifyJobCleanValve(Gprs gprs) {
		try {
			SimpleTrigger tri = (SimpleTrigger) sche.getTrigger(TriggerKey.triggerKey(gprs.getPid()+"t", "gprs"));
			if(tri != null){
				removeJobCleanValve(gprs);
//				tri.getTriggerBuilder().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(mins).repeatForever());
//				sche.resumeTrigger(TriggerKey.triggerKey(m.getPid()+"t", "meter"));
			}
			addJobCleanValve(gprs);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
}
