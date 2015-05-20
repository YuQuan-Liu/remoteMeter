package com.xdkj.yccb.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.RoleAuthority;

/**
 * 自定义DB Realm
 * 
 */
public class MyAuthorizingRealm extends AuthorizingRealm {
	@Autowired
	private AdministratorService administratorService;

	/**
	 * 登录认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Admininfo adInfo = null;
		try {
			adInfo = administratorService.getByLoginName(token.getUsername(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (adInfo != null) {
			UserForSession ufs = new UserForSession();
			ufs.setPid(adInfo.getPid());
			ufs.setLoginName(adInfo.getLoginName());
			ufs.setAdminName(adInfo.getAdminName());
			ufs.setAdminEmail(adInfo.getAdminEmail());
			ufs.setAdminMobile(adInfo.getAdminMobile());
			ufs.setWaterComId(adInfo.getWatercompany().getPid());
			
			//管理员没有片区   将0存到Session中
			if(null == adInfo.getDepartment()){
				ufs.setDepart_id(0);
			}else{
				ufs.setDepart_id(adInfo.getDepartment().getPid());
			}
			
			List<AdminRole> adminRole = new ArrayList<AdminRole>(adInfo.getAdminRoles());
			Set<RoleAuthority> ras = adminRole.get(0).getRoles().getRoleAuthorities();
			Map<String, String> menus = new HashMap<String, String>();
			for (RoleAuthority roleAuthority : ras) {
				menus.put(roleAuthority.getAuthority().getAuthorityCode(), "t");
			}
			ufs.setMenus(menus);
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
