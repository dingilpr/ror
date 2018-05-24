package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DenyRequest
 */
@WebServlet("/DenyRequest")
public class DenyRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DenyRequest() {
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
		//Collect variables
		String start = request.getParameter("startDeny");
		String end = request.getParameter("endDeny");
		String reason = request.getParameter("reasonDeny");
		
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
		
		//get info from booking_reqs
		PreparedStatement isd;
		String email = null;
		String firstName = null;
		String lastName = null;
		try {
			isd = con.prepareStatement("delete from booking_req where startDate = ? and endDate = ?");
			java.sql.Date startDatesqlB = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesqlB = new java.sql.Date(endDate.getTime());
			isd.setDate(1, startDatesqlB);
			isd.setDate(2, endDatesqlB);
			ResultSet rsd= isd.executeQuery();
			while(rsd.next()) {
				email = rsd.getString("email");
				firstName = rsd.getString("firstName");
				lastName = rsd.getString("lastName");
			}
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Remove from booking_reqs
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
		
		//Email
		Mailer mailer = new Mailer();
		mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Booking Request Denied",
				"Your booking request has been denied with the following message: " + reason);
		
		
	}

}
