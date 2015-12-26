package com.xdkj.yccb.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/**
 * Servlet Filter implementation class LoginFilter
 */
//@WebFilter("/LoginFilter")
@Component
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String uri = req.getRequestURI().toLowerCase();

//		System.out.println(uri);
		if(!uri.contains("resource")){
			//
			HttpSession session = req.getSession(false);
			
			if(session == null){
				res.sendRedirect(req.getContextPath()+"/resource/login.do");
				return;
			}else{
				if(session.getAttribute("identity") == null){
					res.sendRedirect(req.getContextPath()+"/resource/login.do");
					return;
				}else{
					if(session.getAttribute("customer") != null){
//						if(!uri.contains("/c/")){
//							res.sendRedirect(req.getContextPath()+"/resource/login.do");
//							return;
//						}
					}
				}
			}
		}
		
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
