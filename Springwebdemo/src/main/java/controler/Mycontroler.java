package controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bs")
public class Mycontroler {
	@RequestMapping(method=RequestMethod.GET,value="/hello")
	public void met1() {
		System.out.println("met one is called....");
	}
	@RequestMapping(method=RequestMethod.GET,value="/hello2")
	public void met2(@RequestParam("name")String uname,@RequestParam("pass")String upass) {
		System.out.println("met2 one is called...."+uname+":"+upass);
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/hello3/{myt}")
	public void met3(@PathVariable("myt")String mypath) {
		System.out.println("met3 called.....................!"+mypath);
	}
	@RequestMapping(method =RequestMethod.GET,value="/hello4/{my1}/{my2}")
	
	public void met4(@PathVariable("my1")String mypath,@PathVariable("my2")String mypath2) {
		System.out.println("met4 called....."+mypath+":"+mypath2);
	}
}
