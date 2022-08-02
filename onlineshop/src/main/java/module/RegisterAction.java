package module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpackage.DBCon;
import dbpack.UserDIO;

public class RegisterAction extends Service {	
		
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
			
			boolean a=con.RegisterUser(user);
			
			if(a) {
					
					return "Logout-Success";
					
				}
				
			else {
			
			return "Logout-Fail";
			
			}
		
	}

}