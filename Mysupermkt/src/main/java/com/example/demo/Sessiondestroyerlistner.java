package com.example.demo;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sessiondestroyerlistner implements HttpSessionListener {

	@Autowired
	private Services st;

	public Services getSt() {
		return st;
	}

	public void setSt(Services st) {
		this.st = st;
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session created ..");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
	HttpSession hts=se.getSession();
		Object a=hts.getAttribute("name");
		Object b=hts.getAttribute("pass");
		if(a!=null && b!=null) {
			
	String c=a.toString();
	String d=b.toString();
	System.out.println(c+" "+d);
		getSt().updateFlag(0, c, d);}
		
		System.out.println("session deleted ..");
	}

}
