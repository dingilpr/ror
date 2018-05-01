package rr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.util.Calendars;

/**
 * Servlet implementation class ImportCal
 */
@WebServlet("/ImportCal")
@MultipartConfig
public class ImportCal extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportCal() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String url = request.getParameter("description"); // Retrieves <input type="text" name="description">
		    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		   
		    
		    URL calurl = new URL(url);
		    InputStream fileContent = calurl.openStream();
		    
		   
		    
		    CalendarBuilder builder = new CalendarBuilder();
		    Calendar calendar = null;
		    List<Date> importedDates = new ArrayList<Date>();
		    try {
				calendar = builder.build(fileContent);
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
		    
		    if (calendar != null) {
	           ComponentList<CalendarComponent> comps = calendar.getComponents();
	           System.out.println(comps);
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
		        	    
		        	    //connect to DB
		    			DBManager db = new DBManager();
		    			Connection con = db.getConnection();
		    			if(con == null){
		    				System.out.println("failed");
		    			}
		    			else{
		    				System.out.println("success ");
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

	        
		   
