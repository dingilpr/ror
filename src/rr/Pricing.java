package rr;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.HashMap;

import rr.DBManager;

import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

/**
 * Servlet implementation class Pricing
 */
@WebServlet("/Pricing")
public class Pricing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pricing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//pre process serve list to admin jsp
		JSONArray list=new JSONArray();
		JSONArray emails=new JSONArray();
		JSONArray dates=new JSONArray();
		JSONArray cancelDates=new JSONArray();
		JSONArray cancelledDates=new JSONArray();
		JSONArray heldDates=new JSONArray();
		JSONArray bookingReqs=new JSONArray();
		JSONArray inquiries=new JSONArray();
		JSONArray promos=new JSONArray();
		JSONArray months=new JSONArray();
		
	    //connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{
			System.out.println("success ");
		}
		
		//get data from DB
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from pricing order by date asc");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Date date = rs.getDate("date");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				int month = date.getMonth();
				int year = date.getYear()+1900;
				String monthYear = Integer.toString(month) +"/" + Integer.toString(year);
				String dateString = df.format(date);
				String price = Integer.toString(rs.getInt("price"));
				if(!months.contains(monthYear)) {
					months.add(monthYear);
				}
				list.add(monthYear);
				list.add(dateString);
				list.add(price);
			}
			System.out.println("ArrayList to send back: " + list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement pse;
		try {
			pse = con.prepareStatement("select * from emails");
			ResultSet rse = pse.executeQuery();
			while(rse.next()) {
				String email = rse.getString("email");
				
				emails.add(email);
			}
			System.out.println("ArrayList to send back: " + list);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement dps;
		
		try {
			dps = con.prepareStatement("select * from dates");
			ResultSet drs = dps.executeQuery();
			while(drs.next()) {
				
				Date startDate = drs.getDate("startDate");
				Date endDate = drs.getDate("endDate");
				String fName = drs.getString("firstName");
				String lName = drs.getString("lastName");
				String phone = drs.getString("phone");
				String email = drs.getString("email");
				String confirmationId = drs.getString("confirmationId");
				DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
				int depositPaid = drs.getInt("depositPaid");
				String stringStart = ddf.format(startDate);
				String stringEnd = ddf.format(endDate);
				dates.add(stringStart);
				dates.add(stringEnd);
				dates.add(fName);
				dates.add(lName);
				dates.add(phone);
				dates.add(email);
				dates.add(confirmationId);
				dates.add(depositPaid);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
PreparedStatement cps;
		
		try {
			cps = con.prepareStatement("select * from cancelledTrips");
			ResultSet crs = cps.executeQuery();
			while(crs.next()) {
				
				Date startDate = crs.getDate("startDate");
				Date endDate = crs.getDate("endDate");
				String fName = crs.getString("firstName");
				String lName = crs.getString("lastName");
				String phone = crs.getString("phone");
				String email = crs.getString("email");
				String confirmationId = crs.getString("confirmationId");
				DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
				String stringStart = ddf.format(startDate);
				String stringEnd = ddf.format(endDate);
				cancelledDates.add(stringStart);
				cancelledDates.add(stringEnd);
				cancelledDates.add(fName);
				cancelledDates.add(lName);
				cancelledDates.add(phone);
				cancelledDates.add(email);
				cancelledDates.add(confirmationId);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
PreparedStatement rps;
		
		try {
			rps = con.prepareStatement("select * from cancel_req");
			ResultSet rrs = rps.executeQuery();
			while(rrs.next()) {
				
				String email = rrs.getString("email");
				String confirmationId = rrs.getString("confirmationId");
				
				cancelDates.add(email);
				cancelDates.add(confirmationId);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement hps;
		try {
			hps = con.prepareStatement("select * from temp_dates");
			ResultSet hrs = hps.executeQuery();
			while(hrs.next()) {
				
				Date startDate = hrs.getDate("startDate");
				Date endDate = hrs.getDate("endDate");
				
				DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
				String stringStart = ddf.format(startDate);
				String stringEnd = ddf.format(endDate);
				
				heldDates.add(stringStart);
				heldDates.add(stringEnd);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement brps;
		try {
			brps = con.prepareStatement("select * from booking_req where inquiry = 0");
			ResultSet brrs = brps.executeQuery();
			while(brrs.next()) {
				String email = brrs.getString("email");
				Date startDate = brrs.getDate("startDate");
				Date endDate = brrs.getDate("endDate");
				String price = brrs.getString("priceWithPromo");
				
				DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
				String stringStart = ddf.format(startDate);
				String stringEnd = ddf.format(endDate);
				
				bookingReqs.add(email);
				bookingReqs.add(stringStart);
				bookingReqs.add(stringEnd);
				bookingReqs.add(price);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get inquiries
		PreparedStatement inps;
		try {
			inps = con.prepareStatement("select * from booking_req where inquiry = 1");
			ResultSet inrs = inps.executeQuery();
			while(inrs.next()) {
				String email = inrs.getString("email");
				Date startDate = inrs.getDate("startDate");
				Date endDate = inrs.getDate("endDate");
				String price = inrs.getString("priceWithPromo");
				
				DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
				String stringStart = ddf.format(startDate);
				String stringEnd = ddf.format(endDate);
				
				inquiries.add(email);
				inquiries.add(stringStart);
				inquiries.add(stringEnd);
				inquiries.add(price);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get promos
				PreparedStatement pps;
				try {
					pps = con.prepareStatement("select * from promos");
					ResultSet prs = pps.executeQuery();
					while(prs.next()) {
						String code = prs.getString("code");
						int discount = prs.getInt("discount");
						int members = prs.getInt("members");
						
						promos.add(code);
						promos.add(discount);
						promos.add(members);
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
		request.setAttribute("list", list);
		request.setAttribute("emails", emails);
		request.setAttribute("dates", dates);
		request.setAttribute("cancelDates", cancelDates);
		request.setAttribute("cancelledDates", cancelledDates);
		request.setAttribute("heldDates", heldDates);
		request.setAttribute("bookingReqs", bookingReqs);
		request.setAttribute("inquiries", inquiries);
		request.setAttribute("promos", promos);
		request.setAttribute("months", months);
		HttpSession session = request.getSession(false);
		String correctu = "admin";
		
		if(session.getAttribute("username") == null) {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		if(session.getAttribute("username").equals(correctu)) {
			request.getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String json;
		JSONParser parser = new JSONParser();
		ArrayList<String> list = new ArrayList<String>(); 
		ArrayList<String> formattedList = new ArrayList<String>();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");  
		
		//get pricing first so i can check for duplicates
		//initialize map
		HashMap<Date, Integer> priceAndDate = new HashMap<>();
		
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{
			System.out.println("success ");
		}
		
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
		
		
		//get jsonArray from hidden input field
		if(request.getParameter("hiddenArrayField") != null) {
			json = request.getParameter("hiddenArrayField");
			System.out.println(json);
			
			System.out.println("STRING-" + json);
			//convert jsonArray to ArrayList
			try {
				Object obj = parser.parse(json);
				JSONArray array = (JSONArray)obj;
				if (array != null) { 
					   int len = array.size();
					   for (int i=0;i<len;i++){ 
					    list.add(array.get(i).toString());
			           } 
				}
				//parse ArrayList and reformat for DB
				for(int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i));
					StringBuilder str = new StringBuilder(list.get(i));
					String price = str.substring(44,47);
					System.out.println("PRICE: " + price);
					String date = str.substring(60,70);
					System.out.println("DATE: " + date);
					formattedList.add(date);
					formattedList.add(price);
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			json = "nuthin here";
		}
		
		System.out.println("FORMATTED LIST: " + formattedList);
				
                //send new dates and prices to DB
				PreparedStatement psT;
				for(int i = 0; i < formattedList.size(); i += 2) {
					try {
						psT = con.prepareStatement("insert into pricing(date,price)" + "values (?,?)");
						Date date = new Date();
						
						//format date and price for the last time
						date = formatter.parse(formattedList.get(i));
						//check if date already exists
						if(priceAndDate.containsKey(date)) {
							System.out.println("Updating new Price!");
							PreparedStatement psX = con.prepareStatement("UPDATE pricing SET date = ?, price = ? WHERE  date = ?");
							java.sql.Date sqlDate = new java.sql.Date(date.getTime());
							int price = Integer.parseInt(formattedList.get(i + 1));
							psX.setDate(1, sqlDate);
							psX.setInt(2, price);
							psX.setDate(3, sqlDate);
							psX.execute();
						}
						
						else if(!priceAndDate.containsKey(date)) {
							java.sql.Date sqlDate = new java.sql.Date(date.getTime());
							int price = Integer.parseInt(formattedList.get(i + 1));
							
							//send over
							psT.setDate(1, sqlDate);
							psT.setInt(2,  price);
							
							psT.execute();
						}
					      
						
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				response.sendRedirect("/Pricing");
		
	}

}
