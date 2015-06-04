package com.xdkj.yccb.main.charge.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.util.JsonUtil;
import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.charge.dto.CustompaylogView;
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
	public List<CustompaylogView> getCList(String custId, int count) {
		List<Customerpaylog> list = custompaylogDAO.getList(count, Integer.parseInt(custId));
		List<CustompaylogView> listView = new ArrayList<CustompaylogView>();
		for (Customerpaylog cpl : list) {
			Customer c = cpl.getCustomer();
			CustompaylogView cplv = new CustompaylogView();
			cplv.setActionTime(cpl.getActionTime());
			cplv.setAdminName(cpl.getAdmininfo().getAdminName());
			cplv.setAmount(cpl.getAmount());
			cplv.setCustId(c.getCustomerId());
			cplv.setCustName(c.getCustomerName());
			cplv.setCustAddr(c.getCustomerAddr());
			cplv.setCustNo(c.getPid()+"");//这个取。。。
			cplv.setPid(cpl.getPid());
			cplv.setPrePaySign(cpl.getPrePaySign());
			cplv.setRemark(cpl.getRemark());
			listView.add(cplv);
		}
		list = null;
		return listView;
	}

	@Override
	public List<MeterdeductionlogView> getMList(String custId, int count) {
		List<Meterdeductionlog> list = meterDeductionLogDao.getList(count, Integer.parseInt(custId));
		List<MeterdeductionlogView> listView = new ArrayList<MeterdeductionlogView>();
		for (Meterdeductionlog mdl : list) {
			MeterdeductionlogView mdlv = new MeterdeductionlogView();
			Meter m = mdl.getMeter();
			mdlv.setActionTime(mdl.getActionTime());
			mdlv.setCollectaddr(m.getCollectorAddr());
			mdlv.setDeMoney(mdl.getDeMoney());
			mdlv.setGprsAddr(m.getGprs().getGprsaddr());
			mdlv.setLastDeRead(mdl.getLastDeRead());
			mdlv.setLastDeTime(mdl.getLastDeTime());
			mdlv.setMeterAddr(m.getMeterAddr());
			mdlv.setMeterRead(mdl.getMeterRead());
			mdlv.setMeterReadTime(mdl.getMeterReadTime());
			mdlv.setPid(mdl.getPid());
			m = null;
			listView.add(mdlv);
		}
		list = null;
		return listView;
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
			custDAO.updateCustomerBalance(pay, custId);
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
		if(mdl.getValid()=='1'){
			int custId = mdl.getMeter().getCustomer().getPid();
			mdl.setValid('0');
			meterDeductionLogDao.updateMeterductionLog(mdl);
			BigDecimal pay = mdl.getDeMoney();
			//将交给额加至Customer 余额 CustomerBalance
			custDAO.updateCustomerBalance(pay, custId);
			//插入操作记录
			
		}
		j.put("state", "succ");
		return j.toJSONString();
	}

}
