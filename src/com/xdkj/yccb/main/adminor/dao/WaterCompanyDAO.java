package com.xdkj.yccb.main.adminor.dao;

import java.util.List;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Watercompany;

public interface WaterCompanyDAO {
	List<Watercompany> getList(Watercompany watcom,PageBase pageInfo);
	
	Integer getTotalCount(Watercompany watcom);
	
	Integer addWatcom(Watercompany watcom);
}
