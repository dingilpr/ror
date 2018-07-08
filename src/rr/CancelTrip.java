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
 * Servlet implementation class CancelTrip
 */
@WebServlet("/CancelTrip")
public class CancelTrip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelTrip() {
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
		String startDateStr = request.getParameter("hiddenCancelStartDate");
		String endDateStr = request.getParameter("hiddenCancelEndDate");
		
        SimpleDateFormat formatter4=new SimpleDateFormat("EEE MMM dd yyyy");
		
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
				if(con == null){
					System.out.println("failed");
				}
				else{
					System.out.println("success ");
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
				
				request.setAttribute("errorCode", 5);
		    	request.getRequestDispatcher("error.jsp").forward(request, response);
	}

}
