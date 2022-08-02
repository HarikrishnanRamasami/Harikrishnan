package com.example.demo;

import java.sql.Blob;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("/mycontrol")

public class MyController {

	@Autowired

	private UserService service;

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public ModelAndView register() {

		ModelAndView mv = new ModelAndView();

		User user = new User();

		user.setFlag(0);

		mv.addObject("user", user);

		mv.setViewName("Register");

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")

	public ModelAndView register1(@ModelAttribute("user") User user, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		String pass1 = request.getParameter("pass1");

		String pass2 = request.getParameter("pass2");

		if (pass1.equals(pass2)) {

			List<User> list = service.checkid(user);

			Iterator<User> iter = list.iterator();

			int f = 0;

			while (iter.hasNext()) {

				f = iter.next().getUserid();

			}

			int g = f + 1;

			user.setPassword(pass2);

			user.setUserid(g);

			mv.addObject("user", user);

			service.saveuser(user);

			mv.setViewName("Welcome");

			return mv;
		} else {
			mv.setViewName("Errors");

			return mv;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ModelAndView loginload() {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("Login");

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ModelAndView loginprocess(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		String username = request.getParameter("username");

		String password = request.getParameter("password");

		List<User> list = service.checkuser(username, password);

		Iterator<User> iter = list.iterator();

		while (iter.hasNext()) {

			mv.setViewName("Welcome");

			return mv;

		}
		mv.setViewName("Errors");

		return mv;
	}
}