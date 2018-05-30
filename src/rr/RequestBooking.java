package rr;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;

/**
 * Servlet implementation class RequestBooking
 */
@WebServlet("/RequestBooking")
public class RequestBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestBooking() {
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
		String startDateStr = request.getParameter("hiddenStartDateTwo");
		String endDateStr = request.getParameter("hiddenEndDateTwo");
		System.out.println(startDateStr);
		
		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd yyyy"); 
		
		//use these for error checks
		boolean invalidDate = false;
		int errorCode = 0;
		
		//reformat dates sent from JSP
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(startDateStr);
			endDate = formatter4.parse(endDateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//check to see if strange dates somehow made it through
		if(startDate.after(endDate)) {
			invalidDate = true;
			errorCode = 1;
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
				
				//get the dates that are currently in people's carts (temp_dates) and see if there is overlap
				ArrayList<Date> tempDates = new ArrayList<Date>();
				PreparedStatement tps;
				
				try {
					tps = con.prepareStatement("select * from temp_dates");
					ResultSet trs = tps.executeQuery();
					while(trs.next()) {
						Date tdbstartDate = trs.getDate("startDate");
						Date tdbendDate = trs.getDate("endDate");
						if(tdbstartDate != null) {
							tempDates.add(tdbstartDate);
						}
						if(tdbendDate != null) {
							tempDates.add(tdbendDate);
						}
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ArrayList<Date> fullTempDates = new ArrayList<Date>();
				ArrayList<Date> dates = new ArrayList<Date>();
				
				//check for collision
				if(tempDates != null) {
					//get all dates between tempstart and tempend and add to fullTEMPDATES
					for(int i = 0; i < tempDates.size(); i+=2) {
						Date tempStart = tempDates.get(i);
						Date tempEnd = tempDates.get(i+1);
						java.util.Calendar addCal = java.util.Calendar.getInstance();
						addCal.setTime(tempEnd);
						addCal.add(java.util.Calendar.DATE, 1);  // number of days to add
						Date realEnd = addCal.getTime();  // dt is now the new date
						
						
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(tempStart);

						while (calendar.getTime().before(realEnd))
						{
						    Date result = calendar.getTime();
						    fullTempDates.add(result);
						    calendar.add(Calendar.DATE, 1);
						}
						
					}
					//get all dates between startdate and enddate
					 
				    Calendar calendar = new GregorianCalendar();
					calendar.setTime(startDate);
					java.util.Calendar addCalT = java.util.Calendar.getInstance();
			    	addCalT.setTime(endDate);
					addCalT.add(java.util.Calendar.DATE, 1);  // number of days to add
					Date realEnd = addCalT.getTime();  // dt is now the new date

				    while (calendar.getTime().before(realEnd))
				    {
					       Date result = calendar.getTime();
					       dates.add(result);
					       calendar.add(Calendar.DATE, 1);
				    }
					
					
					
					//compare all days sent from JSP to all days in tempDates
					for(int i = 0; i < dates.size(); i++) {
						for(int j = 0; j < tempDates.size(); j++) {
							if(dates.get(i).equals(tempDates.get(j))) {
								invalidDate = true;
								errorCode = 3;
							}
						}
					}
					    
				}
				
				//
				//finally, check AirBnB dates for collision
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
				 net.fortuna.ical4j.model.Calendar calendarB = null;
				 
				 try {
						calendarB = builder.build(fileContent);
					 } catch (ParserException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					 } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				 
				//
				//Parse AirBnB iCal to Java dates
		    	 SimpleDateFormat parser = new SimpleDateFormat("yyyyMMdd");
		    	 
		    	 if(calendarB != null) {
		    		 List<Date> airBdates = new ArrayList<Date>();
		    		 ComponentList<CalendarComponent> comps = calendarB.getComponents();
		    		 for(int i = 0; i < comps.size(); i++) {
		    			 String dstring = comps.get(i).getProperty("DTSTART").toString();
			        	 String estring = comps.get(i).getProperty("DTEND").toString(); 
			        	 
			        	 String trimmed = dstring.substring(dstring.length() - 10);
			        	 String trimmedEnd = estring.substring(dstring.length() - 12);
			        	    
			        	 Date imported = new Date();
			        	 Date importedEnd = new Date();
				         try {
						   imported = parser.parse(trimmed);
						   importedEnd = parser.parse(trimmedEnd);
						   
						   //for current dateset in question
						   for(int j = 0; j < dates.size(); j+=2) {
							   //if the start and end date overlap with another dateset for AirBnB
							   if((dates.get(j).getTime() >= imported.getTime()) && (dates.get(j+1).getTime() <= importedEnd.getTime())) {
								   //collision
								   errorCode = 5;
								   invalidDate = true;
							   }
						   }
						   
						 } catch (ParseException e) {	
							e.printStackTrace();
						 }
				         
				         airBdates.add(imported);
				         airBdates.add(importedEnd);
		    		 }
		    		 
		    		 //see if imported dates from DB are same as current AirB dates
		    		 
		    	 }
		    	 
		    	//if no collision, add dates to temp_dates
		 		if(!invalidDate) {
		 			try {
		 				PreparedStatement td = con.prepareStatement("insert into temp_dates(startDate, endDate)" + "values (?,?)");
		 				java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
		 				java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
		 				
		 				td.setDate(1, startDatesql);
		 				td.setDate(2, endDatesql);
		 				td.execute();
		 			} catch (SQLException e) {
		 				// TODO Auto-generated catch block
		 				e.printStackTrace();
		 			}
		 		}
		 		
		 		
		 		//give error code 2 if dates somehow already exist in dates DB table
				ArrayList<Date> alreadyDates = new ArrayList<Date>();
				PreparedStatement dps;
				try {
					dps = con.prepareStatement("select * from dates");
					ResultSet drs = dps.executeQuery();
					while(drs.next()) {
						Date dbstartDate = drs.getDate("startDate");
						Date dbendDate = drs.getDate("endDate");
						//get all dates in between
						List<Date> currentDates = new ArrayList<Date>();
					    Calendar calendar = new GregorianCalendar();
					    calendar.setTime(dbstartDate);

					    while (calendar.getTime().before(dbendDate))
					    {
					        Date result = calendar.getTime();
					        alreadyDates.add(result);
					        calendar.add(Calendar.DATE, 1);
					    }
						
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//get all dates between start and end
				 List<Date> currentDates = new ArrayList<Date>();
				    Calendar calendar = new GregorianCalendar();
				    calendar.setTime(startDate);
				    java.util.Calendar addCalTh = java.util.Calendar.getInstance();
					addCalTh.setTime(endDate);
					addCalTh.add(java.util.Calendar.DATE, 1);  // number of days to add
					Date realEnd = addCalTh.getTime();  // dt is now the new date
					int dayCounter = 0;

				    while (calendar.getTime().before(realEnd))
				    {
				    	dayCounter++;
				        Date result = calendar.getTime();
				        currentDates.add(result);
				        calendar.add(Calendar.DATE, 1);
				    }
				    
				 //check the list
				    for(int i = 0;i < currentDates.size(); i++) {
				    	System.out.println("DATE LIST: " + currentDates.get(i));
				    }
				    
				//check for collision in existing bookings
				   for(int i = 0; i < currentDates.size(); i++) {
					   for(int j = 0; j < alreadyDates.size(); j++) {
						   if(currentDates.get(i).equals(alreadyDates.get(j))) {
							   invalidDate = true;
							   errorCode = 2;
						   }
					   }
				   }
				
		 		//initialize map of prices and dates
				HashMap<Date, Integer> priceAndDate = new HashMap<>();
		 		//get price from DB
				PreparedStatement ps;
				try {
					ps = con.prepareStatement("select * from pricing");
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						Date date = rs.getDate("date");
						Integer price = rs.getInt("price");
						priceAndDate.put(date, price);
					}
					//System.out.println("ArrayList to send back: " + list);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	 
				//compare currentDates list with prices from DB to calculate a total
				int price = 0;
			    for(int i = 0;i < currentDates.size(); i++) {
			    	if(priceAndDate.containsKey(currentDates.get(i))) {
			    		price += priceAndDate.get(currentDates.get(i));
			    	}
			    }
			    
			    int deposit = price/2;
			    int cleaning = 100;
			    
			    int totalPrice = price + deposit + cleaning;
			    
			    int pricePerDay = price/dayCounter;
		    	
			    //reroute to new page with dates
			    HttpSession session = request.getSession();  
		    	session.setAttribute("startDate", startDateStr);
		    	session.setAttribute("endDate", endDateStr);
		    	session.setMaxInactiveInterval(60);
			    request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
				request.setAttribute("price", price);
				request.setAttribute("pricePerDay", pricePerDay);
				request.setAttribute("dayCounter", dayCounter);
				request.setAttribute("deposit", deposit);
				request.setAttribute("cleaning", cleaning);
				request.setAttribute("totalPrice", totalPrice);
				request.getRequestDispatcher("requestStay.jsp").forward(request, response);
	}

}
