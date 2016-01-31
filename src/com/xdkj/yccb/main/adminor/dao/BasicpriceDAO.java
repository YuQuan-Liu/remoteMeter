package com.xdkj.yccb.main.adminor.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xdkj.yccb.main.entity.Basicprice;

public interface BasicpriceDAO {
	List<Basicprice> getListByPriceKindId(int pkId);
	int addBasicprice(Basicprice bp);
	void delete(int bpId);
	
	/**
	 * 获取pricekind下面一阶单价的和
	 * @param pid
	 * @return
	 */
	BigDecimal getFirstSum(int pkid);

}
