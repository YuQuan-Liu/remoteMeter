package com.xdkj.yccb.main.adminor.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.adminor.dao.BasicpriceDAO;
import com.xdkj.yccb.main.adminor.dao.PriceKindDAO;
import com.xdkj.yccb.main.adminor.dto.BasicpriceValues;
import com.xdkj.yccb.main.adminor.dto.BasicpriceView;
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
	public String addPriceKind(Pricekind pk,BasicpriceValues bpv) {
		//滞纳金默认0
		pk.setPriceKindFine(0);
		int pkId = priceKindDAO.addPriceKind(pk);
		//添加基本单价逻辑处理
		String[] basicPriceName = bpv.getBasicPriceName().split(",");
		String[] basicPriceFirst = bpv.getBasicFirstOver().split(",");
		String[] basicFirstOver = bpv.getBasicFirstOver().split(",");
		String[] basicPriceSecond = bpv.getBasicPriceSecond().split(",");
		String[] basicSecondOver = bpv.getBasicSecondOver().split(",");
		String[] basicPriceThird = bpv.getBasicPriceThird().split(",");
		for (int i = 0; i < basicPriceName.length; i++) {
			Basicprice bp = new Basicprice();
			bp.setBasicPriceName(basicPriceName[i]);
			bp.setBasicPriceFirst(new BigDecimal(basicPriceFirst[i]));
			bp.setBasicFirstOver(Integer.parseInt(basicFirstOver[i]));
			bp.setBasicPriceSecond(new BigDecimal(basicPriceSecond[i]));
			bp.setBasicSecondOver(Integer.parseInt(basicSecondOver[i]));
			bp.setBasicPriceThird(new BigDecimal(basicPriceThird[i]));
			bp.setPricekind(pk);
			bp.setValid("1");
			basicpriceDAO.addBasicprice(bp);
		}
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

	@Override
	public List<BasicpriceView> getListByPriceKindId(int priceKindId) {
		List<Basicprice> list = basicpriceDAO.getListByPriceKindId(priceKindId);
		List<BasicpriceView> listView = new ArrayList<BasicpriceView>();
		for (Basicprice bp : list) {
			BasicpriceView bpv = new BasicpriceView();
			bpv.setPid(bp.getPid());
			bpv.setBasicPriceName(bp.getBasicPriceName());
			bpv.setBasicPriceFirst(bp.getBasicPriceFirst());
			bpv.setBasicFirstOver(bp.getBasicFirstOver());
			bpv.setBasicPriceSecond(bp.getBasicPriceSecond());
			bpv.setBasicSecondOver(bp.getBasicSecondOver());
			bpv.setBasicPriceThird(bp.getBasicPriceThird());
			bpv.setRemark(bp.getRemark());
			listView.add(bpv);
		}
		list = null;
		return listView;
	}

	@Override
	public Pricekind getById(int pid) {
		return priceKindDAO.getById(pid);
	}

}
