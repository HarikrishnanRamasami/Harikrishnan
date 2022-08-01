package module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public abstract class Service {
  
	public abstract String executeService(HttpServletRequest request, HttpServletResponse response);

	public String executeServiceAction(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}