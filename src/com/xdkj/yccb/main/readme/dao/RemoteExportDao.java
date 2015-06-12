package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.RemoteExport;

public interface RemoteExportDao {

	List<RemoteExport> getList(int wcid);

	/**
	 * 抄表导出时  获取对应的导出类
	 * @param export_id
	 * @return
	 */
	RemoteExport getByID(int export_id);

}
