package restcontroller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rest")
public class Demo_1 {
	
	@RequestMapping(method = RequestMethod.GET,value="/say")
	public void sayHellow() {
		System.out.println("hi bro");
	}
	@RequestMapping(method = RequestMethod.GET,value="/say2")
	public String sayHellow2() {
		return "welcome";
	}
	@RequestMapping(method = RequestMethod.GET,value="/say3")
	public ModelAndView sayHellow3() {
		
		ModelAndView mv=new ModelAndView();
		mv.addObject("myname","Welcome to the restcontroller method....");
		mv.setViewName("welcome");
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET,value="/say4")
	public ModelAndView sayHellow4(ModelAndView mv) {
		mv.addObject("myname"," Say4 method was called....!");
		mv.setViewName("welcome");
		return mv;

}
	@RequestMapping(method = RequestMethod.GET,value="/say5")
	public ModelAndView sayHellow5(ModelAndView mv,HttpServletRequest request) {
		mv.addObject("myname","Method 5 was called...........!");
		mv.setViewName("welcome");
		request.setAttribute("dreams","Follow your dreams........!");
		return mv;
	}
	@RequestMapping(method = RequestMethod.GET,value="/say6/{name}")
	public ModelAndView sayHellow6(@PathVariable String name, ModelAndView mv,HttpServletRequest request) {
		mv.addObject("myname",name);
		mv.setViewName("welcome");
		request.setAttribute("dreams","Follow your dreams........!");
		return mv;
	}
	
}
