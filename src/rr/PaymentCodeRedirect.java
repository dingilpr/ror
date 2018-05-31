package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
		String price = null;
		
		
		//connect to db
		DBManager db = new DBManager();
		Connection con = db.getConnection();
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//forward it all
				request.setAttribute("startDate", startDate.toString());
				request.setAttribute("endDate", endDate.toString());
				request.setAttribute("firstName", firstName);
				request.setAttribute("lastName", lastName);
				request.setAttribute("pNumber", pNumber);
				request.setAttribute("email", email);
				request.setAttribute("price", price);
				
				request.getRequestDispatcher("payment.jsp").forward(request, response);
	}

}
