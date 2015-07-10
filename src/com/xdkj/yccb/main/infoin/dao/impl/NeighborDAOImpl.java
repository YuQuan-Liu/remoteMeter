package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
import com.xdkj.yccb.main.infoin.dto.NeighborView;
import com.xdkj.yccb.main.statistics.dto.ChargeRate;
import com.xdkj.yccb.main.statistics.dto.MonthSettled;
import com.xdkj.yccb.main.statistics.dto.MonthWaste;
import com.xdkj.yccb.main.statistics.dto.NeighborBalance;
import com.xdkj.yccb.main.statistics.dto.SettledWaste;
import com.xdkj.yccb.main.statistics.dto.SettledWater;
import com.xdkj.yccb.main.statistics.dto.SettledWaterN;
@Repository
public class NeighborDAOImpl extends HibernateDAO<Neighbor> implements NeighborDAO {
	
	public List<Neighbor> getList(int depart_id,int wcid) {
		String hql = "";
		if(depart_id == 0){
			//获取自来水下全部小区
			hql = "from Neighbor nbr where nbr.valid='1' and nbr.watercompany.pid = "+ wcid +" ";
		}else{
			//获取片区下的全部小区
			hql = "select neighbor from Detaildepart detail " +
					"where detail.valid='1' and detail.department.watercompany.pid = "+wcid+" and detail.department.valid = 1 and detail.department.pid = "+depart_id+" and detail.neighbor.valid = 1 ";
		}

		Query q = getSession().createQuery(hql);
		return q.list();
	}

	@Override
	public String checkn_name(int wcid, String n_name) {
		String sql = "from Neighbor nbr where nbr.valid='1' and nbr.neighborName = :n_name and nbr.watercompany.pid = "+ wcid +" ";
		Query q = getSession().createQuery(sql);
		q.setString("n_name", n_name);
		
		if(null == q.uniqueResult()){
			return "false";
		}
		return "true";
		
	}
	
	@Override
	public int addNeighbor(Neighbor nbr) {
		this.getHibernateTemplate().save(nbr);
		return nbr.getPid();
	}

	@Override
	public int getTotalCount(Neighbor nbr,PageBase pb) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Neighbor nbr where nbr.valid='1' ");
		Query q = getSession().createQuery(sb.toString());
		return ((Number)q.uniqueResult()).intValue();
	}

	@Override
	public Neighbor getById(int pid) {
		String hql = "from Neighbor nbr where nbr.pid= "+pid;
		Query q = getSession().createQuery(hql);
		return (Neighbor) q.uniqueResult();
	}

	@Override
	public void update(Neighbor nbr) {
		this.getHibernateTemplate().update(nbr);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void delete(int id) {
		
		String sql = "update Neighbor set valid = 0 where pid = :n_id ";
		Query q = getSession().createSQLQuery(sql);
		q.setInteger("n_id", id);
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Neighbor> getNbrByWatcomId(int wcId) {
		String hql = "from Neighbor nbr where nbr.valid='1' and nbr.watercompany.pid=:pid";
		return getSession().createQuery(hql).setParameter("pid", wcId).list();
	}

	@Override
	public Neighbor getNbrByWcIdName(String wcid, String n_name) {
		String hql = "from Neighbor nbr where nbr.watercompany.pid= "+wcid+" and nbr.neighborName = '"+ n_name+"' and nbr.valid = 1";
		Query q = getSession().createQuery(hql);
		return (Neighbor) q.uniqueResult();
	}

	@Override
	public List<NeighborBalance> getNeighborBalance(int n_id) {
		
		String SQL = "select case when prepaysign = 1 then '预'else '后' end pre,sum(customerBalance) balance from customer c " +
				"where c.neighborid = :n1 and valid = 1 " +
				"group by prepaysign " +
				"union " +
				"select '合计' pre,sum(customerBalance) balance from customer c " +
				"where c.neighborid = :n1 and valid = 1";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("pre", Hibernate.STRING).addScalar("balance", Hibernate.BIG_DECIMAL);
		q.setResultTransformer(Transformers.aliasToBean(NeighborBalance.class));
		q.setInteger("n1", n_id);
//		q.setInteger("n2", n_id);
		List<NeighborBalance> list = new ArrayList<>();
		
		try {
			list = q.list();
		} catch (HibernateException e) {
//			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<String> getLous(int n_id) {
		String sql = "select distinct louNum lou from customer where valid = 1 and neighborid = :n_id";
		Query q = getSession().createSQLQuery(sql).addScalar("lou", Hibernate.STRING);
		q.setInteger("n_id", n_id);
		return q.list();
	}

	@Override
	public List<String> getDys(int n_id, String lou) {
		String sql = "select distinct dyNum dy from customer where valid = 1 and neighborid = :n_id and louNum = :lou";
		Query q = getSession().createSQLQuery(sql).addScalar("dy", Hibernate.STRING);
		q.setInteger("n_id", n_id);
		q.setString("lou", lou);
		return q.list();
	}

	@Override
	public List<MonthWaste> getWaste(int n_id, int year) {

		String SQL = "select s.month_ month,sum(w.meterread) nRead,sum(w.SalveSum) slaveSum from wastelog w " +
				"join ( " +
				"select max(pid) readlogid,month(completetime) month_ from readlog " +
				"where year(completetime) = :year and settle = 1 and objectid = :n_id " +
				"group by month(completetime) " +
				")s " +
				"on w.readlogid = s.readlogid " +
				"where w.louNum = 0 " +
				"group by s.month_";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("month", Hibernate.INTEGER)
				.addScalar("nRead", Hibernate.INTEGER)
				.addScalar("slaveSum", Hibernate.INTEGER);
		q.setInteger("year", year);
		q.setInteger("n_id", n_id);
		q.setResultTransformer(Transformers.aliasToBean(MonthWaste.class));
		return q.list();
	}

	@Override
	public List<SettledWater> getSettledWater(String ids, int year) {
//		String SQL ="select sum_.pid,ObjectID n_id,neighborName,month,sum(yl) yl,sum(demoney) demoney from ( " +
//				"select s.pid,s.objectid,month(s.startTime) month,sum(meterread-lastderead) yl,sum(demoney) demoney  from settlelog s " +
//				"join  meterdeductionlog mdl " +
//				"on s.pid = mdl.settlelogid " +
//				"where year(startTime) = 2015 and objecttype = 1 and mdl.valid = 1 and mdl.changend = 0 " +
//				"group by s.pid,s.objectid,month(s.startTime) " +
//				"union " +
//				"select s.pid,s.objectid,month(s.startTime),sum(meterread+mdl.changend-lastderead) yl,sum(demoney) demoney  from settlelog s " +
//				"join  meterdeductionlog mdl " +
//				"on s.pid = mdl.settlelogid " +
//				"where year(startTime) = 2015 and objecttype = 1 and mdl.valid = 1 and mdl.changend > 0 " +
//				"group by s.pid,s.objectid,month(s.startTime) " +
//				")sum_  " +
//				"join neighbor n " +
//				"on n.pid = sum_.objectid " +
//				"group by sum_.pid,ObjectID,neighborName,month";
		
		String SQL = "select s.pid settleid,s.startTime,n.pid n_id,n.neighborname n_name,sum(yl) yl,sum(demoney) demoney from ( " +
				"select s.pid,sum(meterread-lastderead) yl,sum(demoney) demoney  from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend = 0 " +
				"group by s.pid " +
				"union all " +
				"select s.pid,sum(meterread+mdl.changend-lastderead) yl,sum(demoney) demoney  from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend > 0 " +
				"group by s.pid " +
				")sum_ " +
				"join settlelog s " +
				"on sum_.pid = s.pid " +
				"join neighbor n " +
				"on s.objectid = n.pid " +
				"where n.pid in ("+ids+") " +
				"group by s.pid,s.starttime,n.pid,n.neighborname";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("settleid", Hibernate.INTEGER)
				.addScalar("n_id", Hibernate.INTEGER)
				.addScalar("n_name", Hibernate.STRING)
				.addScalar("yl", Hibernate.INTEGER)
				.addScalar("demoney", Hibernate.BIG_DECIMAL)
				.addScalar("startTime", Hibernate.STRING);
		q.setInteger("year", year);
//		q.setString("ids", ids);
		q.setResultTransformer(Transformers.aliasToBean(SettledWater.class));
		
		List<SettledWater> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MonthSettled> getMonthSettled(int n_id, int year) {
		String SQL = "select month(sum_.starttime) month,sum(yl) yl,sum(demoney) demoney from ( " +
				"select s.objectid,s.starttime,meterread-lastderead yl,mdl.demoney from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend = 0 and s.ObjectID = :n_id " +
				"union all " +
				"select s.objectid,s.starttime,meterread+mdl.changend-lastderead yl,mdl.demoney from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend > 0 and s.ObjectID = :n_id " +
				")sum_ " +
				"group by month(sum_.starttime)";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("yl", Hibernate.INTEGER)
				.addScalar("demoney", Hibernate.BIG_DECIMAL)
				.addScalar("month", Hibernate.INTEGER);
		q.setInteger("year", year);
		q.setInteger("n_id", n_id);
		q.setResultTransformer(Transformers.aliasToBean(MonthSettled.class));
		
		List<MonthSettled> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<SettledWaste> getSettledyl(int n_id, int year) {
		String SQL = "select s.completetime settleTime,w.meterread nread,w.SalveSum slaveSum from wastelog w " +
				"join ( " +
				"select pid readlogid,completetime from readlog " +
				"where year(completetime) = :year and settle = 1 and objectid = :n_id " +
				") s " +
				"on w.readlogid = s.readlogid " +
				"where w.louNum = 0 ";
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("settleTime", Hibernate.STRING)
				.addScalar("nread", Hibernate.INTEGER)
				.addScalar("slaveSum", Hibernate.INTEGER);
		q.setInteger("year", year);
		q.setInteger("n_id", n_id);
		q.setResultTransformer(Transformers.aliasToBean(SettledWaste.class));
		
		return q.list();
	}

	@Override
	public List<SettledWaterN> getSettledWaterN(String ids, int year) {
		String SQL = "select n.pid n_id,n.neighborname n_name,sum(yl) yl,sum(demoney) demoney from ( " +
				"select s.pid,sum(meterread-lastderead) yl,sum(demoney) demoney  from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend = 0 " +
				"group by s.pid " +
				"union all " +
				"select s.pid,sum(meterread+mdl.changend-lastderead) yl,sum(demoney) demoney  from settlelog s " +
				"join  meterdeductionlog mdl " +
				"on s.pid = mdl.settlelogid " +
				"where year(startTime) = :year and objecttype = 1 and mdl.valid = 1 and mdl.changend > 0 " +
				"group by s.pid " +
				")sum_ " +
				"join settlelog s " +
				"on sum_.pid = s.pid " +
				"join neighbor n " +
				"on s.objectid = n.pid " +
				"where n.pid in ("+ids+") " +
				"group by n.pid,n.neighborname";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("n_id", Hibernate.INTEGER)
				.addScalar("n_name", Hibernate.STRING)
				.addScalar("yl", Hibernate.INTEGER)
				.addScalar("demoney", Hibernate.BIG_DECIMAL);
		q.setInteger("year", year);
//		q.setString("ids", ids);
		q.setResultTransformer(Transformers.aliasToBean(SettledWaterN.class));
		
		List<SettledWaterN> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ChargeRate> getChargeRate(String ids, int year) {
		String SQL = "select cc.neighborid n_id,cc.neighborname n_name,cc.owecount,cc.allcount,format(cc.owerate,4) owerate,cc.owebalance,cc.balance,mm.demoney,format(1-cc.owebalance/mm.demoney,4) chargerate from ( " +
				"select c.neighborid,n.neighborname,sum( case when c.customerbalance < 0 then 1 else 0 end) owecount, " +
				"count(*) allcount, sum( case when c.customerbalance < 0 then 1 else 0 end)/count(*) owerate," +
				"sum( case when c.customerbalance < 0 then -c.customerbalance else 0 end) owebalance,sum(c.customerbalance) balance from customer c " +
				"join neighbor n on n.pid = c.neighborid " +
				"where c.neighborid in ("+ids+") and c.valid = 1 " +
				"group by c.neighborid) cc " +
				"join ( " +
				"select m.neighborid,sum(mdl.demoney) demoney from meterdeductionlog mdl " +
				"join meter m " +
				"on mdl.meterid = m.pid " +
				"where m.neighborid in ("+ids+") and mdl.valid = 1 and m.valid = 1 " +
				"group by m.neighborid ) mm " +
				"on cc.neighborid = mm.neighborid";
		
		Query q = getSession().createSQLQuery(SQL)
				.addScalar("n_id", Hibernate.INTEGER)
				.addScalar("n_name", Hibernate.STRING)
				.addScalar("owecount", Hibernate.INTEGER)
				.addScalar("allcount", Hibernate.INTEGER)
				.addScalar("owerate", Hibernate.DOUBLE)
				.addScalar("owebalance", Hibernate.BIG_DECIMAL)
				.addScalar("balance", Hibernate.BIG_DECIMAL)
				.addScalar("demoney", Hibernate.BIG_DECIMAL)
				.addScalar("chargerate", Hibernate.DOUBLE);
//		q.setString("ids", ids);
		q.setResultTransformer(Transformers.aliasToBean(ChargeRate.class));
		
		List<ChargeRate> list = new ArrayList<>();
		try {
			list = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String checkGPRSAddr(String gprsaddr) {
		String sql = "from Gprs g where g.valid='1' and g.gprsaddr = :gprsaddr";
		Query q = getSession().createQuery(sql);
		q.setString("gprsaddr", gprsaddr);
		
		if(null == q.uniqueResult()){
			return "false";
		}
		return "true";
	}

	@Override
	public List<Neighbor> getTimerList() {
		
		Query q = getSession().createQuery("from Neighbor n where n.valid='1' and n.timerSwitch=1");
		return q.list();
	}

	@Override
	public Admininfo getAdmin(Neighbor n) {
		Query q = getSession().createQuery("from Admininfo a where a.watercompany.pid = :wcid and a.valid = 1 order by a.pid desc ");
		q.setInteger("wcid", n.getWatercompany().getPid());
		return (Admininfo) q.list().get(0);
	}

	

}
