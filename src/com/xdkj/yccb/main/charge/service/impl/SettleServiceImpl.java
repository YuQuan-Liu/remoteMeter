package com.xdkj.yccb.main.charge.service.impl;

import java.math.BigDecimal;
import java.util.List;

import net.sf.jasperreports.engine.type.JsonOperatorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.SettleLogDao;
import com.xdkj.yccb.main.charge.dto.SettleSum;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.dto.SettleView;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Settlelog;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;

@Service
public class SettleServiceImpl implements SettleService {

	@Autowired
	private ReadMeterLogDao readMeterLogDao;
	@Autowired
	private SettleLogDao settleLogDao;
	@Autowired
	private ReadLogDao readLogDao;
	@Autowired
	private MeterDao meterDao;
	@Autowired
	private MeterDeductionLogDao meterDeductionLogDao;
	
	
	@Override
	public List<SettleView> getSettleData(int n_id) {
		
		return readMeterLogDao.getReadMeterLogToSettle(n_id);
	}

	@Override
	public String settleAll(int n_id,int adminid) {
		// TODO Auto-generated method stub
		//查看今天是否已经有管理员结算过了   
		//查看现在是否有未完成的结算
		JSONObject jo = new JSONObject();
		List<Settlelog> logs = settleLogDao.getTodaySettleLog(n_id);
		if(logs.size() > 0){
			//今天已经结算了  不能再结算了
			jo.put("done", false);
			jo.put("reason", "今天已结算");
		}else{
			
			Readlog readlog = readLogDao.getMaxReadlogNonSettle(n_id);
			if(readlog.getSettle() == 1){
				jo.put("done", false);
				jo.put("reason", "无最新抄表记录");
			}else{
				settleLogDao.settleAll(n_id,adminid,readlog.getPid());
				jo.put("done", true);
				jo.put("reason", "");
			}
		}
		return jo.toJSONString();
	}

	@Override
	public String settleSingle(int m_id,int adminid) {
		JSONObject jo = new JSONObject();
		Meter meter = meterDao.getMeterByID(m_id);
		if(meter.getReaddata() - meter.getDeRead() == 0){
			jo.put("done", false);
			jo.put("reason", "用水量为0");
		}else{
			Settlelog settlelog = settleLogDao.getLastSettleLog(meter.getNeighbor().getPid());
			if(settlelog == null){
				jo.put("done", false);
				jo.put("reason", "小区未结算过");
			}else{
				settleLogDao.settleSingle(m_id,adminid,settlelog.getPid());
				SettleView settleView = readMeterLogDao.getReadMeterLog(m_id);
				jo.put("done", true);
				jo.put("reason", "");
				jo.put("customerBalance", settleView.getCustomerBalance().doubleValue());
				jo.put("readdate", settleView.getReaddata());
				jo.put("deread", settleView.getDeread());
			}
		}
		return jo.toJSONString();
	}

	@Override
	public String getSettleLog(int n_id) {
		
		return settleLogDao.getSettleLogs(n_id);
	}

	@Override
	public List<SettledView> getSettleDataPostPay(int n_id, int settle_id) {
		
		return meterDeductionLogDao.getLogPostPay(n_id,settle_id); 
	}

	@Override
	public String getSettleLogAuto(int n_id) {

		return settleLogDao.getSettleLogsAuto(n_id);
	}

	@Override
	public List<SettledView> getSettleAuto(int n_id, int settle_id) {
		
		return meterDeductionLogDao.getLogAuto(n_id,settle_id); 
	}

	@Override
	public List<SettleSum> getSettleYL(int n_id) {
		List<SettleSum> list = readMeterLogDao.getSettleSum(n_id);
		int yl = 0;
		SettleSum sum = null;
		for(int i = 0;i < list.size();i++){
			sum = list.get(i);
			yl += sum.getYl();
		}
		sum = new SettleSum();
		sum.setDemoney(0);
		sum.setYl(yl);
		sum.setPricekindname("总水量");
		list.add(sum);
		return list;
	}

	@Override
	public List<SettleSum> getSettleYL(int n_id, int settle_id) {
		List<SettleSum> list = readMeterLogDao.getSettleSum(n_id,settle_id);
		int yl = 0;
		double demoney = 0;
		SettleSum sum = null;
		for(int i = 0;i < list.size();i++){
			sum = list.get(i);
			yl += sum.getYl();
			demoney += sum.getDemoney();
		}
		sum = new SettleSum();
		sum.setDemoney(demoney);
		sum.setYl(yl);
		sum.setPricekindname("总水量");
		list.add(sum);
		return list;
	}

}
