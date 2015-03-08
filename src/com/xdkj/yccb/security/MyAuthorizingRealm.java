package com.xdkj.yccb.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xdkj.yccb.usermanage.entity.Users;
import com.xdkj.yccb.usermanage.service.UserService;

/**
 * 自定义DB Realm
 * 
 */
public class MyAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;

	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Users u = userService.findByUname(token.getUsername());
		if (u != null) {
			 // this.setSession("currentUser", u);
			return new SimpleAuthenticationInfo(u.getUname(), u.getPasswd(),getName());
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

}
