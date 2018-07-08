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
 * Servlet implementation class Calculate
 */
@WebServlet("/Calculate")
public class Calculate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Calculate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String startDateStr = request.getParameter("hiddenStartDate");
		String endDateStr = request.getParameter("hiddenEndDate");
		String firstName = request.getParameter("fname");
		String lastName = request.getParameter("lname");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String people = request.getParameter("ppl");
		String promo = null;
		Boolean promot = false;
		if (!request.getParameter("promo").isEmpty()) {
			promo = request.getParameter("promo");
			promot = true;
		}

		SimpleDateFormat formatter4 = new SimpleDateFormat("E MMM dd HH:mm:ssz yyyy");

		// reformat dates sent from JSP
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = formatter4.parse(startDateStr);
			endDate = formatter4.parse(endDateStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// get all dates between start and end
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		java.util.Calendar addCal = java.util.Calendar.getInstance();
		addCal.setTime(endDate);
		addCal.add(java.util.Calendar.DATE, 1); // number of days to add
		Date realEnd = addCal.getTime(); // dt is now the new date
		int dayCounter = 0;

		while (calendar.getTime().before(realEnd)) {
			dayCounter++;
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}

		// connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();

		HashMap<Date, Integer> priceAndDate = new HashMap<>();
		// get price from DB
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from pricing");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Date date = rs.getDate("date");
				Integer price = rs.getInt("price");
				priceAndDate.put(date, price);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// compare local price list with prices from DB to calculate a total
		int price = 0;
		for (int i = 0; i < dates.size() - 1; i++) {
			// fix
			if (priceAndDate.containsKey(dates.get(i))) {
				price += priceAndDate.get(dates.get(i));
				System.out.println("DATE: " + (dates.get(i) + "PRICE: " + priceAndDate.get(dates.get(i))));
			}
		}

		// get discount
		int disc = 0;
		double discount = 0;
		int memCheck = 0;
		ArrayList<String> emailAddresses = new ArrayList<String>();
		if (promot == true) {
			// apply promo
			try {
				PreparedStatement pp = con.prepareStatement("SELECT * FROM promos WHERE code =?");
				pp.setString(1, promo);
				ResultSet rs = pp.executeQuery();
				while (rs.next()) {
					disc = rs.getInt("discount");
					memCheck = rs.getInt("members");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (memCheck == 0) {
				discount = (double) disc / 100;
			}

			// else check member email
			if (memCheck == 1) {
				try {
					PreparedStatement eps = con.prepareStatement("select * from emails");
					ResultSet ers = eps.executeQuery();
					while (ers.next()) {
						String emailDB = ers.getString("email");
						emailAddresses.add(emailDB);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int x = 0; x < emailAddresses.size(); x++) {
					if (emailAddresses.get(x).equals(email)) {
						discount = (double) disc / 100;
					}
				}

			}
		}

		int deposit = price/2;
		int cleaning = 199;
		double totalMath = 0;
		int pricePerDay = price/(dayCounter - 1);
		price += cleaning;

		int totalPrice = price;
		// apply promo
		totalMath = (double) totalPrice - ((double) totalPrice * discount);
		totalPrice = (int) totalMath;
		
		
		//create a random Confirmation Id for this trip
		String confirmationId = UUID.randomUUID().toString().replaceAll("-", "");
		String code = confirmationId + "?" + System.currentTimeMillis();
		
		// insert all into booking_req
		PreparedStatement psd;
		try {
			psd = con.prepareStatement(
					"insert into booking_req(startDate, endDate, firstName, lastName, email, phone, confirmationId, promo, priceWithoutPromo, priceWithPromo, deposit)"
							+ "values (?,?,?,?,?,?,?,?,?,?,?)");
			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
			psd.setDate(1, startDatesql);
			psd.setDate(2, endDatesql);
			psd.setString(3, firstName);
			psd.setString(4, lastName);
			psd.setString(5, email);
			psd.setString(6, phone);
			psd.setString(7, code);
			if (promo != null) {
				psd.setString(8, promo);
			} else {
				psd.setString(8, null);
			}
			psd.setString(9, Integer.toString(price));
			psd.setString(10, Integer.toString(totalPrice));
			psd.setInt(11, totalPrice / 2);
			psd.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession(); 
		session.setAttribute("startDate", startDateStr);
    	session.setAttribute("endDate", endDateStr);
    	session.setMaxInactiveInterval(60);
	    request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("price", price);
		request.setAttribute("pricePerDay", pricePerDay);
		request.setAttribute("dayCounter", dayCounter - 1);
		request.setAttribute("deposit", deposit);
		request.setAttribute("cleaning", cleaning);
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("code", code);
		if(promot == true) {
			request.setAttribute("promo", disc);
		}
		request.getRequestDispatcher("confirmDetails.jsp").forward(request, response);
	}

}
