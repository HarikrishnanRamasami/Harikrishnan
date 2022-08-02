package com.example.demo;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mcr")
public class MyController {
	
	@Autowired
	MessageSource messagesource;
	@Autowired
	private UserService service;

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/reg")
	public ModelAndView loadForm() {
		ModelAndView mv = new ModelAndView();
		User user = new User();

		user.setFlag(0);
		int a=user.getUserid()+1;

		mv.addObject("userobj", user);
		
		mv.setViewName("register");
		
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/reg")
	public ModelAndView processForm(@Valid @ModelAttribute("userobj") User user,BindingResult result, HttpServletRequest request
		) {
		
		ModelAndView mv = new ModelAndView();
	
		if(result.hasErrors()) {
			
			mv.setViewName("register");
			
			return mv;
			
		}else {
			
		System.out.println(user.getUsername());
		
		String password1 = request.getParameter("password1");

		String password2 = request.getParameter("password2");
		

		if (password1.equals(password2)) {

			List<User> a = service.checkUserid(user);

			Iterator<User> itr = a.iterator();

			int b = 0;

			while (itr.hasNext()) {

				b = itr.next().getUserid();

			}
			int g = b + 1;

			System.out.println(b);

			user.setPassword1(password1);
			
			user.setUserid(g);

			mv.addObject("user", user);

			service.saveUser(user);

			mv.setViewName("welcome");

			return mv;

		} else {

			mv.setViewName("error");

			return mv;

		}
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ModelAndView logform() {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("Login");

		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ModelAndView logprocess(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		String name = request.getParameter("name");

		String pass = request.getParameter("pass");

		System.out.println(name + ":" + pass);
		
		List<User> list = service.checkUser(name, pass);

		Iterator<User> itr = list.iterator();

		while (itr.hasNext()) {

			mv.setViewName("wellloging");

			return mv;

		}

		mv.setViewName("error");

		return mv;

	}

}
