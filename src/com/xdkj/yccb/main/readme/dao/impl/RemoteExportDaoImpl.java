package com.xdkj.yccb.main.readme.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.entity.RemoteExport;
import com.xdkj.yccb.main.readme.dao.RemoteExportDao;

@Repository
public class RemoteExportDaoImpl extends HibernateDAO<RemoteExport> implements
		RemoteExportDao {

	@Override
	public List<RemoteExport> getList(int wcid) {
		Query q = getSession().createQuery("from RemoteExport r where r.watercompany.pid = :wcid");
		q.setInteger("wcid", wcid);
		return q.list();
	}

	@Override
	public RemoteExport getByID(int export_id) {
		Query q = getSession().createQuery("from RemoteExport r where r.pid = :export_id");
		q.setInteger("export_id", export_id);
		return (RemoteExport) q.uniqueResult();
	}

}
