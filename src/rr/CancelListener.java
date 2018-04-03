package rr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class CancelListener
 *
 */
@WebListener
public class CancelListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener {

    /**
     * Default constructor. 
     */
    public CancelListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent hse)  { 
         // TODO Auto-generated method stub
    	/**
    	 HttpSession session = hse.getSession();
    	 Object startDate = session.getAttribute("startDateStr");
    	 Object endDate = session.getAttribute("endDateStr");
    	 session.setAttribute("startDateStr", startDate);
    	 session.setAttribute("endDateStr", endDate);
    	 **/
    }

	/**
     * @see HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)
     */
    public void valueBound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent sesh)  { 
         // TODO Auto-generated method stub
    	/**
    	HttpSession cancel = sesh.getSession();
    	String startDateStr = null;
    	String endDateStr = null;
    	startDateStr = (String) cancel.getAttribute("startDateStr");
    	endDateStr = (String) cancel.getAttribute("endDateStr");
    	
    	SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd yyyy"); 
    	
    	Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(startDateStr);
			endDate = formatter4.parse(endDateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{
			System.out.println("success ");
		}
		
		//remove from temp_dates
		PreparedStatement tsd;
		try {
			tsd = con.prepareStatement("delete from temp_dates where startDate = ? and endDate = ?");
			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
			tsd.setDate(1, startDatesql);
			tsd.setDate(2, endDatesql);
			tsd.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		**/
    }

	/**
     * @see HttpSessionActivationListener#sessionDidActivate(HttpSessionEvent)
     */
    public void sessionDidActivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent ses)  { 
         // TODO Auto-generated method stub
    	HttpSession cancel = ses.getSession();
    	System.out.println("IN ATTRBTADDED");
  
    	if(cancel.getAttribute("pressedBack") != null) {
    		System.out.println("PAST NULL CHECK");
    		ArrayList<String> data = (ArrayList<String>)cancel.getAttribute("pressedBack");
    		String startDatestr = data.get(0);
    		String endDatestr = data.get(1);
    		
    		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd yyyy"); 
        	
        	Date startDate = null;
    		Date endDate = null;
    		try {
    			startDate = formatter4.parse(startDatestr);
    			endDate = formatter4.parse(endDatestr);
    		} catch (ParseException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    		
    		//connect to DB
    		DBManager db = new DBManager();
    		Connection con = db.getConnection();
    		if(con == null){
    			System.out.println("failed");
    		}
    		else{
    			System.out.println("success ");
    		}
    		
    		//remove from temp_dates
    		PreparedStatement tsd;
    		try {
    			tsd = con.prepareStatement("delete from temp_dates where startDate = ? and endDate = ?");
    			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
    			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
    			tsd.setDate(1, startDatesql);
    			tsd.setDate(2, endDatesql);
    			tsd.execute();
    			
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		cancel.invalidate();
    	}
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionActivationListener#sessionWillPassivate(HttpSessionEvent)
     */
    public void sessionWillPassivate(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see HttpSessionBindingListener#valueUnbound(HttpSessionBindingEvent)
     */
    public void valueUnbound(HttpSessionBindingEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
