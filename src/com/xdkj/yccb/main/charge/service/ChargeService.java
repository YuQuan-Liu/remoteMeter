package com.xdkj.yccb.main.charge.service;

import com.xdkj.yccb.main.infoin.dto.CustomerView;

public interface ChargeService {
	/**
	 * 通过小区id和用户id查询用户信息
	 * @param nbrId 小区id
	 * @param custId 用户信息
	 * @return 用户bean
	 */
	CustomerView getCustByNeibourAndCustId(String nbrId,String custId);
	/**
	 * 预后付费转换
	 * @param cstId
	 * @param prePaySign
	 * @return
	 */
	String updatePayment(String cstId,String prePaySign);

}
