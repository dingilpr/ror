package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AcceptRequest
 */
@WebServlet("/AcceptRequest")
public class AcceptRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptRequest() {
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
		//Collect vars
		String start = request.getParameter("startAccept");
		String end = request.getParameter("endAccept");
		
		SimpleDateFormat formatter4=new SimpleDateFormat("yyyy-MM-dd"); 
		
		//reformat dates sent from JSP
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(start);
			endDate = formatter4.parse(end);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//connect to db
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		//get rest of info from booking_req then remove
		PreparedStatement brps;
		ResultSet brrs;
		String firstName = null;
		String lastName = null;
		String email = null;
		String phone = null;
		String promo = null;
		String priceWithoutPromo = null;
		String priceWithPromo = null;
		
		String confirmationId = UUID.randomUUID().toString().replaceAll("-", "");
		java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
		java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
		String code = confirmationId + "?" + System.currentTimeMillis();
		
		
		try {
			brps = con.prepareStatement("SELECT * FROM booking_req WHERE startDate=? AND endDate=?");
			brps.setDate(1, startDatesql);
			brps.setDate(2, endDatesql);
		    brrs = brps.executeQuery();
		    while(brrs.next()) {
		    	firstName = brrs.getString("firstName");
		    	lastName = brrs.getString("lastName");
		    	email = brrs.getString("email");
		    	phone = brrs.getString("phone");
		    	if(brrs.getString("promo") != null) {
		    		promo = brrs.getString("promo");
		    	}
		    	priceWithoutPromo = brrs.getString("priceWithoutPromo");
		    	priceWithPromo = brrs.getString("priceWithPromo");
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//add to dates
		PreparedStatement psd;
		try {
			psd = con.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId, promo, priceWithoutPromo, priceWithPromo, paid)" + "values (?,?,?,?,?,?,?,?,?,?,?)");
			startDatesql = new java.sql.Date(startDate.getTime());
			endDatesql = new java.sql.Date(endDate.getTime());
			psd.setDate(1, startDatesql);
			psd.setDate(2, endDatesql);
			psd.setString(3, firstName);
			psd.setString(4, lastName);
			psd.setString(5, email);
			psd.setString(6, phone);
			psd.setString(7, code);
			psd.setString(8, promo);
			psd.setString(9, priceWithoutPromo);
			psd.setString(10, priceWithPromo);
			psd.setInt(11, 0);
			psd.execute();
			
			LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int startYear  = localDate.getYear();
			int startMonth = localDate.getMonthValue();
			int startDay   = localDate.getDayOfMonth();
			
			LocalDate localEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int endYear  = localEnd.getYear();
			int endMonth = localEnd.getMonthValue();
			int endDay   = localEnd.getDayOfMonth();
			
			//email confirmation 
			Mailer mailer = new Mailer();
			String newline = "<br/>";
			mailer.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "info@sartopartners.com", "Booking Confirmation",
					"Hi " + firstName + "," + newline + "Thanks for choosing Ranch on the Rocks! Your request has been approved, and you have an upcoming reservation on " + startMonth+"/"+startDay+"/"+startYear + " until " + endMonth+"/"+endDay+"/"+endYear + ". You have 24 hours to pay, or else your trip will be cancelled!" + newline + "Your Trip Payment Code is: " + code + "." + newline + "Please visit https://ranchontherocks.com/paymentCode.jsp#" + code + " to pay. Your code will already be filled in for you!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//remove from temp_dates
		PreparedStatement tsd;
		try {
			tsd = con.prepareStatement("delete from temp_dates where startDate = ? and endDate = ?");
			java.sql.Date startDatesqlT = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesqlT = new java.sql.Date(endDate.getTime());
			tsd.setDate(1, startDatesqlT);
			tsd.setDate(2, endDatesqlT);
			tsd.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//remove from booking_req
		PreparedStatement bsd;
		try {
			bsd = con.prepareStatement("delete from booking_req where startDate = ? and endDate = ?");
			java.sql.Date startDatesqlB = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesqlB = new java.sql.Date(endDate.getTime());
			bsd.setDate(1, startDatesqlB);
			bsd.setDate(2, endDatesqlB);
			bsd.execute();
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/Pricing").forward(request, response);
		
	}

}
