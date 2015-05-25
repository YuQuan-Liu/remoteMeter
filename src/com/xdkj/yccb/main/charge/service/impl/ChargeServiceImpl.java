package com.xdkj.yccb.main.charge.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.dao.CustompaylogDAO;
import com.xdkj.yccb.main.charge.dto.CustompaylogView;
import com.xdkj.yccb.main.charge.service.ChargeService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Customerpaylog;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
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

	@Override
	public CustomerView getCustByNeibourAndCustId(String nbrId, String custId) {
		if(null!=nbrId&&null!=custId){
			Customer c = custDAO.getCustByNborOrCust(Integer.parseInt(nbrId), custId);
			if(c!=null){
				CustomerView cv = new CustomerView();
				cv.setApid(c.getApid());
				cv.setCustomerAddr(c.getCustomerAddr());
				cv.setCustomerBalance(c.getCustomerBalance());
				cv.setCustomerEmail(c.getCustomerEmail());
				cv.setCustomerId(c.getCustomerId());
				cv.setCustomerMobile(c.getCustomerMobile());
				cv.setCustomerName(c.getCustomerName());
				cv.setHk_id(c.getHousekind().getPid()+"");
				cv.setPid(c.getPid());
				cv.setNationalId(c.getNationalId());
				cv.setC_num(c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
				cv.setN_name(c.getNeighbor().getNeighborName());
				cv.setWarnThre(c.getWarnThre());
				cv.setPrePaySign(c.getPrePaySign());
				cv.setWarnStyle(c.getWarnStyle());
				cv.setWarnSwitch(c.getWarnSwitch());
				return cv;
			}
		}
		return null;
		
	}

	@Override
	public String updatePayment(String cstId, String prePaySign) {
		JSONObject j = new JSONObject();
		try {
			byte p = 0;
			if("0".equals(prePaySign)){
				p = 1;
			}
			custDAO.updatePrePaySign(Integer.parseInt(cstId), p);
			j.put("update", 1);
		} catch (Exception e) {
			j.put("update", 0);
		}
		return j.toJSONString();
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
			mdlv.setActionTime(mdl.getActionTime());
			//mdlv.set
			listView.add(mdlv);
		}
		list = null;
		return listView;
	}

}
