package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Refund;

/**
 * Servlet implementation class FulfillCancellation
 */
@WebServlet("/FulfillCancellation")
public class FulfillCancellation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FulfillCancellation() {
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
		String confirmationId = request.getParameter("confirmationId");
		String email = request.getParameter("email");
		System.out.println("CONF: " + confirmationId);
		System.out.println("EMAIL: " + email);
		String stripeCode = null;
		
		//delete from trips, then add to cancelled trips
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{
			System.out.println("success ");
		}
		
		//select row from Dates
		try {
			PreparedStatement spd = con.prepareStatement("select * from dates where confirmationId = ? and email = ?");
			spd.setString(1, confirmationId);
			spd.setString(2, email);
			ResultSet rs = spd.executeQuery();
			if(rs == null) {
				System.out.println("null");
			}
			while(rs.next()) {
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String emailRs = rs.getString("email");
				String phone = rs.getString("phone");
				String confirmationIdRs = rs.getString("confirmationId");
				stripeCode = rs.getString("sCode");
				
				//reformat dates...
				java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
				java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
				System.out.println("STARTDATESQL - " + startDatesql);
				System.out.println("EndDATESQL - " + endDatesql);
				
				//insert into cancelledDates for redundancy
				PreparedStatement cps = con.prepareStatement("insert into cancelledTrips(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
				cps.setDate(1, startDatesql);
				cps.setDate(2, endDatesql);
				cps.setString(3, firstName);
				cps.setString(4, lastName);
				cps.setString(5, email);
				cps.setString(6, phone);
				cps.setString(7, confirmationId);
				cps.execute();
			}
			
			//refund
			Stripe.apiKey = "sk_test_5sP8eowPH6zWy1KZUBC43Zmn";
			
			System.out.println("STRIPECODE: " + stripeCode);

			Map<String, Object> refundParams = new HashMap<String, Object>();
			refundParams.put("charge", stripeCode);

			try {
				Refund.create(refundParams);
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
					| APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//email cancel confirmation
			Mailer mailer = new Mailer();
			/**
			mailer.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Cancellation Fulfilled",
					"Your trip has been succcesfully cancelled. A refund will post to your account within 5-10 days. Please email info@sartopartners.com with any further questions.");
			**/
			//delete from dates
			PreparedStatement ps = con.prepareStatement("delete from  dates where confirmationId = ? and email = ?");
			ps.setString(1, confirmationId);
			ps.setString(2, email);
			ps.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/Pricing");
		
		
	}

}
