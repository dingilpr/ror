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
 * Servlet implementation class UndoHold
 */
@WebServlet("/UndoHold")
public class UndoHold extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UndoHold() {
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
		
		String start = request.getParameter("startUnhold");
		String end = request.getParameter("endUnhold");
		
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
		
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement ps = con.prepareStatement("DELETE FROM temp_dates WHERE startDate=? AND endDate=?");
			java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
			java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
			ps.setDate(1, startDatesql);
			ps.setDate(2, endDatesql);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/Pricing");
	}

}
