package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpack.DBConnection;

public class LogoutServices extends ServiceRequest
{@Override
public String executeService(HttpServletRequest request, HttpServletResponse response) {
	String name=request.getSession().getAttribute("cname").toString();
	
	DBConnection con=new DBConnection();
	
	boolean a=con.updateFlag(name, 0);
	
	if(a) {
		
		return "Logout-Success";
		
	}else {
	
	return "Logout-Fail";
	
	}
	
}

}
