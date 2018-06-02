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
		
		
		//this is insecure - I have to calculate on server :(
		//int price = Integer.parseInt(request.getParameter("hiddenPrice"));
		//price *= 100;
		boolean promot = false;
		String startDateStr = request.getParameter("hiddenStartDate");
		String endDateStr = request.getParameter("hiddenEndDate");
		String firstName = request.getParameter("hiddenFirstName");
		String lastName = request.getParameter("hiddenLastName");
		String phone = request.getParameter("hiddenpNumber");
		String email = request.getParameter("hiddenEmail");
		String promo = null;
		if(!request.getParameter("promo").isEmpty()) {
			promo = request.getParameter("promo");
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//give error code 2 if dates already exist in DB
				
				//get all dates between start and end
				 List<Date> dates = new ArrayList<Date>();
				    Calendar calendar = new GregorianCalendar();
				    calendar.setTime(startDate);
				    java.util.Calendar addCal = java.util.Calendar.getInstance();
					addCal.setTime(endDate);
					addCal.add(java.util.Calendar.DATE, 1);  // number of days to add
					Date realEnd = addCal.getTime();  // dt is now the new date

				    while (calendar.getTime().before(realEnd))
				    {
				        Date result = calendar.getTime();
				        dates.add(result);
				        calendar.add(Calendar.DATE, 1);
				    }
		
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
					
					//compare local price list with prices from DB to calculate a total
					//lets check the list
					int price = 0;
				    for(int i = 0;i < dates.size(); i++) {
				    	//fix
				    	if(priceAndDate.containsKey(dates.get(i))) {
				    		price += priceAndDate.get(dates.get(i));
				    	}
				    }
		
		price *= 100;
		int disc = 0;
		int discount = 0;
		if(promot == true) {
			//apply promo
			try {
				PreparedStatement pp = con.prepareStatement("SELECT * FROM promos WHERE code =?");
				pp.setString(1, promo);
				ResultSet rs = pp.executeQuery();
				while(rs.next()) {
					disc = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    discount = disc/100;
		}
		
		price = price * discount;
		int deposit = price/2;
	    int cleaning = 100;
	    
	    int totalPrice = price + deposit + cleaning;
		
		// Set your secret key: remember to change this to your live secret key in production
		// See your keys here: https://dashboard.stripe.com/account/apikeys
		Stripe.apiKey = "sk_test_5sP8eowPH6zWy1KZUBC43Zmn";

		// Token is created using Checkout or Elements!
		// Get the payment token ID submitted by the form:
		String token = request.getParameter("stripeToken");

		// Charge the user's card:
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amount", totalPrice);
		params.put("currency", "usd");
		params.put("description", "Example charge");
		params.put("source", token);
		
		//create a random Confirmation Id for this trip
		String confirmationId = UUID.randomUUID().toString().replaceAll("-", "");

	
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
			//actually book dates
			PreparedStatement psd;
			try {
				psd = con.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
				java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
				java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
				psd.setDate(1, startDatesql);
				psd.setDate(2, endDatesql);
				psd.setString(3, firstName);
				psd.setString(4, lastName);
				psd.setString(5, email);
				psd.setString(6, phone);
				psd.setString(7, confirmationId);
				psd.execute();
				
				//email confirmation 
				Mailer mailer = new Mailer();
				mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Cancellation Request",
						"You have booked Ranch on the Rocks! Your trip confirmation ID is: " + confirmationId + ".");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
			//invalidate session
			HttpSession session = request.getSession();  
			session.invalidate();
			
			request.setAttribute("price", price/100);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			request.getRequestDispatcher("success.jsp").forward(request, response);
		}
		else if(!chargeWorked) {
			//remove from temp dates
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
			//forward to error
			int errorCode = 1;
			request.setAttribute("errorCode", errorCode);
	    	request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		
	}
	

}
