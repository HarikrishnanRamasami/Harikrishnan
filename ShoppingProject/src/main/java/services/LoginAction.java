package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbpack.DBCon;
import dtopack.UserDTO;

public class LoginAction extends Action{

	private UserDTO userDTO; 
	private DBCon  dbcon;
	public LoginAction() {
		// TODOm Auto-generated constructor stub
	}
	
public DBCon getDbcon() {
		return dbcon;
	}


	public void setDbcon(DBCon dbcon) {
		this.dbcon = dbcon;
	}


public UserDTO getUserDTO() {
		return userDTO;
	}


	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}


public String execute(HttpServletRequest request,HttpServletResponse response) {
	userDTO=new UserDTO();
	dbcon=new DBCon();
	userDTO=new UserDTO();
	dbcon=new DBCon();
	userDTO.setName(request.getParameter("Name"));
	userDTO.setPassword(request.getParameter("Password"));
	
	boolean user=dbcon.checkUser(userDTO.getName(),userDTO.getPassword());
	if(user)
	{
		boolean valid=dbcon.checkFlag(userDTO.getName());
		if(valid)
		{
			dbcon.updateFlag(userDTO.getName(),1 );
			request.getSession().setAttribute("Name", userDTO.getName());
			return "login.welcome";
		}
		else
		{
			return "login.alreadylogedin";
		}
	}
	else 
	{
	return "login.invaliduser";
}
}
}