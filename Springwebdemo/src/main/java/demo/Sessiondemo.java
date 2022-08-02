package demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.SessionScope;

@Controller

@RequestMapping("/bs2")

public class Sessiondemo {

	@RequestMapping(method = RequestMethod.GET, value = "/session1")

	public void met1(HttpServletRequest request) {

		System.out.println("met1................" + request);

		HttpSession sess = request.getSession();

		System.out.println("session.............." + sess.getId());

		sess.setAttribute("cr7", "i am a foodball player......");

		String str = sess.getAttribute("cr7").toString();

		System.out.println("cr7........" + str);
	}

	@RequestMapping(method = RequestMethod.GET,value="/session2")
	public void met2(HttpSession ses) {

		System.out.println("ses2.........." + ses.getId());

		String str = ses.getAttribute("cr7").toString();
		
		System.out.println("cr72..."+str);

	}
}
