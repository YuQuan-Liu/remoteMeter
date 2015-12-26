package com.xdkj.yccb.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xdkj.yccb.common.WebUtil;
import com.xdkj.yccb.common.encoder.Md5PwdEncoder;
import com.xdkj.yccb.main.adminor.service.AdministratorService;
import com.xdkj.yccb.main.entity.AdminRole;
import com.xdkj.yccb.main.entity.Admininfo;
import com.xdkj.yccb.main.entity.Customer;
import com.xdkj.yccb.main.entity.RoleAuthority;
import com.xdkj.yccb.main.infoin.service.CustomerService;
import com.xdkj.yccb.main.logger.ActionLogService;
@Controller
public class LoginCtrl {
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ActionLogService actionLogService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/resource/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String username = request.getParameter("loginname");
        String password = request.getParameter("loginkey");
        //获取HttpSession中的验证码  
        String checkcode = (String)request.getSession().getAttribute("check");
        //获取用户请求表单中输入的验证码  
        
        String submitCode = request.getParameter("checkcode");//WebUtils.getCleanParam(request, "checkcode");
        if (StringUtils.isEmpty(submitCode)){
        	return  "login";
        }
        if (!StringUtils.equals(checkcode, submitCode.toLowerCase())){  
            request.setAttribute("message_login", "验证码不正确！");  
            return  "login";  
        }
        
        String identity = request.getParameter("identity");
        if(identity.equals("1")){
        	//管理员
        	Admininfo admin = administratorService.getByLoginName(username, new Md5PwdEncoder().encodePassword(password));
            if(admin == null){
            	request.setAttribute("message_login", "用户名密码错误！"); 
                return "login";
            }else{
            	UserForSession ufs = new UserForSession();
    			ufs.setPid(admin.getPid());
    			ufs.setLoginName(admin.getLoginName());
    			ufs.setAdminName(admin.getAdminName());
    			ufs.setAdminEmail(admin.getAdminEmail());
    			ufs.setAdminMobile(admin.getAdminMobile());
    			ufs.setWaterComId(admin.getWatercompany().getPid());
    			
    			//管理员没有片区   将0存到Session中
    			if(null == admin.getDepartment()){
    				ufs.setDepart_id(0);
    			}else{
    				ufs.setDepart_id(admin.getDepartment().getPid());
    			}
    			
    			List<AdminRole> adminRole = new ArrayList<AdminRole>(admin.getAdminRoles());
    			Set<RoleAuthority> ras = adminRole.get(0).getRoles().getRoleAuthorities();
    			Map<String, String> menus = new HashMap<String, String>();
    			for (RoleAuthority roleAuthority : ras) {
    				menus.put(roleAuthority.getAuthority().getAuthorityCode(), "t");
    			}
    			ufs.setMenus(menus);
    			//log
    			actionLogService.addActionlog(admin.getPid(), 28, "login:adminid"+admin.getPid());
    			
    			request.getSession().setAttribute("curuser", ufs);
    			request.getSession().setAttribute("identity", identity);
            }
            return "redirect:/index.do";
        }else{
        	//用户登录
        	Customer c = customerService.getByMobile(username, new Md5PwdEncoder().encodePassword(password));
            if(c == null){
            	request.setAttribute("message_login", "用户名密码错误！"); 
                return "login";
            }else{
    			request.getSession().setAttribute("customer", c);
    			request.getSession().setAttribute("identity", identity);
    			return "redirect:/c/customer.do";
            }
        }
	}

	
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);

		if(session.getAttribute("identity").equals("1")){
			//log
			actionLogService.addActionlog(WebUtil.getCurrUser(request).getPid(), 29, "logout:adminid"+WebUtil.getCurrUser(request).getPid());
		}
		session.removeAttribute("curuser");
		session.removeAttribute("identity");
		session.removeAttribute("customer");
		return "redirect:/login.do";
	}
}
