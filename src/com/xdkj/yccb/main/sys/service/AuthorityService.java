package com.xdkj.yccb.main.sys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xdkj.yccb.common.PageBase;
import com.xdkj.yccb.main.entity.Authority;
import com.xdkj.yccb.main.sys.dto.AuthorityView;

public interface AuthorityService {
	
	/**
	 * 获取权限树
	 * @param request
	 * @param roleId
	 * @return
	 */
	String getAuthTreeJson(HttpServletRequest request);
	

}
