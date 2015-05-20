package com.xdkj.yccb.main.infoin.service;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.infoin.dto.CustomerView;
import com.xdkj.yccb.main.infoin.dto.MeterView;

public interface CustomerService {

	/*
	 * 根据小区  用户号  用户标识获取用户list
	 * 如果用户号c_num为空  取出小区下的所有用户
	 * 如果用户号c_num 没有被[ .,-]分隔    则用户号标识用户的唯一标识  使用唯一标识去查找用户    否则使用小区 楼 单元 户去查询
	 */
	public List<CustomerView> getCustomerby(String n_id, String c_num);
	
	/*
	 * 根据用户的pid 获取用户下的全部表具
	 */
	public List<MeterView> getMeterbyCid(String cpid);
	
	/**
	 * 根据传入的CustomerView 添加用户
	 * @param cv
	 * @return
	 */
	public Map<String, String> addCustomer(CustomerView cv);
	
	/**
	 * 根据传入的MeterView 添加用户
	 * @param mv
	 * @return
	 */
	public Map<String, String> addMeter(MeterView mv);
	
	/**
	 * 根据用户cid 删除用户
	 * @param cid
	 * @return
	 */
	public String deleteCustomer(int cid);
	
	public CustomerView getCustomerViewbyCid(int cid);

	public Map<String, String> updateCustomer(CustomerView cv);

	public String deleteMeter(int mid);

	public MeterView getMeterViewbyMid(int mid);

	public Map<String, String> updateMeter(MeterView mv);

}
