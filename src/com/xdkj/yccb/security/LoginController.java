package com.xdkj.yccb.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.xdkj.yccb.usermanage.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response,String uname, Model model){
		
		String username = request.getParameter("uname");
        String password = request.getParameter("passwd");
        //获取HttpSession中的验证码  
        String verifyCode = (String)request.getSession().getAttribute("verifyCode");
        //获取用户请求表单中输入的验证码  
        String submitCode = WebUtils.getCleanParam(request, "verifyCode");  
        System.out.println("用户[" + username + "]登录时输入的验证码为[" + submitCode + "],HttpSession中的验证码为[" + verifyCode + "]");  
       /* if (StringUtils.isEmpty(submitCode) || !StringUtils.equals(verifyCode, submitCode.toLowerCase())){  
            request.setAttribute("message_login", "验证码不正确");  
            return  "login";  
        }  */
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
        token.setRememberMe(true);
       // System.out.println("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();  
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查  
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应  
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法  
           //System.out.println("对用户[" + username + "]进行登录验证..验证开始");  
            currentUser.login(token);  
            //System.out.println("对用户[" + username + "]进行登录验证..验证通过");  
        }catch(UnknownAccountException uae){  
            //System.out.println("对用户[" + username + "]进行登录验证..验证未通过,未知账户");  
            request.setAttribute("message_login", "未知账户");  
        }catch(IncorrectCredentialsException ice){
        	//ice.printStackTrace();
            //System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");  
            request.setAttribute("message_login", "密码不正确");  
        }catch(LockedAccountException lae){  
            System.out.println("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");  
            request.setAttribute("message_login", "账户已锁定");  
        }catch(ExcessiveAttemptsException eae){  
            //System.out.println("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");  
            request.setAttribute("message_login", "用户名或密码错误次数过多");  
        }catch(AuthenticationException ae){  
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
            //System.out.println("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");  
           // ae.printStackTrace();  
            request.setAttribute("message_login", "用户名或密码不正确");  
        }  
        //验证是否登录成功  
        if(currentUser.isAuthenticated()){
        	//登录成功的操作
            System.out.println("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            loginSucc(token,true,currentUser,request,response);
        }else{  
            token.clear();  
        }  
		return "/index";
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
}
