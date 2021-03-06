package com.xdkj.yccb.main.readme.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.readme.ReadMeter;
import com.xdkj.yccb.main.readme.dao.MeterDao;

/**
 * 系统启动时   2min之后   启动所有的定时抄表    
 * @author Rocket
 *
 */
public class StartAllTimer{

	@Autowired
	private NeighborDAO neighborDAO;
	@Autowired
	private GprsDAO gprsDAO;
	@Autowired
	private MeterDao meterDao;
	@Autowired
	private SchedulerFactoryBean schefactory;
	
	@Autowired
	private ReadMeter readMeter;
	
	public void startAll(){
		
//		System.out.println(readMeter);
		
		QuartzReadMeter.readMeter = readMeter;
		QuartzReadNeighbor.readMeter = readMeter;
		
		QuartzManager.sche = schefactory.getScheduler();
//		QuartzManager.addJob();
		//获取需要定时的全部小区  
		List<Neighbor> n_list = neighborDAO.getTimerList();
		
		for(int i = 0;i < n_list.size();i++){
			Neighbor n = n_list.get(i);
			Admininfo admin = neighborDAO.getAdmin(n);
			QuartzManager.addJobNeighbor(n, admin);
		}
		
		//获取需要定时的全部表具
		List<Meter> m_list = meterDao.getTimerList();
		
		for(int i = 0;i <m_list.size();i++){
			Meter m = m_list.get(i);
			Admininfo admin = neighborDAO.getAdmin(m.getNeighbor());
			Neighbor n = m.getNeighbor();
			n.getIp();
			QuartzManager.addJobMeter(m,n, admin);
		}
		
		//获取全部需要阀门清理的集中器
		List<Gprs> g_list = gprsDAO.getCleanList();
		
		for(int i = 0;i < g_list.size();i++){
			Gprs g = g_list.get(i);
			QuartzManager.addJobCleanValve(g);
		}
		
	}

}
