package com.xdkj.yccb.main.adminor.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.main.adminor.dao.DetaildepartDAO;
import com.xdkj.yccb.main.entity.Department;
import com.xdkj.yccb.main.entity.Detaildepart;
import com.xdkj.yccb.main.entity.Neighbor;
@Repository
public class DetaildepartDAOImpl extends HibernateDAO<Detaildepart> implements
		DetaildepartDAO {

	@Override
	public List<Detaildepart> getListByDepId(int depId) {
		String hql = "from Detaildepart dpt where dpt.valid='1' and dpt.department.pid=:pid";
		return getSession().createQuery(hql).setParameter("pid", depId).list();
	}

	@Override
	public int addDetaildepart(Detaildepart dpt) {
		getHibernateTemplate().save(dpt);
		return dpt.getPid();
	}


	@Override
	public int addDetaildeparts(Department dep, int[] nbr_ids) {
		for(int i = 0;i < nbr_ids.length;i++){
			Detaildepart detail = new Detaildepart();
			detail.setDepartment(dep);
			detail.setValid("1");
			Neighbor n = new Neighbor();
			n.setPid(nbr_ids[i]);
			detail.setNeighbor(n);
			getHibernateTemplate().save(detail);
		}
		
		return nbr_ids.length;
	}

	@Override
	public String deleteDetaildepart(int dep_id, int n_id) {
		String hql = "update Detaildepart dpt set dpt.valid='0' where dpt.department.pid = :dep_id and dpt.neighbor.pid = :n_id ";
		if(getSession().createQuery(hql).setInteger("dep_id", dep_id).setInteger("n_id", n_id).executeUpdate()>0){
			return "true";
		}
		return "false";
	}

	@Override
	public Detaildepart getDetailBy(int dep_id, int n_id) {
		String hql = "from Detaildepart dpt where dpt.neighbor.pid = :n_id and dpt.department.pid=:dep_id";
		return (Detaildepart) getSession().createQuery(hql).setInteger("dep_id", dep_id).setInteger("n_id", n_id).uniqueResult();
	}


}
