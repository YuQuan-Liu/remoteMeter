package com.xdkj.yccb.main.statistics.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Meterdeductionlog;
import com.xdkj.yccb.main.statistics.dao.MeterDeductionLogDao;
@Repository
public class MeterDeductionLogImpl extends HibernateDAO<Meterdeductionlog> implements MeterDeductionLogDao {

	@Override
	public List<Meterdeductionlog> getMeterDeductionLog(String id) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Meterdeductionlog> getList(int count, int custId) {
		String hql = "from Meterdeductionlog mdl where mdl.meter.customer.pid=:pid order by mdl.actionTime desc";
		return getSession().createQuery(hql).setParameter("pid", custId)
				.setFirstResult(0).setMaxResults(count).list();
	}

	@Override
	public Meterdeductionlog getById(Integer mdlId) {
		return this.getById(Meterdeductionlog.class, mdlId);
	}

	@Override
	public void updateMeterductionLog(Meterdeductionlog mdl) {
		this.updateBase(mdl);
	}

}
