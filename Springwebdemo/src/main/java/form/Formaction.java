package form;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("/form")

public class Formaction {
	
	@Autowired
	
	MessageSource messagesource;
	
	@RequestMapping(method = RequestMethod.GET,value="/register")
	
	public ModelAndView loadform() {
		
		ModelAndView mandv=new ModelAndView();
		
		mandv.addObject("userObj",new User());
		
		mandv.setViewName("userform");
		
		return mandv;
	}
	
	@RequestMapping(method =RequestMethod.POST,value="/register")
	
	public ModelAndView process(@Valid @ModelAttribute("userObj") User user,BindingResult result) {
		
		ModelAndView mandv=new ModelAndView();
		
		if(result.hasErrors()) {
			
			mandv.setViewName("userform");
			
			return mandv;
		}
		else {
		
		user.setUname(user.getUname()+" "+"Foodball player...!");
		
		user.setEmail(user.getEmail()+" "+"persnal mail....!");
		
		user.setPhone(user.getPhone()+" "+"ISD");
		
		mandv.addObject("myuser",user);
		
		mandv.setViewName("welcome");
		
		return mandv;
		
		}
	}
	
}
