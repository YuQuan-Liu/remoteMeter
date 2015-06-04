package com.xdkj.yccb.main.infoin.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.xdkj.yccb.main.adminor.dao.MeterKindDao;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Housekind;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Meterkind;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.entity.Pricekind;
import com.xdkj.yccb.main.infoin.dao.CustomerDao;
import com.xdkj.yccb.main.infoin.dao.GprsDAO;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.CustomerMeter;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;
import com.xdkj.yccb.main.infoin.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private NeighborDAO neighborDAO;
	@Autowired
	private GprsDAO gprsDAO;
	@Autowired
	private MeterKindDao meterKindDao;
	
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
	
	@Override
	public List<MeterView> getMeterbyCid(String cpid) {
		List<Meter> list = customerDao.getMeterListByCid(cpid);
		if(list.size() == 0){
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
			//类有对象引用的gc不会及时回收 最好手动释放
			list = null;
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
			c.setLoginName("");
			c.setLoginKey("96e79218965eb72c92a549dd5a330112");
			c.setValid("1");
			c.setCustomerId("0");
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
			
			Customer c = customerDao.getCustomerByPid(mv.getC_id());
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
			m.setValveState((byte)1);
			m.setMeterState((byte)1);
			m.setDeRead(0);
			m.setChangend(0);
			m.setChangestart(0);
			m.setReaddata(0);
			m.setReadtime(new Date());
			
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
			
			//这个地方不可以使用 这中copy  ！！！可能会把不该更新的也更新了。  TODO  
			//需要更改哪些属性  就直接  赋值哪些属性
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
			//TODO
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
	public String updateCustomerInfo(CustomerView cv) {
//		Customer c = customerDao.getCustomerByPid(cv.getPid());
//		c.setCustomerMobile(cv.getCustomerMobile());
//		c.setCustomerEmail(cv.getCustomerEmail());
//		c.setNationalId(cv.getNationalId());
//		c.setCustomerName(cv.getCustomerName());
		//上面直接更新到数据库了
		
		if(customerDao.updateCustomerInfo(cv) > 0){
			return "1";
		}
		return "0";
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
	@Override
	public Map addCustomers(Map map) {
		String n_name = (String) map.get("n_name");
		String g_addr = (String) map.get("g_addr");
		String wcid = map.get("wcid")+"";
		List<CustomerMeter> list = (List<CustomerMeter>) map.get("list");
		
		Map result = new HashMap();
		Neighbor n = neighborDAO.getNbrByWcIdName(wcid,n_name);
		if(n == null){
			result.put("success", false);
			result.put("reason", "请录入小区"+n_name);
			return result;
		}
		Gprs g = gprsDAO.getByAddr(g_addr);
		if(g == null){
			result.put("success", false);
			result.put("reason", "请录入集中器"+g_addr);
			return result;
		}
		if(list == null){
			result.put("success", false);
			result.put("reason", "请输入数据");
			return result;
		}
		
		Customer c = null;
		CustomerMeter cm = null;
		for(int i = 0;i < list.size();i++){
			cm = list.get(i);
			
			List<Customer> clist = customerDao.getCustomerListByNLDH(n.getPid()+"",cm.getLouNum(),cm.getDyNum(),cm.getHuNum());
			if(clist.size() == 0){
				//全新的用户 全新的表
				c = new Customer();
				try {
					ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);
					BeanUtils.copyProperties(c, cm);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				//customer
				Housekind hk = new Housekind();
				hk.setPid(cm.getHkid());
				c.setHousekind(hk);
				c.setLoginName("");
				c.setLoginKey("96e79218965eb72c92a549dd5a330112");
				c.setValid("1");
				c.setNeighbor(n);
				c.setApid(cm.getC_apid());
				c.setCustomerId("..");
				customerDao.addCustomer(c);
				
				addMeter(c, cm, n, g);
			}else{
				//Oh  已经有这个用户了
				c = clist.get(0);
				List<Meter> mlist = customerDao.getMeterListByCid(c.getPid()+"");
				boolean done = false;
				if(mlist.size() == 0){
					//new
					done = false;
				}else{
					//got one in db
					//check the meter
					if(cm.getCollectorAddr().equals("0")){
						//手抄
						for(int j = 0;j < mlist.size();j++){
							if(mlist.get(j).getMeterAddr().equalsIgnoreCase(cm.getMeterAddr())){
								done = true;
								break;
							}
						}
					}else{
						//远传
						switch (g.getGprsprotocol()) {
						case 1:
							//eg
							for(int j = 0;j < mlist.size();j++){
//								m = mlist.get(j);
								if(null != customerDao.getMeterByGCM(g.getPid(),cm.getCollectorAddr(),cm.getMeterAddr())){
									done = true;
									break;
								}
							}
							break;
						case 2:
							//188
							for(int j = 0;j < mlist.size();j++){
//								m = mlist.get(j);
								if(null != customerDao.getMeterByMAddr(cm.getMeterAddr())){
									done = true;
									break;
								}
							}
							break;
						default:
							break;
						}
					}
				}
				if(!done){
					//add the meter
					addMeter(c, cm, n, g);
				}
			}
		}
		result.put("success", true);
		result.put("reason", "");
		return result;
	}

	private void addMeter(Customer c, CustomerMeter cm, Neighbor n, Gprs g){
		Meter m = new Meter();
		try {
			BeanUtils.copyProperties(m, cm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//meter
		Pricekind pk = new Pricekind();
		pk.setPid(cm.getPkid());
		m.setPricekind(pk);
		m.setCustomer(c);
		m.setGprs(g);
		
		Meterkind mk = new Meterkind();
		mk.setPid(cm.getMkid());
		m.setMeterkind(mk);

		m.setNeighbor(n);
		m.setValid('1');
		m.setDeTime(new Date());
		m.setNeighbor(c.getNeighbor());
		m.setValid('1');
		m.setDeTime(new Date());
		m.setValveState((byte)1);
		m.setMeterState((byte)1);
		m.setDeRead(0);
		m.setChangend(0);
		m.setChangestart(0);
		m.setReaddata(0);
		m.setReadtime(new Date());
		m.setApid(cm.getM_apid());
		
		customerDao.addMeter(m);
	}
	@Override
	public List<ControlWarnView> getOwes(int n_id, String lou, String dy,
			int pre, double low) {
		if(lou.equals("")){
			//小区
			return customerDao.getCustomerOwe(n_id,pre,low);
		}
		
		if(dy.equals("")){
			//楼
			return customerDao.getCustomerOwe(n_id,lou,pre,low);
		}
		
		//单元
		return customerDao.getCustomerOwe(n_id,lou,dy,pre,low);
		
	}

	
	
}
