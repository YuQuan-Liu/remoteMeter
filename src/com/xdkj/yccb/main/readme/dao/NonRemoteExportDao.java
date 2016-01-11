package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.entity.NonRemoteExport;

public interface NonRemoteExportDao {

	List<NonRemoteExport> getList(int wcid);

	/**
	 * 非远传导出时  获取对应的导出类
	 * @param export_id
	 * @return
	 */
	NonRemoteExport getByID(int export_id);
}
