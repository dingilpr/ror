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
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FulfillBookRequest
 */
@WebServlet("/FulfillBookRequest")
public class FulfillBookRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FulfillBookRequest() {
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
		String startDateStr = request.getParameter("hiddenStartDate");
		String endDateStr = request.getParameter("hiddenEndDate");
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String people = request.getParameter("ppl");
		String promo = null;
		Boolean promot = false;
		if(!request.getParameter("promo").isEmpty()) {
			promo = request.getParameter("promo");
			promot = true;	
		}
		
		//create a random Confirmation Id for this trip
		String confirmationId = UUID.randomUUID().toString().replaceAll("-", "");
		String code = confirmationId + "?" + System.currentTimeMillis();
		
		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd HH:mm:ssz yyyy");
		
		//reformat dates sent from JSP
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(startDateStr);
			endDate = formatter4.parse(endDateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
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
		
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
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
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//compare local price list with prices from DB to calculate a total
		int price = 0;
		for(int i = 0;i < dates.size(); i++) {
			//fix
			if(priceAndDate.containsKey(dates.get(i))) {
				price += priceAndDate.get(dates.get(i));
			}
		}
		
		//get discount
		int disc = 0;
		double discount = 0;
		if(promot == true) {
			//apply promo
			try {
				PreparedStatement pp = con.prepareStatement("SELECT * FROM promos WHERE code =?");
				pp.setString(1, promo);
				ResultSet rs = pp.executeQuery();
				while(rs.next()) {
					disc = rs.getInt("discount");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    discount = (double)disc/100;
		}
		
		//int deposit = price/2;
	    int cleaning = 199;
	    double totalMath = 0;
	    
	    int totalPrice = price + cleaning;
	    //apply promo
	    totalMath = (double)totalPrice - ((double)totalPrice * discount);
	    totalPrice = (int)totalMath;
		
		//insert all into booking_req
		PreparedStatement psd;
		try {
			psd = con.prepareStatement("insert into booking_req(startDate, endDate, firstName, lastName, email, phone, confirmationId, promo, priceWithoutPromo, priceWithPromo)" + "values (?,?,?,?,?,?,?,?,?,?)");
			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
			psd.setDate(1, startDatesql);
			psd.setDate(2, endDatesql);
			psd.setString(3, firstName);
			psd.setString(4, lastName);
			psd.setString(5, email);
			psd.setString(6, phone);
			psd.setString(7, code);
			if(promo != null) {
				psd.setString(8, promo);
			}
			else {
				psd.setString(8, null);
			}
			psd.setString(9, Integer.toString(price));
			psd.setString(10, Integer.toString(totalPrice));
			psd.execute();
			
			//email confirmation 
			Mailer mailer = new Mailer();
			mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Booking Requested!",
					"Your booking request is being reviewed! You will hear back shortly. Your cancellation code is: " + confirmationId + ". If you "
							+ "decide to cancel, please visit https://ranchontherocks.com/cancelRequest.jsp and enter your cancellation code.");
			
			//email confirmation 
			Mailer mailerTwo = new Mailer();
			mailerTwo.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Booking Request Received.",
					"Someone has requested to book Ranch on the Rocks from " + startDateStr + " until " + endDateStr + " for $" + totalPrice + ". Please visit https://ranchontherocks.com.login.jsp and use Username: admin Password: jsarto to approve or deny.");
			
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
		
		request.setAttribute("price", totalPrice);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.getRequestDispatcher("requestSuccess.jsp").forward(request, response);
	}

}
