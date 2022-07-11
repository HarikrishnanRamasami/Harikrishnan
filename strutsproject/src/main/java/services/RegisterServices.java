package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpack.DBConnection;
import dtopack.UserDTO;


public class RegisterServices extends ServiceRequest {	
		
		private UserDTO user;
		
		private DBConnection con;

		public UserDTO getUser() {
			return user;
		}

		public void setUser(UserDTO user) {
			this.user = user;
		}

		public DBConnection getCon() {
			return con;
		}

		public void setCon(DBConnection con) {
			this.con = con;
		}

		@Override
		public String executeService(HttpServletRequest request, HttpServletResponse response) {
			
			user=new UserDTO();
			
			con=new DBConnection();
			
			user.setName(request.getParameter("Name"));
			
			user.setPassword(request.getParameter("Password"));
			
			boolean a=con.registerUser(user);
			
			if(a) {
					
					return "Logout-Success";
					
				}
				
			else {
			
			return "Logout-Fail";
			
			}
		
	}

}
