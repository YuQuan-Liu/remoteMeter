package com.xdkj.yccb.main.charge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.util.JsonUtil;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.charge.dto.CustomerpaylogView;
import com.xdkj.yccb.main.charge.dto.MeterDereadMonth;
import com.xdkj.yccb.main.charge.dto.SettledView;
import com.xdkj.yccb.main.charge.service.ChargeService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.readme.dao.MeterDao;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
import com.xdkj.yccb.main.statistics.dto.MeterdeductionlogView;
import com.xdkj.yccb.main.statistics.dto.MonthSettled;
@Service
public class ChargeServiceImpl implements ChargeService {
	@Autowired
	private CustomerDao custDAO;
	@Autowired
	private CustompaylogDAO custompaylogDAO;
	@Autowired
	private MeterDeductionLogDao meterDeductionLogDao;
	@Autowired
	private MeterDao meterDao;

	@Override
	public String updatePayment(int cstId, int prePaySign) {
		
		return custDAO.updatePrePaySign(cstId, (byte)prePaySign)+"";
	}

	@Override
	public List<CustomerpaylogView> getCList(int custId, int count) {
		return custompaylogDAO.getList(count, custId);
	}


	@Override
	public CustomerpaylogView getPaylog(int cplid) {
		return custompaylogDAO.getViewById(cplid);
	}

	@Override
	public List<Customerpaylog> getPaylogLimit2(int cid,int cplid) {
		return custompaylogDAO.getPaylogLimit2(cid,cplid);
	}
	

	@Override
	public BigDecimal sumAfterPay(int cid, int cplid) {
		return custompaylogDAO.getsumAfterPay(cid,cplid);
	}

	

	@Override
	public List<SettledView> getMList(int c_id, int count) {
		
		return meterDeductionLogDao.getLogDetail(c_id, count);
	}
	

	@Override
	public List<SettledView> getMeterDeLog(int cid, Date start, Date end) {
		return meterDeductionLogDao.getLogDetail(cid, start, end);
	}

	@Override
	public String updatePrice(String meterId, String priceId) {
		return meterDao.updateMeterPrice(Integer.parseInt(meterId), Integer.parseInt(priceId))+"";
	}

	@Override
	public String cancleCustPay(String custPayLogId) {
		JSONObject j = new JSONObject();
		//将custpaylog valid置为0
		Customerpaylog cpl = custompaylogDAO.getById(Integer.parseInt(custPayLogId));
		if(cpl.getValid().equals("1")){
			int custId = cpl.getCustomer().getPid();
			cpl.setValid("0");
			custompaylogDAO.updateCustLog(cpl);
			BigDecimal pay = cpl.getAmount();
			//将交给额加至Customer 余额 CustomerBalance
			custDAO.updateCustomerBalance(pay.negate(), custId);
			//插入操作记录
			
		}
		j.put("state", "succ");
		return j.toJSONString();
	}

	@Override
	public String cancleCost(String meterDeLogId) {
		JSONObject j = new JSONObject();
		//将MeterDeductionLog valid置为0
		Meterdeductionlog mdl = meterDeductionLogDao.getById(Integer.parseInt(meterDeLogId));
		
		//获取当前表最新的有效的扣费记录的pid
		Meterdeductionlog mdl_news = meterDeductionLogDao.getLastmdl(mdl.getMeter().getPid());
		
		if(mdl_news.getPid() == mdl.getPid()){
			if(mdl.getValid()=='1'){
				int custId = mdl.getMeter().getCustomer().getPid();
				mdl.setValid('0');
				meterDeductionLogDao.updateMeterductionLog(mdl);
				meterDao.updateDeread(mdl.getMeter().getPid(), mdl.getLastDeRead(), mdl.getLastDeTime());
				BigDecimal pay = mdl.getDeMoney();
				//将交给额加至Customer 余额 CustomerBalance
				custDAO.updateCustomerBalance(pay, custId);
				//插入操作记录
				
			}
			j.put("state", "succ");
		}else{
			j.put("state", "fail");
		}
		
		
		return j.toJSONString();
	}

	@Override
	public String addpay(int adminid,int c_id, BigDecimal amount) {
		JSONObject jo = new JSONObject();
		if(amount.doubleValue()>0){
			Customer c = custDAO.updateCustomerBalance(amount, c_id);
			int cplid = custompaylogDAO.addPaylog(adminid,amount,c_id);
			jo.put("balance", c.getCustomerBalance());
			jo.put("cplid", cplid);
			return jo.toJSONString();
		}
		return jo.toJSONString();
	}

	@Override
	public String getDrawMeter(int mid) {
		List<MeterDereadMonth> list = meterDeductionLogDao.getMeterDeread(mid);
		JSONObject jo = new JSONObject();
		JSONArray ja_n = new JSONArray();
		for(int i = 0;i < 12;i++){
			ja_n.add(i, 0);
		}
		MeterDereadMonth meterDereadMonth = null;
		for(int i = 0;i < list.size();i++){
			meterDereadMonth = list.get(i);
			ja_n.set(meterDereadMonth.getMonth()-1, meterDereadMonth.getMeterread());
		}
		jo.put("yl", ja_n);
		return jo.toJSONString();
	}

	@Override
	public String updateDeread(int m_id, int deread) {
		
		return meterDao.updateDeread(m_id, deread)+"";
	}
	
}
