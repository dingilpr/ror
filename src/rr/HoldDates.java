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
 * Servlet implementation class HoldDates
 */
@WebServlet("/HoldDates")
public class HoldDates extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HoldDates() {
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
		String startDateStr = request.getParameter("hiddenStartDate");
		String endDateStr = request.getParameter("hiddenEndDate");
		
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
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement td = con.prepareStatement("insert into temp_dates(startDate, endDate)" + "values (?,?)");
			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
			
			td.setDate(1, startDatesql);
			td.setDate(2, endDatesql);
			td.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("Pricing").forward(request, response);
	}

}
