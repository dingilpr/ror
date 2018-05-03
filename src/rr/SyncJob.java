package rr;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class SyncJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		URL calurl = null;
		try {
			calurl = new URL("https://www.airbnb.com/calendar/ical/21715641.ics?s=a13290268869a0cd41b5392ddbc211c6");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 InputStream fileContent = null;
		try {
			fileContent = calurl.openStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		    
		   
		    
		 CalendarBuilder builder = new CalendarBuilder();
		 net.fortuna.ical4j.model.Calendar calendar = null;
		 List<Date> importedDates = new ArrayList<Date>();
    	 try {
			calendar = builder.build(fileContent);
		 } catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	//ummm
    	 SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
    	   
    	 
    	  if (calendar != null) {
	           ComponentList<CalendarComponent> comps = calendar.getComponents();
	           System.out.println(comps);
	         //connect to DB
				DBManager db = new DBManager();
				Connection con = db.getConnection();
				if(con == null){
					System.out.println("failed");
				}
				else{
					System.out.println("success ");
				}
				try {
					PreparedStatement clear = con.prepareStatement("DELETE FROM dates WHERE email=?");
					clear.setString(1, "imported");
					clear.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           //get all components, turn into dates somehow, add to db
	           for(int i = 0; i < comps.size(); i++) {
	        	    String dstring = comps.get(i).getProperty("DTSTART").toString();
	        	    String estring = comps.get(i).getProperty("DTEND").toString();  	
	        	    
	        	  //get last 8 characters, this is the date string 
	        	    String trimmed = dstring.substring(dstring.length() - 10);
	        	    String trimmedEnd = estring.substring(dstring.length() - 12);
	        	    System.out.println(trimmedEnd);
	        	    Date imported = new Date();
	        	    Date importedEnd = new Date();
		        	    try {
							imported = parser.parse(trimmed);
							importedEnd = parser.parse(trimmedEnd);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
		    			//send dates to bd
		    			PreparedStatement psd;
		    			try {
							psd = con.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
							java.sql.Date startDatesql = new java.sql.Date(imported.getTime());
							java.sql.Date endDatesql = new java.sql.Date(importedEnd.getTime());
							psd.setDate(1, startDatesql);
							psd.setDate(2, endDatesql);
							psd.setString(3, "imported");
							psd.setString(4, "imported");
							psd.setString(5, "imported");
							psd.setString(6, "imported");
							psd.setString(7, "imported");
							psd.execute();
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
	           }
		    }
		
	}

}
