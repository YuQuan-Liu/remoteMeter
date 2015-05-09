package com.xdkj.yccb.main.infoin.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Meter;

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
	 * 添加用户
	 * @param c
	 * @return
	 */
	public int addCustomer(Customer c);
	
	/**
	 * 添加表
	 * @param c
	 * @return
	 */
	public int addMeter(Meter m);

}