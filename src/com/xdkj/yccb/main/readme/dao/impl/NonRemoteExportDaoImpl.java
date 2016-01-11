package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.NonRemoteExport;
import com.xdkj.yccb.main.entity.RemoteExport;
import com.xdkj.yccb.main.readme.dao.NonRemoteExportDao;

@Repository
public class NonRemoteExportDaoImpl extends HibernateDAO<NonRemoteExport> implements NonRemoteExportDao {

	@Override
	public List<NonRemoteExport> getList(int wcid) {
		Query q = getSession().createQuery("from NonRemoteExport r where r.watercompany.pid = :wcid");
		q.setInteger("wcid", wcid);
		return q.list();
	}

	@Override
	public NonRemoteExport getByID(int export_id) {
		Query q = getSession().createQuery("from NonRemoteExport r where r.pid = :export_id");
		q.setInteger("export_id", export_id);
		return (NonRemoteExport) q.uniqueResult();
	}

}
