package com.xdkj.yccb.main.infoin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Housekind;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterkind;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Pricekind;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Override
	public List<CustomerView> getCustomerby(String n_id, String c_num) {
		c_num = c_num.trim();
		List<Customer> list = null;
		if(c_num.equals("")){
			// all the customer under the neighbor
			list = customerDao.getCustomerList(n_id);
		}else{
			String[] ldh = c_num.split("[ .,-]");
			if(ldh.length == 1){
				//根据用户的唯一标示 查询
				list = customerDao.getCustomerListByID(c_num);
			}else{
				if(ldh.length == 3){
					//根据小区 楼 单元 户查询
					list = customerDao.getCustomerListByNLDH(n_id,ldh[0],ldh[1],ldh[2]);
				}
			}
		}
		if(list == null){
			return null;
		}else{
			List<CustomerView> listView = new ArrayList<>();
			CustomerView cv = null;
			for(Customer c:list){
				cv = new CustomerView();
				try {
					BeanUtils.copyProperties(cv, c);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				cv.setN_name(c.getNeighbor().getNeighborName());
				cv.setHk(c.getHousekind().getHkname());
				cv.setC_num(c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
				listView.add(cv);
			}
			return listView;
		}
		
	}
	@Override
	public List<MeterView> getMeterbyCid(String cpid) {
		List<Meter> list = customerDao.getMeterListByCid(cpid);
		if(null == list){
			return null;
		}else{
			List<MeterView> listView = new ArrayList<>();
			MeterView mv = null;
			for(Meter m:list){
				mv = new MeterView();
				try {
					BeanUtils.copyProperties(mv, m);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				mv.setGprs(m.getGprs().getGprsaddr());
				mv.setPk(m.getPricekind().getPriceKindName());
				mv.setMk(m.getMeterkind().getMeterTypeName());
				listView.add(mv);
			}
			
			return listView;
		}
	}
	@Override
	public Map<String, String> addCustomer(CustomerView cv) {
		
		//check CustomerView 
		Map<String, String> map = cv.check_view();
		if(map.get("success").equals("true")){
			Customer c = new Customer();
			
			try {
				ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
				BeanUtils.copyProperties(c, cv);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			Neighbor n = new Neighbor();
			n.setPid(Integer.parseInt(cv.getN_id()));
			c.setNeighbor(n);
			
			Housekind hk = new Housekind();
			hk.setPid(Integer.parseInt(cv.getHk_id()));
			c.setHousekind(hk);
			c.setLoginKey("96e79218965eb72c92a549dd5a330112");
			c.setValid("1");
			if(customerDao.addCustomer(c) > 0){
				map.put("add", c.getPid()+"");
			}else{
				map.put("add", "0");
			}
		}
		
		return map;
	}
	@Override
	public Map<String, String> addMeter(MeterView mv) {
		//check meterview
		Map<String, String> map = mv.check_view();
		if(map.get("success").equals("true")){
			Meter m = new Meter();
			try {
				BeanUtils.copyProperties(m, mv);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			Pricekind pk = new Pricekind();
			pk.setPid(Integer.valueOf(mv.getPk_id()));
			m.setPricekind(pk);
			
			Customer c = new Customer();
			c.setPid(mv.getC_id());
			m.setCustomer(c);
			
			Gprs g = new Gprs();
			g.setPid(Integer.valueOf(mv.getGprs_id()));
			m.setGprs(g);
			
			Meterkind mk = new Meterkind();
			mk.setPid(Integer.parseInt(mv.getMk_id()));
			m.setMeterkind(mk);
			
			m.setNeighbor(c.getNeighbor());
			m.setValid('1');
			m.setDeTime(new Date());
			
			if(customerDao.addMeter(m) > 0){
				map.put("add", m.getPid()+"");
			}else{
				map.put("add", "0");
			}
		}
		
		
		return map;
	}
	@Override
	public String deleteCustomer(int cid) {
		if(customerDao.deleteCustomer(cid) > 0){
			return "true";
		}else{
			return "false";
		}
	}
	@Override
	public CustomerView getCustomerViewbyCid(int cid) {
		
		Customer c = customerDao.getCustomerByPid(cid);
		CustomerView cv = new CustomerView();
		try {
			BeanUtils.copyProperties(cv, c);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		cv.setN_name(c.getNeighbor().getNeighborName());
		cv.setHk(c.getHousekind().getHkname());
		cv.setHk_id(c.getHousekind().getPid()+"");
		cv.setC_num(c.getLouNum()+"-"+c.getDyNum()+"-"+c.getHuNum());
		
		return cv;
	}
	@Override
	public Map<String, String> updateCustomer(CustomerView cv) {
		// check CustomerView
		Map<String, String> map = cv.check_view();
		if (map.get("success").equals("true")) {
			Customer c = customerDao.getCustomerByPid(cv.getPid());
			try {
				ConvertUtils.register(new BigDecimalConverter(),
						BigDecimal.class);
				BeanUtils.copyProperties(c, cv);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//住宅類型不更改
			//金額不允許更改
			if (customerDao.updateCustomer(c) > 0) {
				map.put("update", c.getPid() + "");
//				map.put("cv", JSON.toJSONString(cv));
			} else {
				map.put("update", "0");
			}
		}

		return map;
	}
	@Override
	public String deleteMeter(int mid) {
		if(customerDao.deleteMeter(mid) > 0){
			return "true";
		}else{
			return "false";
		}
	}
	@Override
	public MeterView getMeterViewbyMid(int mid) {
		
		Meter m = customerDao.getMeterByPid(mid);
		MeterView mv = new MeterView();
		try {
			BeanUtils.copyProperties(mv, m);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		mv.setGprs(m.getGprs().getGprsaddr());
		mv.setGprs_id(m.getGprs().getPid()+"");
		mv.setPk(m.getPricekind().getPriceKindName());
		mv.setPk_id(m.getPricekind().getPid()+"");
		mv.setMk(m.getMeterkind().getMeterTypeName());
		mv.setMk_id(m.getMeterkind().getPid()+"");
//		System.out.println(m.getCustomer().getPid());
		mv.setC_id(m.getCustomer().getPid());
		return mv;
	}
	@Override
	public Map<String, String> updateMeter(MeterView mv) {
		// check CustomerView
		Map<String, String> map = mv.check_view();
		if (map.get("success").equals("true")) {
			Meter m = customerDao.getMeterByPid(mv.getPid());
			try {
				BeanUtils.copyProperties(m, mv);
				//可以更新那些程序  就copy那些 属性  //TODO
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			// 住宅類型不更改
			// 金額不允許更改
			if (customerDao.updateMeter(m) > 0) {
				map.put("update", m.getPid() + "");
				// map.put("cv", JSON.toJSONString(cv));
			} else {
				map.put("update", "0");
			}
		}

		return map;
	}

}
