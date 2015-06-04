package com.xdkj.yccb.main.infoin.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dto.CustomerMeter;
import com.xdkj.yccb.main.infoin.dto.CustomerView;

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
	int updatePrePaySign(int custId,byte prepaySign);
	
	/**
	 * 添加用户
	 * @param c
	 * @return
	 */
	public int addCustomer(Customer c);
	public Customer getCustomerByPid(int cid);
	public int deleteCustomer(int cid);
	/**
	 * 用户信息中的更新用户   
	 * @param c
	 * @return
	 */
	public int updateCustomer(Customer c);
	
	/**
	 * 收费界面更新用户资料
	 * @param c
	 * @return
	 */
	public int updateCustomerInfo(CustomerView cv);
	
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
	
	int updateCustomerBalance(BigDecimal b,Integer custId);
	/**
	 * 获取用户的欠费和提醒次数情况
	 * @param n_id
	 * @param pre
	 * @param low
	 * @return
	 */
	List<ControlWarnView> getCustomerOwe(int n_id, int pre, double low);
	List<ControlWarnView> getCustomerOwe(int n_id, String lou, int pre, double low);
	List<ControlWarnView> getCustomerOwe(int n_id, String lou, String dy,
			int pre, double low);

}
