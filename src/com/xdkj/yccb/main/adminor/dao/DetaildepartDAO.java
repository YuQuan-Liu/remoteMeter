package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.Detaildepart;

public interface DetaildepartDAO {
	List<Detaildepart> getListByDepId(int depId);
	
	int addDetaildepart(Detaildepart dpt);
	
	void deleteDetaildepart(int dptId);
}
