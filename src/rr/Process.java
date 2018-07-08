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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


/**
 * Servlet implementation class Process
 */
@WebServlet("/Process")
public class Process extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Process() {
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
		boolean promot = false;
		String startDateStr = request.getParameter("hiddenStartDate");
		String endDateStr = request.getParameter("hiddenEndDate");
		String firstName = request.getParameter("hiddenFirstName");
		String lastName = request.getParameter("hiddenLastName");
		String phone = request.getParameter("hiddenpNumber");
		String email = request.getParameter("hiddenEmail");
		String id = request.getParameter("hiddenCode");
		String depositCheck = "false";
		if(request.getParameter("hiddenDepositCheck") != null) {
			depositCheck = request.getParameter("hiddenDepositCheck");
		}
		String promo = null;
		if(!request.getParameter("hiddenPromo").isEmpty()) {
			promo = request.getParameter("hiddenPromo");
			promot = true;
		}
		
		SimpleDateFormat formatter4=new SimpleDateFormat("EEE MMM dd yyyy");
		
		//reformat dates sent from JSP
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(startDateStr);
			endDate = formatter4.parse(endDateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
					
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		int deposit = 0;
		boolean dep = false;
		
		if(depositCheck.equals("true")) {
			deposit = 0;
			PreparedStatement pd;
			//get deposit price from DB
			try {
				pd = con.prepareStatement("select * from dates where confirmationId = ?");
				pd.setString(1, id);
				ResultSet rd = pd.executeQuery();
				while(rd.next()) {
					deposit = Integer.parseInt(rd.getString("deposit"));
				}
				
				dep = true;
				deposit *= 100;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		int price = 0;
		if(dep == false) {
			//get price from DB
			PreparedStatement ps;
			try {
				ps = con.prepareStatement("select * from booking_req where confirmationId = ?");
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					price = Integer.parseInt(rs.getString("priceWithPromo"));
				}			
			} catch (SQLException e) {
				e.printStackTrace();
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
						
			price *= 100;
		}
    
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = "sk_test_5sP8eowPH6zWy1KZUBC43Zmn";

		// Token is created using Checkout or Elements!
		// Get the payment token ID submitted by the form:
		String token = request.getParameter("stripeToken");
		
		boolean chargeWorked = true;
		
		if(dep == false) {
			// Charge the user's card:
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", price);
			params.put("currency", "usd");
			params.put("description", "Example charge");
			params.put("source", token);
			params.put("capture", false);
			try {
				//fixed?
				Charge charge = Charge.create(params);
				String StripeCode = charge.getId();
				//insert stripe code into DB
				PreparedStatement ss = con.prepareStatement("UPDATE booking_req SET sCode = ? WHERE confirmationId = ?");
				ss.setString(1, StripeCode);
				ss.setString(2, id);
				ss.execute();
				
				LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int startYear  = localDate.getYear();
				int startMonth = localDate.getMonthValue();
				int startDay   = localDate.getDayOfMonth();
				
				LocalDate localEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int endYear  = localEnd.getYear();
				int endMonth = localEnd.getMonthValue();
				int endDay   = localEnd.getDayOfMonth();
				
				String newline = "<br/>";
				Mailer mailerThree = new Mailer();
				/**
				mailerThree.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "info@sartopartners.com", "Booking Requested!",
						"Hi " + firstName + "," + newline + newline +  "Thanks for reaching out. We've recieved your reservation request for Ranch on the Rocks from " + startMonth+"/"+startDay+"/"+startYear + 
						" through " + endMonth+"/"+endDay+"/"+endYear + " and will get back to you shortly with a confirmation. We have held the funds on your card but will not charge you until your trip has been approved. Please keep an eye out for the confirmation email as it will have more details regarding your reservation."+ newline + "Your cancellation code is: " 
								+ id + newline + "If you "
								+ "decide to cancel your request, please visit https://ranchontherocks.com/cancelRequest.jsp and enter your cancellation code. We look forward to hosting you!"
								+ newline + newline + "Best," + newline + "Joe and Crystal");
				**/
				
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
					| APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				chargeWorked = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(dep == true) {
			// Charge the user's card:
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("amount", deposit);
			params.put("currency", "usd");
			params.put("description", "Example charge");
			params.put("source", token);
						
			try {
				//fixed?
				Charge charge = Charge.create(params);
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
					| APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				chargeWorked = false;
			}
			
			PreparedStatement pfd;
			try {
				pfd = con.prepareStatement("UPDATE dates SET depositPaid = ? WHERE  confirmationId = ?");
				pfd.setInt(1, 1);
				pfd.setString(2, id);
				pfd.execute();
				
				Mailer mailerTwo = new Mailer();
				String newline = "<br/>";
				mailerTwo.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Deposit Received",
							firstName + lastName + " has paid their deposit." );
				//set paid to true
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(chargeWorked) {
			
			//email confirmation 
			Mailer mailer = new Mailer();
			String newline = "<br/>";
			mailer.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Ranch on the Rocks Payment Confirmed",
						"Hi " + firstName + "," + newline + "Thank you for your payment of $"+price/100+"! Your payment code is now your cancellation code. Please visit https://ranchontherocks.com/cancel.jsp if you decide to cancel.");
			//set paid to true
			try {
				PreparedStatement paidS = con.prepareStatement("UPDATE dates SET paid = ? WHERE confirmationId = ?");
				paidS.setInt(1, 1);
				paidS.setString(2, id);
				paidS.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//invalidate session
			HttpSession session = request.getSession();  
			session.invalidate();			
			request.setAttribute("price", price/100);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.getRequestDispatcher("success.jsp").forward(request, response);
		}
		else if(!chargeWorked) {
			//forward to error
			int errorCode = 11;
			request.setAttribute("errorCode", errorCode);
	    	request.getRequestDispatcher("error.jsp").forward(request, response);
		}	
	}
}
