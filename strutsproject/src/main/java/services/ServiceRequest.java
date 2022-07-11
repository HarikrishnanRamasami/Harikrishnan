package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ServiceRequest {
	  
	public abstract String executeService(HttpServletRequest request, HttpServletResponse response);

}
