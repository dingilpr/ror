package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class HandleExpiration
 */
@WebServlet("/HandleExpiration")
public class HandleExpiration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleExpiration() {
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
		// TODO Auto-generated method stub
		String startDateStr = request.getAttribute("startDate").toString();
		
		String endDateStr = request.getAttribute("endDate").toString();
		
		String firstName = request.getAttribute("firstName").toString();
		String lastName = request.getAttribute("lastName").toString();
		String pNumber = request.getAttribute("pNumber").toString();
		
		String email = request.getAttribute("email").toString();
		String confirmationId = request.getAttribute("code").toString();
		
		
		//
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
		//			
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		//
		try {
			//insert into cancelledDates for redundancy
			PreparedStatement cps = con.prepareStatement("insert into cancelledTrips(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
			cps.setDate(1, new java.sql.Date(startDate.getTime()));
			cps.setDate(2, new java.sql.Date(endDate.getTime()));
			cps.setString(3, firstName);
			cps.setString(4, lastName);
			cps.setString(5, email);
			cps.setString(6, pNumber);
			cps.setString(7, confirmationId);
			cps.execute();
			
			//remove from dates
			PreparedStatement ps = con.prepareStatement("delete from dates where startDate = ? and endDate = ? and firstName = ? and lastName = ?");
			ps.setDate(1, new java.sql.Date(startDate.getTime()));
			ps.setDate(2, new java.sql.Date(endDate.getTime()));
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//email confirmation 
		request.setAttribute("errorCode", 10);
    	request.getRequestDispatcher("error.jsp").forward(request, response);
		
	}

}
