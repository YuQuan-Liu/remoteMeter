package com.xdkj.yccb.main.infoin.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.CustomerMeter;

public interface CustomerDao {
	
	/*
	 * 根据小区ID  查出小区下的全部用户信息
	 */
	List<Customer> getCustomerList(String n_id);
	/*
	 * 根据用户在数据库里的唯一标示 查询用户
	 */
	List<Customer> getCustomerListByID(String c_identify);
	/*
	 * 根据小区 楼  单元  户 查询用户
	 */
	List<Customer> getCustomerListByNLDH(String n_id, String lou, String dy,String hu);
	/*
	 * 根据用户pid获取用户下的表具
	 */
	List<Meter> getMeterListByCid(String cpid);
	/**
	 * 预后付费转换单独更新方法
	 * @param custId
	 * @param prepaySign
	 */
	void updatePrePaySign(int custId,byte prepaySign);
	
	/**
	 * 添加用户
	 * @param c
	 * @return
	 */
	public int addCustomer(Customer c);
	public Customer getCustomerByPid(int cid);
	public int deleteCustomer(int cid);
	public int updateCustomer(Customer c);
	/**
	 * 添加表
	 * @param c
	 * @return
	 */
	public int addMeter(Meter m);
	public Meter getMeterByPid(int mid);
	public int deleteMeter(int cid);
	public int updateMeter(Meter m);
	/**
	 * 通过小区id 用户id或用户号查询用户信息
	 * @param nbrId 小区id
	 * @param cust 用户号或用户id
	 * @return
	 */
	Customer getCustByNborOrCust(int nbrId,String cust);
	/**
	 * eg协议  根据集中器  采集器 表地址 获取表
	 * @param pid
	 * @param collectorAddr
	 * @param meterAddr
	 * @return
	 */
	Meter getMeterByGCM(int pid, String collectorAddr, String meterAddr);
	/**
	 * 188协议表  根据表地址获取表
	 * @param meterAddr
	 * @return
	 */
	Meter getMeterByMAddr(String meterAddr);
	
	void updateCustomerBalance(BigDecimal b,Integer custId);

}
