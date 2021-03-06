package rr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Servlet implementation class ExportCal
 */
@WebServlet("/ExportCal")
public class ExportCal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExportCal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		java.util.Calendar calendar = new GregorianCalendar();
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
				
				    calendar.setTime(startDate);

				    while (calendar.getTime().before(realEnd))
				    {
				        Date result = calendar.getTime();
				        dates.add(result);
				        calendar.add(java.util.Calendar.DATE, 1);
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
			UidGenerator ug = new UidGenerator("1");
			booking.getProperties().add(ug.generateUid());
			calendarS.getComponents().add(booking);
		}
		
		
		  //send dates to ical
		 
     
		
		
		File file = new File("/tmp/booking.ics");
		
		 if (!file.exists()) {
		     file.createNewFile();
		  }
		 
		FileOutputStream fout = new FileOutputStream(file);

		CalendarOutputter outputter = new CalendarOutputter();
		outputter.output(calendarS, fout);
		
		s3.deleteObject(new DeleteObjectRequest(bucket, key));
		
	    s3.putObject(new PutObjectRequest(bucket, key, file));
		
		
		/**serve the file for downloading
		
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileLocation);
				
		byte[] buffer = new byte[4096];

		while((fis.read(buffer, 0, 4096)) != -1){
			sos.write(buffer, 0, 4096);
		}
		
		fis.close();
		**/
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
