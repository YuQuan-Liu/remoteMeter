package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Basicprice;

public interface BasicpriceDAO {
	List<Basicprice> getListByPriceKindId(int pkId);
	int addBasicprice(Basicprice bp);
	void delete(int bpId);

}
