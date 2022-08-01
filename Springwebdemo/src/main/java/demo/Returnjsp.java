package demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("/drm")

@SessionAttributes({"dream"})
public class Returnjsp {

	@RequestMapping(method = RequestMethod.GET, value = "/dr")

	public String dream() {

		return "dream";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/dr2")

	public String dream2(HttpServletRequest request,Model model) {
		
		request.setAttribute("dream","this is my dream and my joys...!");
		
		model.addAttribute("dream","this is added in dr2...");
		
		return "dream";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/dr3")

	public ModelAndView dream3() {

		ModelAndView mod = new ModelAndView();
		mod.addObject("dream","this is your joys....!");
		mod.setViewName("dream");

		return mod;
	}
	
	
	
	
}
