package com.example.demo;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shopping")
public class Actions {

	@Autowired
	private Services st;

	@Autowired
	MessageSource ms;

	public Services getSt() {
		return st;
	}

	public void setSt(Services st) {
		this.st = st;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/register")
	public ModelAndView websearch(Consumer d) {

		ModelAndView mv = new ModelAndView();

		mv.addObject("user", d);

		mv.setViewName("register");

		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ModelAndView websearch(@Valid @ModelAttribute("user") Consumer d, BindingResult rs,
			HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		if (rs.hasErrors()) {

			mv.setViewName("register");

			return mv;

		} else {

			String pass1 = request.getParameter("pass1"), pass2 = d.getPass();

			if (pass1.equals(pass2)) {

				List<Consumer> a = st.checkUID(d);

				Iterator<Consumer> itr = a.iterator();

				int b = 0;

				while (itr.hasNext()) {
					b = itr.next().getUid();
				}

				d.setUid(b + 1);
				d.setFlag(0);

				mv.addObject("user", d);
				st.saveData(d);

				mv.setViewName("login");
				return mv;

			} else {

				mv.setViewName("register");
				return mv;

			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ModelAndView websearch5(HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		mv.setViewName("login");

		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ModelAndView websearch1(Consumer d, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();

		String name = request.getParameter("name"), pass = request.getParameter("pass");

		if (name.length() >= 5)

			mv.addObject("names", name);

		mv.addObject("passs", pass);

		List<Consumer> c = st.checkUser(name, pass);

		Iterator<Consumer> itr = c.iterator();
		request.getSession().setAttribute("name", name);
		request.getSession().setAttribute("pass", pass);

		while (itr.hasNext()) {

			if (itr.next().getFlag() == 0) {

				HttpSession hs=request.getSession();
				hs.setMaxInactiveInterval(30);
				mv.setViewName("welcome");
				st.updateFlag(1, name, pass);
				return mv;
			} else {

				mv.setViewName("logout");
				return mv;

			}

		}

		mv.addObject("user", d);

		mv.setViewName("register");
		return mv;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {

		System.out.println(request.getParameter("name") + ":" + request.getParameter("pass"));

		st.updateFlag(0, request.getParameter("name"), request.getParameter("pass"));

		return websearch5(request);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/shop")
	public String shop(Consumer d, HttpServletRequest request) {

		Enumeration<String> em = request.getParameterNames();

		HttpSession hs = request.getSession();

		while (em.hasMoreElements()) {

			String a = em.nextElement().toString();

			String b = request.getParameter(a);

			hs.setAttribute(a, b);

		}

		return request.getParameter("shop");

	}

	@RequestMapping(method = RequestMethod.GET, value = "/imgtake")
	public void met(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("image/jpg");
		String name = request.getParameter("name");
		String shopname = request.getParameter("shopname");
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "cristiano");
			String sql = "SELECT * FROM " + shopname + " WHERE name =?";
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				byte[] imageData = rs.getBytes("img");
				OutputStream os = response.getOutputStream();
				os.write(imageData);
				os.flush();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
