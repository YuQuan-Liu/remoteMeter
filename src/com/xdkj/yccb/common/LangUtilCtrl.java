package com.xdkj.yccb.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
@Controller
public class LangUtilCtrl {
	 /**
     * 语言切换
     */
    @RequestMapping(value="/lang", method = {RequestMethod.GET})
    public String changeLang(HttpServletRequest request,Model model, @RequestParam(value="langType", defaultValue="zh") String langType){
        if(langType.equals("zh")){
            Locale locale = new Locale("zh", "CN"); 
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale); 
        }
        else if(langType.equals("en")){
            Locale locale = new Locale("en", "US"); 
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
        }
        else {
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,LocaleContextHolder.getLocale());
        }
        //返回当前页面
        String uri = request.getHeader("Referer");
        return "redirect:"+uri;
    }
    /**
     * 登录跳转，国际化处理
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="login*",method=RequestMethod.GET)
    public String login(HttpServletRequest request,HttpServletResponse response){
    	HttpSession session = request.getSession();
    	session.removeAttribute("check");
		return "login";
    }
}
