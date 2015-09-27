package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.Gprs;
import com.xdkj.yccb.main.entity.Meter;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.readme.dao.ReadDao;
import com.xdkj.yccb.main.readme.dto.ReadView;

@Repository
public class ReadDaoImpl extends HibernateDAO implements ReadDao{

	@Override
	public List<ReadView> getRemoteMeters(String n_id) {
		
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.CustomerName,c.customerAddr,c.prePaySign,c.CustomerMobile,c.CustomerBalance, " +
				"m.pid m_id,m.apid m_apid,m.CollectorAddr,m.MeterAddr,m.ValveState,m.MeterState,m.deread,m.readdata,m.readtime, " +
				"mk.MeterTypeName,mk.Remote, " +
				"n.pid n_id,n.NeighborName n_name,g.pid g_id,g.GPRSAddr g_addr from customer c " +
				"left join meter m " +
				"on c.pid = m.CustomerID " +
				"left join neighbor n " +
				"on n.pid = c.neighborid " +
				"left join gprs g " +
				"on g.pid = m.GPRSID " +
				"left join MeterKind mk " +
				"on mk.pid = m.meterkindid " +
				"where c.NeighborID = "+n_id+" and c.valid !=0 and m.valid != 0 and mk.remote = 1 " +
				"order by length(lounum),lounum,DYNum,length(HuNum),HuNum")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("m_apid",Hibernate.STRING)
				.addScalar("n_name",Hibernate.STRING)
				.addScalar("n_id",Hibernate.INTEGER)
				.addScalar("g_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("meterTypeName",Hibernate.STRING)
				.addScalar("remote",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING)
				.addScalar("meterState",Hibernate.BYTE);

		
		q.setResultTransformer(Transformers.aliasToBean(ReadView.class));
		
		return q.list();
	}

	@Override
	public List<ReadView> getNonRemoteMeters(String n_id) {
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.CustomerName,c.prePaySign,c.CustomerMobile,c.CustomerBalance, " +
				"m.pid m_id,m.apid m_apid,m.CollectorAddr,m.MeterAddr,m.ValveState,m.MeterState,m.deread,m.readdata,m.readtime, " +
				"mk.MeterTypeName,mk.Remote, " +
				"n.pid n_id,n.NeighborName n_name,g.pid g_id,g.GPRSAddr g_addr from customer c " +
				"left join meter m " +
				"on c.pid = m.CustomerID " +
				"left join neighbor n " +
				"on n.pid = c.neighborid " +
				"left join gprs g " +
				"on g.pid = m.GPRSID " +
				"left join meterkind mk " +
				"on mk.pid = m.meterkindid " +
				"where c.NeighborID = "+n_id+" and c.valid !=0 and m.valid != 0 and mk.remote = 0 " +
				"order by length(lounum),lounum,DYNum,length(HuNum),HuNum")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("m_apid",Hibernate.STRING)
				.addScalar("n_name",Hibernate.STRING)
				.addScalar("n_id",Hibernate.INTEGER)
				.addScalar("g_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("meterTypeName",Hibernate.STRING)
				.addScalar("remote",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING)
				.addScalar("meterState",Hibernate.BYTE);

		
		q.setResultTransformer(Transformers.aliasToBean(ReadView.class));
		
		return q.list();
	}

	@Override
	public List<ReadView> getAllRemoteMeters(List<Integer> nid_list) {
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.CustomerName,c.prePaySign,c.CustomerMobile,c.CustomerBalance,c.CustomerAddr, " +
				"m.pid m_id,m.apid m_apid,m.CollectorAddr,m.MeterAddr,m.ValveState,m.MeterState,m.deread,m.readdata,m.readtime,m.steelNum, " +
				"mk.MeterTypeName,mk.Remote, " +
				"n.pid n_id,n.NeighborName n_name,g.pid g_id,g.GPRSAddr g_addr from customer c " +
				"left join meter m " +
				"on c.pid = m.CustomerID " +
				"left join neighbor n " +
				"on n.pid = c.neighborid " +
				"left join gprs g " +
				"on g.pid = m.GPRSID " +
				"left join MeterKind mk " +
				"on mk.pid = m.meterkindid " +
				"where c.NeighborID in (:nid_list) and c.valid !=0 and m.valid != 0 and mk.remote = 1 " +
				"order by c.NeighborID,length(lounum),lounum,DYNum,length(HuNum),HuNum")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("m_apid",Hibernate.STRING)
				.addScalar("n_name",Hibernate.STRING)
				.addScalar("n_id",Hibernate.INTEGER)
				.addScalar("g_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerAddr",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("meterTypeName",Hibernate.STRING)
				.addScalar("remote",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING)
				.addScalar("meterState",Hibernate.BYTE)
				.addScalar("steelNum",Hibernate.STRING);

		
		q.setResultTransformer(Transformers.aliasToBean(ReadView.class));
		q.setParameterList("nid_list", nid_list);
		return q.list();
	}

	@Override
	public List<ReadView> getYT2Data(List<Integer> nid_list) {
		Query q = getSession().createSQLQuery("select c.pid c_id,concat(c.LouNum ,'-',c.DYNum ,'-',c.HuNum) c_num,c.CustomerName,c.prePaySign,c.CustomerMobile,c.CustomerBalance, " +
				"m.pid m_id,m.apid m_apid,m.CollectorAddr,m.MeterAddr,m.ValveState,m.MeterState,m.deread,m.readdata,m.readtime, " +
				"mk.MeterTypeName,mk.Remote, " +
				"n.pid n_id,n.NeighborName n_name,g.pid g_id,g.GPRSAddr g_addr from customer c " +
				"left join meter m " +
				"on c.pid = m.CustomerID " +
				"left join neighbor n " +
				"on n.pid = c.neighborid " +
				"left join gprs g " +
				"on g.pid = m.GPRSID " +
				"left join MeterKind mk " +
				"on mk.pid = m.meterkindid " +
				"where c.NeighborID in (:nid_list) and c.valid !=0 and m.valid != 0 and mk.remote = 1 and m.lihu = 1 " +
				"order by c.NeighborID,length(lounum),lounum,DYNum,length(HuNum),HuNum")
				.addScalar("c_id",Hibernate.INTEGER)
				.addScalar("m_id",Hibernate.INTEGER)
				.addScalar("m_apid",Hibernate.STRING)
				.addScalar("n_name",Hibernate.STRING)
				.addScalar("n_id",Hibernate.INTEGER)
				.addScalar("g_id",Hibernate.INTEGER)
				.addScalar("g_addr",Hibernate.STRING)
				.addScalar("c_num",Hibernate.STRING)
				.addScalar("customerName",Hibernate.STRING)
				.addScalar("customerMobile",Hibernate.STRING)
				.addScalar("customerBalance",Hibernate.BIG_DECIMAL)
				.addScalar("prePaySign",Hibernate.BYTE)
				.addScalar("meterTypeName",Hibernate.STRING)
				.addScalar("remote",Hibernate.INTEGER)
				.addScalar("collectorAddr",Hibernate.STRING)
				.addScalar("meterAddr",Hibernate.STRING)
				.addScalar("valveState",Hibernate.BYTE)
				.addScalar("deread",Hibernate.INTEGER)
				.addScalar("readdata",Hibernate.INTEGER)
				.addScalar("readtime",Hibernate.STRING)
				.addScalar("meterState",Hibernate.BYTE);

		
		q.setResultTransformer(Transformers.aliasToBean(ReadView.class));
		q.setParameterList("nid_list", nid_list);
		return q.list();
	}

	
	
	

}
