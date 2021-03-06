package com.xdkj.yccb.main.charge.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.charge.dao.ControlWarnDao;
import com.xdkj.yccb.main.charge.dto.ControlWarnView;
import com.xdkj.yccb.main.charge.dto.WarnPostPay;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Warnlog;
import com.xdkj.yccb.main.readme.dto.ReadView;

@Repository
public class ControlWarnDaoImpl extends HibernateDAO implements ControlWarnDao {

	@Override
	public List<ControlWarnView> getControlWarns(int n_id) {
		Query q = getSession().createSQLQuery("select c.pid c_id,m.pid m_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.customerId,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.customerEmail,c.CustomerBalance,c.warnThre," +
				"c.warnStyle,g.GPRSAddr g_addr, m.collectorAddr,m.meterAddr,m.isValve,m.valveState,m.meterState,maxwarn.warnCount from customer c " +
				"left join meter m " +
				"on c.pid = m.customerid " +
				"left join gprs g " +
				"on m.gprsid = g.pid " +
				"left join ( " +
				"select max(pid),customerid,count(*) warncount from warnlog " +
				"where valid = 1 and successCount > 0 " +
				"group by customerid " +
				") maxwarn " +
				"on maxwarn.customerid = c.pid " +
				//"left join warnlog warn " +
				//"on maxwarn.customerid = warn.customerid " +
				"where c.neighborid = "+n_id+" and c.valid = 1 and m.valid = 1 " +
				"order by length(lounum),lounum,DYNum,length(HuNum),HuNum")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerId",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerEmail",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("warnThre",Hibernate.INTEGER)
				.addScalar("warnStyle",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("isValve",Hibernate.INTEGER)
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("warnCount",Hibernate.STRING);

		
		q.setResultTransformer(Transformers.aliasToBean(ControlWarnView.class));
		
		return q.list();
	}

	@Override
	public void addWarnLog(Customer c, boolean done,String failReason,String cid) {
		
		Warnlog warnlog = new Warnlog();
		warnlog.setActionTime(new Date());
		warnlog.setCustomer(c);
		warnlog.setEmail(c.getCustomerEmail());
		warnlog.setMobile(c.getCustomerMobile());
		if(done){
			warnlog.setFailCount(0);
			warnlog.setSuccessCount(1);
		}else{
			warnlog.setFailCount(1);
			warnlog.setSuccessCount(0);
		}
		warnlog.setValid('1');
		warnlog.setWarn(0);
		warnlog.setWarnContent(c.getCustomerName()+c.getCustomerBalance());
		warnlog.setWarnCount(1);
		warnlog.setWarnReason(failReason);
		warnlog.setWarnStyle(c.getWarnStyle());
		warnlog.setSmscid(cid);
		
		getHibernateTemplate().save(warnlog);
		
	}
	
	@Override
	public void addWarnLog(WarnPostPay warnPostPay, boolean done,
			String failReason) {
		Warnlog warnlog = new Warnlog();
		warnlog.setActionTime(new Date());
		Customer c = new Customer();
		c.setPid(warnPostPay.getC_id());
		warnlog.setCustomer(c);
		warnlog.setEmail("");
		warnlog.setMobile(warnPostPay.getCustomerMobile());
		if(done){
			warnlog.setFailCount(0);
			warnlog.setSuccessCount(1);
		}else{
			warnlog.setFailCount(1);
			warnlog.setSuccessCount(0);
		}
		warnlog.setValid('1');
		warnlog.setWarn(0);
		warnlog.setWarnContent(warnPostPay.getCustomerName()+warnPostPay.getDemoney());
		warnlog.setWarnCount(1);
		warnlog.setWarnReason(failReason);
		warnlog.setWarnStyle(1);
		warnlog.setSmscid("");
		
		getHibernateTemplate().save(warnlog);
		
	}
	
	@Override
	public boolean todaySend(String mobile) {
		/**
		 * 查询一个手机号码今天是否可以发送     >3次  不可发送
		 * @param mobile
		 * @return
		 */
		Query q = getSession().createSQLQuery("select count(*) from warnlog " +
				"where mobile = :mobile and actiontime > curdate() ")
				.setString("mobile", mobile);

		int times = ((BigInteger)q.uniqueResult()).intValue();
		
		if(times > 1){
			return false;
		}
		return true;
	}

	@Override
	public void addWarnLog(String mobile, boolean done, String failReason,
			String cid) {
		Warnlog warnlog = new Warnlog();
		warnlog.setActionTime(new Date());
		warnlog.setCustomer(null);
		warnlog.setEmail("");
		warnlog.setMobile(mobile);
		if(done){
			warnlog.setFailCount(0);
			warnlog.setSuccessCount(1);
		}else{
			warnlog.setFailCount(1);
			warnlog.setSuccessCount(0);
		}
		warnlog.setValid('1');
		warnlog.setWarn(0);
		warnlog.setWarnContent("");
		warnlog.setWarnCount(1);
		warnlog.setWarnReason(failReason);
		warnlog.setWarnStyle(1);
		warnlog.setSmscid(cid);
		
		getHibernateTemplate().save(warnlog);
		
	}

}
