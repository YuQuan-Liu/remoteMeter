package com.xdkj.yccb.main.readme.dao;

import java.util.List;

import com.xdkj.yccb.main.readme.dto.WasteReadView;

public interface WasteLogDao {

	List<WasteReadView> getWasteByReadlogid(int readlogid);

	void addWaste(int wid, String reason);

}
