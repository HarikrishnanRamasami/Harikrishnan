package com.example.demo;

import java.io.IOException;
import java.net.http.HttpResponse;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class FilterFunction extends HttpFilter implements Filter{
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest hsr=(HttpServletRequest)request;
		HttpServletResponse hss=(HttpServletResponse) response;
		HttpSession hs=hsr.getSession();
		if(hs.isNew()) {
			hss.sendRedirect("../index.html");
		}else {
		chain.doFilter(request, response);
	}
	}
}
