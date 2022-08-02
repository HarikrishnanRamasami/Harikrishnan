package com.example.demo;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/controller")
public class UserController {

	@Autowired
	private Userservice service;

	public Userservice getService() {
		return service;
	}

	public void setService(Userservice service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET, value = ("/register"))
	public ModelAndView registerload() {
		ModelAndView mv = new ModelAndView();

		User user = new User();
		
		user.setLoginid(0);

		int log = user.getId() + 1;

		user.setId(log);

		mv.addObject("user", user);

		mv.setViewName("Register");

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = ("/register"))
	public ModelAndView registerprocess(@Valid @ModelAttribute User user,BindingResult result, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String pass = request.getParameter("password");

		String cpass = request.getParameter("cpassword");
		
		if(result.hasErrors()) {
			
			mv.setViewName("Register");
			
			return mv;
		}	
			
			else if (cpass.equals(pass)) {

			List<User> list = service.checkid(user);

			Iterator<User> iter = list.iterator();

			int b = 0;
			while (iter.hasNext()) {

				b = iter.next().getId();
			}

			System.out.println(b);

			user.setId(b + 1);

			mv.addObject("user1", user);

			service.saveuser(user);

			mv.setViewName("Welcome");

			return mv;
		} else {
			mv.setViewName("error");

			return mv;

		}
		}
	

	@RequestMapping(method = RequestMethod.GET, value = ("/login"))
	public ModelAndView loginload() {
		ModelAndView mv = new ModelAndView();

		mv.setViewName("Login");

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = ("/login"))
	public ModelAndView loginprocess(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String name = request.getParameter("name");

		String pass = request.getParameter("password");

		System.out.println(name + ":" + pass);

		List<User> list = service.checkuser(name, pass);

		Iterator<User> iter = list.iterator();

		while (iter.hasNext()) {

			mv.setViewName("Login");

			return mv;

		}
		mv.setViewName("error");
		return mv;

	}

}
