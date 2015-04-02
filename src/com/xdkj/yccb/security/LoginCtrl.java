package com.xdkj.yccb.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class LoginCtrl {
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,String uname, Model model){
		
		String username = request.getParameter("loginname");
        String password = request.getParameter("loginkey");
        //获取HttpSession中的验证码  
        String checkcode = (String)request.getSession().getAttribute("check");
        //获取用户请求表单中输入的验证码  
        String submitCode = WebUtils.getCleanParam(request, "checkcode");  
        if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(checkcode, submitCode.toLowerCase())){  
            request.setAttribute("message_login", "验证码不正确！");  
            return  "login";  
        } 
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
        token.setRememberMe(true);
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();  
        try {
            currentUser.login(token);  
        }catch(UnknownAccountException uae){  
            request.setAttribute("message_login", "未知账户！"); 
            return "login";
        }catch(IncorrectCredentialsException ice){
            request.setAttribute("message_login", "密码不正确！");
            return "login";
        }catch(LockedAccountException lae){  
            request.setAttribute("message_login", "账户已锁定！");
            return "login";
        }catch(ExcessiveAttemptsException eae){  
            request.setAttribute("message_login", "用户名或密码错误次数过多！");
        }catch(AuthenticationException ae){  
            request.setAttribute("message_login", "用户名或密码不正确！");
            return "login";
        }  
        //验证是否登录成功  
        if(currentUser.isAuthenticated()){
        	//登录成功的操作
            loginSucc(token,true,currentUser,request,response);
        }else{
            token.clear();  
        }  
		return "redirect:/index.do";
	}
	/**
	 * 登录成功操作
	 */
	private void loginSucc(AuthenticationToken token,boolean adminLogin,Subject subject,
			ServletRequest request, ServletResponse response){
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String username = (String) subject.getPrincipal();
		//管理登录
		if(adminLogin){
			//cmsLogMng.loginSuccess(req, user);
		}
		// 清除需要验证码cookie
		//return super.onLoginSuccess(token, subject, request, response);
	}
	@RequestMapping(value="logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			// session销毁
			subject.logout(); 
		}
		return "redirect:/login.do";
	}
}
