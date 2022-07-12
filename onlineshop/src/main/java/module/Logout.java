package module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpackage.DBCon;

public class Logout extends Service{
	
	public Logout() {
		
	}

	@Override
	public String executeService(HttpServletRequest request, HttpServletResponse response) {
		
		String name=request.getSession().getAttribute("uname").toString();
		
		DBCon con=new DBCon();
		
		boolean a=con.updateFlag(0, name);
		
		if(a) {
			
			return "Logout-Success";
			
		}else {
		
		return "Logout-Fail";
		
		}
		
	}

}