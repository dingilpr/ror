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
		int inq = 0;
		int priceWithoutPromo = 0;
		int priceWithPromo = 0;
		int deposit = 0;
		int paid = 0;
		int dPaid = 0;
		boolean expired = false;
		boolean alreadyPaid = false;
		boolean alreadyDPaid = false;
		String trimmedCode = id.substring(id.lastIndexOf("?")+1);
		System.out.println("trimmedCode: " + trimmedCode);
		Long timeStamp = Long.parseLong(trimmedCode);
		if ((System.currentTimeMillis() - timeStamp) >= (60*60*24*1000)) { //add 24 between 60 and 1000
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
				email = rs.getString("email");
				priceWithoutPromo = Integer.parseInt(rs.getString("priceWithoutPromo"));
				priceWithPromo = Integer.parseInt(rs.getString("priceWithPromo"));
				deposit = Integer.parseInt(rs.getString("deposit"));
				paid = rs.getInt("paid");
				dPaid = rs.getInt("depositPaid");
				inq = rs.getInt("inquiry");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		   
		//forward it all
	    if((expired == false) && (priceWithPromo > 0) && (paid < 1)) {
			request.setAttribute("startDate", startDate.toString());
			request.setAttribute("endDate", endDate.toString());
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("pNumber", pNumber);
			request.setAttribute("email", email);
			request.setAttribute("price", priceWithPromo);
			request.setAttribute("promo", promo);
			request.setAttribute("code", id);
			request.setAttribute("inquiry", inq);
			request.getRequestDispatcher("payment.jsp").forward(request, response);
	    }
	    else if(paid == 1 && dPaid == 0) {
	    	request.setAttribute("startDate", startDate.toString());
			request.setAttribute("endDate", endDate.toString());
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("pNumber", pNumber);
			request.setAttribute("email", email);
			request.setAttribute("price", deposit);
			request.setAttribute("promo", promo);
			request.setAttribute("code", id);
			request.setAttribute("deposit", "true");
			request.getRequestDispatcher("payment.jsp").forward(request, response);
	    }
	    else {
	    	String startDatestr = startDate.toString();
	    	String endDatestr = endDate.toString();
	    	request.setAttribute("startDate", startDatestr);
			request.setAttribute("endDate", endDatestr);
			request.setAttribute("firstName", firstName);
			request.setAttribute("lastName", lastName);
			request.setAttribute("pNumber", pNumber);
			request.setAttribute("email", email);
			request.setAttribute("price", priceWithPromo);
			request.setAttribute("promo", promo);
			request.setAttribute("code", id);
			request.getRequestDispatcher("/HandleExpiration").forward(request, response);
	    }
	}

}
