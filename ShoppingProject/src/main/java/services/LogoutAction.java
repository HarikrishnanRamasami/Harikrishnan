package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpack.DBCon;

public class LogoutAction extends Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String Name=request.getSession().getAttribute("Name").toString();
		
		DBCon dbcon=new DBCon();
		
		boolean done=dbcon.updateFlag(Name, 0);
		
		if(done)
		{
			return "logout.success";
		}
		else {
			return "logout.failure";
		}
		
	}

}
