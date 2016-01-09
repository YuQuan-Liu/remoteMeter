package com.xdkj.yccb.main.infoin.service;

import java.util.List;
import java.util.Map;

import com.xdkj.yccb.main.charge.dto.ControlWarnView;
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
	public Map<String, String> addCustomer(CustomerView cv,int adminid);
	
	/**
	 * 根据传入的MeterView 添加用户
	 * @param mv
	 * @return
	 */
	public Map<String, String> addMeter(int adminid,MeterView mv);
	
	/**
	 * 根据用户cid 删除用户
	 * @param cid
	 * @return
	 */
	public String deleteCustomer(int cid);
	
	public CustomerView getCustomerViewbyCid(int cid);

	/**
	 * 用户表信息里面的更新用户   各种信息都可以更新
	 * @param cv
	 * @return
	 */
	public String updateCustomer(CustomerView cv);

	/**
	 * 更新用户的  姓名  手机  邮箱  身份证
	 * @param cv
	 * @return
	 */
	public String updateCustomerInfo(CustomerView cv);
	
	public String deleteMeter(int mid);

	public MeterView getMeterViewbyMid(int mid);

	public String updateMeter(int adminid,MeterView mv);

	/**
	 * map 中包含上传excel的所有的数据
	 * @param map
	 */
	public Map addCustomers(Map map);

	public List<ControlWarnView> getOwes(int n_id, String lou, String dy, int pre, double low);

	/**
	 * 检测数据库中是否已经包含此用户的关联ID
	 * @param c_apid
	 * @return
	 */
	public String check_capid(String c_apid);

	/**
	 * 检测数据库中是否已经包含此表的关联ID
	 * @param m_apid
	 * @return
	 */
	public String check_mapid(String m_apid);

	/**
	 * 检测集中器下是否已经有表地址了
	 * @param maddr
	 * @param caddr
	 * @param gprs_id
	 * @return
	 */
	public String check_maddr(String maddr, String caddr, int gprs_id);

	/**
	 * 换表
	 * @param new_maddr
	 * @param new_read
	 * @param meterid
	 * @return
	 */
	public String changemeter(String new_maddr, int end, int meterid);

	/**
	 * 根据用户的手机号和密码 获取用户
	 * @param mobile
	 * @param encodePassword
	 * @return
	 */
	public Customer getByMobile(String mobile, String encodePassword);

	/**
	 * 调整表具  将集中器、采集器、表对应的表   对应到customerid下。   将当前表扣的所有的费用加回去，并在customerid下扣掉
	 * @param adminid   用于保存记录
	 * @param gaddr
	 * @param caddr
	 * @param maddr
	 * @param customerid
	 * @return
	 */
	public String adjustmeter(int adminid,String gaddr, String caddr, String maddr,
			int customerid);

}
