package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.BasicpriceDAO;
import com.xdkj.yccb.main.adminor.dao.PriceKindDAO;
import com.xdkj.yccb.main.adminor.dto.PriceKindView;
import com.xdkj.yccb.main.adminor.service.PriceService;
import com.xdkj.yccb.main.entity.Basicprice;
import com.xdkj.yccb.main.entity.Pricekind;
@Service
public class PriceServiceImpl implements PriceService {
	@Autowired
	private PriceKindDAO priceKindDAO;
	@Autowired
	private BasicpriceDAO basicpriceDAO;

	@Override
	public List<PriceKindView> getList(PriceKindView pkv, PageBase pb) {
		List<Pricekind> list = priceKindDAO.getList(pkv, pb);
		List<PriceKindView> listView = new ArrayList<PriceKindView>();
		for (Pricekind pk0 : list) {
			PriceKindView pkv0 = new PriceKindView();
			pkv0.setPid(pk0.getPid());
			pkv0.setPriceKindFine(pk0.getPriceKindFine());
			pkv0.setPriceKindName(pk0.getPriceKindName());
			pkv0.setRemark(pk0.getRemark());
			pkv0.setValid(pk0.getValid());
			pkv0.setWatercompany(pk0.getWatercompany().getCompanyName());
			listView.add(pkv0);
		}
		list=null;
		return listView;
	}
	
	public List<PriceKindView> getList(int wcid) {
		List<Pricekind> list = priceKindDAO.getList(wcid);
		List<PriceKindView> listView = new ArrayList<PriceKindView>();

		PriceKindView pkv = null;
		for (Pricekind pk : list) {
			pkv = new PriceKindView();
			
			try {
				BeanUtils.copyProperties(pkv, pk);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			listView.add(pkv);
		}
		return listView;
	}
	
	@Override
	public int getTotalCount(PriceKindView pkv) {
		return priceKindDAO.getTotalCount(pkv);
	}

	@Override
	public String addPriceKind(Pricekind pk) {
		int pkId = priceKindDAO.addPriceKind(pk);
		if(pkId>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String updatePriceKind(Pricekind pk) {
		priceKindDAO.update(pk);
		return "succ";
	}

	@Override
	public String addBasicPrice(Basicprice bp) {
		int pid = basicpriceDAO.addBasicprice(bp);
		if(pid>0){
			return "succ";
		}
		return "fail";
	}

	@Override
	public String deleteBasicPrice(String bpId) {
		if(null!=bpId&&!"".equals(bpId)){
			basicpriceDAO.delete(Integer.parseInt(bpId));
		}
		return "succ";
	}

}
