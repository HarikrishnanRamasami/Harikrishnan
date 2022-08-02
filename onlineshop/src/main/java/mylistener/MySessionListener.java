package mylistener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import dbpackage.DBCon;

@WebListener
public class MySessionListener implements HttpSessionListener {

      public void sessionCreated(HttpSessionEvent se)  { 
       System.out.println("session created...");
    }

	
    public void sessionDestroyed(HttpSessionEvent se)  {
    	 System.out.println("session destroyed...");
    	DBCon dbcon=new DBCon();
    	HttpSession session=se.getSession();
    	Object obj=session.getAttribute("uname");
    	if(obj!=null) {
    		String uname=obj.toString();
    		dbcon.updateFlag(0, uname);
    	}
      
    }
	
}
