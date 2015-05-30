package com.xdkj.yccb.main.charge.service.impl;

import java.util.List;

import net.sf.jasperreports.engine.type.JsonOperatorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.SettleLogDao;
import com.xdkj.yccb.main.charge.dto.SettleView;
import com.xdkj.yccb.main.charge.service.SettleService;
import com.xdkj.yccb.main.entity.Readlog;
import com.xdkj.yccb.main.entity.Settlelog;
import com.xdkj.yccb.main.readme.dao.ReadLogDao;
import com.xdkj.yccb.main.readme.dao.ReadMeterLogDao;

@Service
public class SettleServiceImpl implements SettleService {

	@Autowired
	private ReadMeterLogDao readMeterLogDao;
	@Autowired
	private SettleLogDao settleLogDao;
	@Autowired
	private ReadLogDao readLogDao;
	
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
		// TODO Auto-generated method stub
		return null;
	}

}
