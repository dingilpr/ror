package rr;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		 String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
		    Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		    String fileName = getSubmittedFileName(filePart);
		    InputStream fileContent = filePart.getInputStream();
		    
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
	           //get all components, turn into dates somehow, add to db
	           for(int i = 0; i < comps.size(); i++) {
	        	    String dstring = comps.get(i).getProperty("DTSTART").toString();
	        	    //get last 8 characters, this is the date string 
	        	    String trimmed = dstring.substring(dstring.length() - 10);
	        	    Date imported = new Date();
					try {
						imported = parser.parse(trimmed);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	    importedDates.add(imported);
	           }
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
			
			boolean streak = false;
			Date startDate = new Date();
			Date endDate = new Date();
			
			//send imported dates to DB
			for(int i = 0; i < importedDates.size(); i++) {
				if(i == 0 || streak == false) {
					startDate = importedDates.get(i);
				}
				//get next consecutive date
				java.util.Calendar c = java.util.Calendar.getInstance();
				c.setTime(importedDates.get(i));
				c.add(java.util.Calendar.DATE, 1);
				Date tomorrow = c.getTime();
				
				//see if next consecutive date is next date in list
				if(i < importedDates.size() - 1) {
					if(tomorrow.getTime() == importedDates.get(i+1).getTime()) {
						streak = true;
					}
					else {
						streak = false;
						endDate = importedDates.get(i);
						PreparedStatement psd;
						
						try {
							psd = con.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
							java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
							java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
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
	
	private static String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

}
