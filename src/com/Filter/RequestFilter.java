package com.Filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestFilter implements Filter{


	//req.getContextPath();//->"/shopping"
	//D:\apache-tomcat-7.0.52\webapps\shopping\
	//String realPath = req.getServletContext().getRealPath("/");
	//String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+req.getContextPath()+"/";
	FilterConfig config;
	String errPage="/jsp/ShowError.jsp";
	String[] params;
	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String param = config.getInitParameter("param");
		this.params = param.split(",");
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) arg0;
			HttpServletResponse rep = (HttpServletResponse) arg1;
			req.setCharacterEncoding("utf-8");
			rep.setCharacterEncoding("utf-8");
			HttpSession ss = req.getSession(true);
			String name="";
			name = (String)ss.getAttribute("name");
			String path = req.getServletPath();
			String begin = config.getInitParameter("begin");
			//System.out.println(path);
			if(name == null&&!path.endsWith(".js")&&isContains(path)){
				rep.sendRedirect(req.getContextPath()+"/"+begin);
				//rep.sendRedirect(basePath+"index.jsp");
			}else{
					try {
						arg2.doFilter(req, rep);
					} catch (Exception e) {
						
						req.getSession().setAttribute("exception", e);
						rep.sendRedirect(req.getContextPath()+errPage);
						//req.getRequestDispatcher(errPage).forward(req, rep);
						e.printStackTrace();
					}
			}
		
	}

	private boolean isContains(String url){
		for(String temp : params){
			if(url.endsWith(temp)||url.contains(temp)){
				return false;
			}
		}
		return true;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
