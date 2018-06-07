package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

/**
 * Servlet implementation class PaymentCodeRedirect
 */
@WebServlet("/PaymentCodeRedirect")
public class PaymentCodeRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentCodeRedirect() {
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
		String id = request.getParameter("paymentCode");
		Date startDate = new Date();
		Date endDate = new Date();
		String firstName = null;
		String lastName = null;
		String pNumber = null;
		String email = null;
		String promo = null;
		boolean expired = false;
		String trimmedCode = id.substring(id.lastIndexOf("?")+1);
		System.out.println("trimmedCode: " + trimmedCode);
		Long timeStamp = Long.parseLong(trimmedCode);
		if ((System.currentTimeMillis() - timeStamp) >= (60*60*1000)) { //add 24 between 60 and 1000
			// interval is over 24 hours
			expired = true;
		} else {
			// interval is less than 24 hours
			expired = false;
		}
		
		
		
		//connect to db
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		//get all matching info
		try {
			PreparedStatement pcps = con.prepareStatement("select * from dates where confirmationId = ?");
			pcps.setString(1, id);
			ResultSet rs = pcps.executeQuery();
			while(rs.next()) {
				startDate = rs.getDate("startDate");
				endDate = rs.getDate("endDate");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				pNumber = rs.getString("phone");
				promo = rs.getString("promo");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("PROMO in PCREDIRECT: " + promo);
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
		    
		
		//calculate price
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
	    
	    int discount = 0;
	    double discountMath = 0;
	    double totalMath = 0;
	    
	    System.out.println("TOTALPRICE BEFORE PROMO CODE" + totalPrice);
	    
	    //if promo isnt't null, get discount
	    if(promo != null) {
	    	try {
				PreparedStatement proPs = con.prepareStatement("select * from promos where code = ?");
				System.out.println("promo: " + promo);
				proPs.setString(1, promo);
				ResultSet proRs = proPs.executeQuery();
				while(proRs.next()) {
					discount = proRs.getInt("discount");
					System.out.println("discount: " + discount);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	discountMath = (double)discount/100;
	    	System.out.println("discount math: " + discountMath);
			totalMath = ((double)totalPrice - ((double)totalPrice * discountMath));
			totalPrice = (int)totalMath;
			System.out.println("total price after DISCOUNT MATH: " + totalPrice);
	    }
	    
	   
		//forward it all
	    if(expired == false) {
			request.setAttribute("startDate", startDate.toString());
			request.setAttribute("endDate", endDate.toString());
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("pNumber", pNumber);
			request.setAttribute("email", email);
			request.setAttribute("price", totalPrice);
			request.setAttribute("promo", promo);
		    
					
			System.out.println("TOTAL PRICE PASSED TO payment: " + totalPrice);
					
			request.getRequestDispatcher("payment.jsp").forward(request, response);
	    }
	    else {
	    	request.setAttribute("startDate", startDate.toString());
			request.setAttribute("endDate", endDate.toString());
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("pNumber", pNumber);
			request.setAttribute("email", email);
			request.setAttribute("price", totalPrice);
			request.setAttribute("promo", promo);
			request.getRequestDispatcher("/HandleExpiration").forward(request, response);
	    }
	}

}
