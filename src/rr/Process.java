package rr;

import java.io.IOException;
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
		String promo = null;
		if(!request.getParameter("hiddenPromo").isEmpty()) {
			promo = request.getParameter("hiddenPromo");
			promot = true;
		}
		
		SimpleDateFormat formatter4=new SimpleDateFormat("yyyy-MM-dd");
		
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
					
		//get price from DB
		int price = 0;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from dates where confirmationId = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				price = Integer.parseInt(rs.getString("priceWithPromo"));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		price *= 100;
    
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = "sk_test_5sP8eowPH6zWy1KZUBC43Zmn";

		// Token is created using Checkout or Elements!
		// Get the payment token ID submitted by the form:
		String token = request.getParameter("stripeToken");

		// Charge the user's card:
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", price);
		params.put("currency", "usd");
		params.put("description", "Example charge");
		params.put("source", token);
		
		boolean chargeWorked = true;
		try {
			//fixed?
			Charge charge = Charge.create(params);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			chargeWorked = false;
		}
		
		if(chargeWorked) {
			
			//email confirmation 
			Mailer mailer = new Mailer();
			String newline = "<br/>";
			mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Ranch on the Rocks Payment Confirmed",
						"Hi " + firstName + "," + newline + "thank you for your payment of $"+price/100+"! Your payment code is now your cancellation code. Please visit https://ranchontherocks.com/cancel.jsp if you decide to cancel.");
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
