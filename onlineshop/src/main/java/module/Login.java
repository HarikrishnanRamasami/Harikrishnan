package module;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.Session;

import dbpackage.DBCon;
import dbpack.UserDIO;

public class Login extends Service{
	
	public Login() {
		
	}
	
	private UserDIO user;
	
	private DBCon con;

	public UserDIO getUser() {
		return user;
	}

	public void setUser(UserDIO user) {
		this.user = user;
	}

	public DBCon getCon() {
		return con;
	}

	public void setCon(DBCon con) {
		this.con = con;
	}

	@Override
	public String executeService(HttpServletRequest request, HttpServletResponse response) {
		
		user=new UserDIO();
		
		con=new DBCon();
		
		user.setUname(request.getParameter("uname"));
		
		user.setUpass(request.getParameter("upass"));
		
		boolean a=con.checkUser(user.getUname(), user.getUpass());
		
		if(a) {
			
			boolean b=con.checkFlag(user.getUname());
			
			if(b) {
				
				con.updateFlag(1,user.getUname());
				
				request.getSession().setAttribute("uname", user.getUname());
				
				return "Welcome-Page";
				
			}else {
				
				return "Already-Login";
				
			}
			
		}else {
		
		return "Invalid-User";
		
		}
		
	}


}
