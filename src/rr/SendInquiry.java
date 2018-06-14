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
 * Servlet implementation class SendInquiry
 */
@WebServlet("/SendInquiry")
public class SendInquiry extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendInquiry() {
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
		String startDateStr = request.getParameter("hiddenStartDateThree");
		String endDateStr = request.getParameter("hiddenEndDateThree");
		String message = request.getParameter("message");
		String email = request.getParameter("email");
		
		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd yyyy"); 
		
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
		
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement("insert into inquiries (startDate, endDate, message, email)" + "values(?,?,?,?)" );
			ps.setDate(1, new java.sql.Date(startDate.getTime()));
			ps.setDate(2, new java.sql.Date(endDate.getTime()));
			ps.setString(3, message);
			ps.setString(4, email);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		
	}
}
