package com.xdkj.yccb.common;

import javax.servlet.http.HttpServletRequest;

import com.xdkj.yccb.security.UserForSession;
/**
 * 常用帮助类
 * @author SGR
 *
 */
public class WebUtil {
	/**
	 * 获取当前登录用户的信息   
	 * @param request
	 * @return
	 */
	public static UserForSession getCurrUser(HttpServletRequest request){
		if(null == request.getSession(false)){
			return null;
		}
		return (UserForSession) request.getSession(false).getAttribute("curuser");
	}

}
