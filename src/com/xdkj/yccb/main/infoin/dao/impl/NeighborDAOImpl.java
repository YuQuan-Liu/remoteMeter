package com.xdkj.yccb.main.infoin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xdkj.yccb.common.HibernateDAO;
import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Neighbor;
import com.xdkj.yccb.main.infoin.dao.NeighborDAO;
@Repository
public class NeighborDAOImpl extends HibernateDAO<Neighbor> implements NeighborDAO {

	@Override
	public List<Neighbor> getList(Neighbor nbr, PageBase pb) {
		return null;
	}

	@Override
	public int addNeighbor(Neighbor nbr) {
		return 0;
	}

	@Override
	public int getTotalCount(Neighbor nbr) {
		return 0;
	}

	@Override
	public Neighbor getById(int pid) {
		return null;
	}

	@Override
	public void update(Neighbor nbr) {

	}

}
