package com.xdkj.yccb.security;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.Admininfo;

/**
 * 自定义DB Realm
 * 
 */
public class MyAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private AdministratorService userService;

	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Admininfo adInfo = userService.getByLoginName(token.getUsername(), null);
		if (adInfo != null) {
			UserForSession ufs = new UserForSession();
			try {
				PropertyUtils.copyProperties(ufs, adInfo);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
			setSession("curuser", ufs);
			return new SimpleAuthenticationInfo(adInfo.getLoginName(), adInfo.getLoginKey(),getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		//CmsUser user = cmsUserMng.findByUsername(username);
		SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
		/*if (user != null) {
			Set<String>viewPermissionSet=new HashSet<String>();
			Set<String> perms = user.getPerms(site.getId(),viewPermissionSet);
			if (!CollectionUtils.isEmpty(perms)) {
				// 权限加入AuthorizationInfo认证对象
				auth.setStringPermissions(perms);
			}
		}*/
		return auth;
	}
	
	public void removeUserAuthorizationInfoCache(String username){
		  SimplePrincipalCollection pc = new SimplePrincipalCollection();
		  pc.add(username, super.getName()); 
		  super.clearCachedAuthorizationInfo(pc);
	}
	private void setSession(String key,UserForSession ufs){
		Subject curUser = SecurityUtils.getSubject();
		if(null!=curUser){
			Session session = curUser.getSession();
			session.setAttribute(key, ufs);
		}
	}
}
