package com.xdkj.yccb.main.charge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdkj.yccb.main.charge.service.ChargeService;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
@Service
public class ChargeServiceImpl implements ChargeService {
	@Autowired
	private CustomerDao custDAO;

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
			custDAO.updatePrePaySign(Integer.parseInt(cstId), Byte.parseByte(prePaySign));
			j.put("update", 1);
		} catch (Exception e) {
			j.put("update", 0);
		}
		return j.toJSONString();
	}

}
