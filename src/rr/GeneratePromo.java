package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GeneratePromo
 */
@WebServlet("/GeneratePromo")
public class GeneratePromo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratePromo() {
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
		String code = request.getParameter("promoCode");
		String disc = request.getParameter("percentOff");
		boolean membersOnly = false;
		if(request.getParameter("mo") != null) {
			membersOnly = true;
		}
		int discount = Integer.parseInt(disc);
		
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		try {
			PreparedStatement td = con.prepareStatement("insert into promos(code, discount, members)" + "values (?,?, ?)");
			
			td.setString(1, code);
			td.setInt(2, discount);
			int membersCheck = 0;
			if(membersOnly) {
				membersCheck = 1;
			}
			td.setInt(3, membersCheck);
			td.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.sendRedirect("/Pricing");
	}

}
