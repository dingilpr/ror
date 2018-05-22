package rr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import net.fortuna.ical4j.model.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;
import net.fortuna.ical4j.validate.ValidationException;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

public class SyncJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//First check AirBnB for new dates
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
    	 
    	//Parse AirBnB iCal to Java dates
    	 SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
    	   
    	 //Replace old imported dates with new ones
    	  if (calendar != null) {
	           ComponentList<CalendarComponent> comps = calendar.getComponents();
	           System.out.println(comps);
	           //connect to DB
				DBManager dbQ = new DBManager();
				Connection conQ = dbQ.getConnection();
				if(conQ == null){
					System.out.println("failed");
				}
				else{
					System.out.println("success ");
				}
				try {
					PreparedStatement clear = conQ.prepareStatement("DELETE FROM dates WHERE email=?");
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
							psd = conQ.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
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
    	  
    	    //connect to AWS s3
			BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAJRX5JRE6SU3UUFAA", "Z9EjoynRyIJcAKJ8dTuXI9UXSCNXvwmh1BRk12nL");
		        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
		                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                .withRegion("us-west-2")
		                .build();
		        
		        String bucket = "rorcal";
		        String key = "bookings.ics";
		        
		        
		        
		      
			
			//replace current calendar file if exists
			
			//upload to s3
		        
		        
			//get all dates from DB for calendar EXPORT
			 //connect to DB
			DBManager db = new DBManager();
			Connection con = db.getConnection();
			if(con == null){
				System.out.println("failed");
			}
			else{
				System.out.println("success ");
			}
			
			//get all dates
			PreparedStatement dps;
			java.util.Calendar calendarQ = new GregorianCalendar();
			 // Creating a new calendar
		      Calendar calendarS = new Calendar();
		      calendarS.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
		      calendarS.getProperties().add(Version.VERSION_2_0);
		      calendarS.getProperties().add(CalScale.GREGORIAN);

			List<Date> dates = new ArrayList<Date>();
			try {
				dps = con.prepareStatement("select * from dates");
				ResultSet drs = dps.executeQuery();
				while(drs.next()) {
					Date startDate = drs.getDate("startDate");
					Date endDate = drs.getDate("endDate");
					java.util.Calendar addCal = java.util.Calendar.getInstance();
					addCal.setTime(endDate);
					addCal.add(java.util.Calendar.DATE, 1);  // number of days to add
					Date realEnd = addCal.getTime();  // dt is now the new date
					
					//get all dates between start and end
					
					    calendarQ.setTime(startDate);

					    while (calendarQ.getTime().before(realEnd))
					    {
					        Date result = calendarQ.getTime();
					        dates.add(result);
					        calendarQ.add(java.util.Calendar.DATE, 1);
					    }
				}
					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i = 0; i < dates.size(); i++) {
				java.util.Calendar calendarT = java.util.Calendar.getInstance();
				calendarT.setTime(dates.get(i));
				VEvent booking = new VEvent(new net.fortuna.ical4j.model.Date(calendarT.getTime()), "Booking");
				// Generate a UID for the event..
				UidGenerator ug = null;
				try {
					ug = new UidGenerator("1");
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				booking.getProperties().add(ug.generateUid());
				calendarS.getComponents().add(booking);
			}
			
			
			  //send dates to ical
			 
	     
			
			
			File file = new File("/tmp/booking.ics");
			
			 if (!file.exists()) {
			     try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			 
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(file);
			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			CalendarOutputter outputter = new CalendarOutputter();
			try {
				outputter.output(calendarS, fout);
			} catch (ValidationException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			s3.deleteObject(new DeleteObjectRequest(bucket, key));
			
			PutObjectRequest request = new PutObjectRequest(bucket, key, file);
			request.setCannedAcl(CannedAccessControlList.PublicRead);
			
		    s3.putObject(request);
		
	}

}
