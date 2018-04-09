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
 * Servlet implementation class UndoCancel
 */
@WebServlet("/UndoCancel")
public class UndoCancel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UndoCancel() {
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
		//read vars
		String confirmationId = request.getParameter("confirmationIdUndo");
		String email = request.getParameter("emailUndo");
		
		System.out.println("conf: " + confirmationId + " email: " + email);
		
		if(confirmationId == null || email == null) {
			System.out.println("NULL VARS SENT");
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
		
		
		//remove from cancelled
		try {
			PreparedStatement spd = con.prepareStatement("select * from cancelledTrips where confirmationId = ? and email = ?");
			spd.setString(1, confirmationId);
			spd.setString(2, email);
			ResultSet rs = spd.executeQuery();
			if(rs == null) {
				System.out.println("null");
			}
			while(rs.next()) {
				System.out.println("IN RESULT SET");
				Date startDate = rs.getDate("startDate");
				Date endDate = rs.getDate("endDate");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String emailRs = rs.getString("email");
				String phone = rs.getString("phone");
				String confirmationIdRs = rs.getString("confirmationId");
				
				//reformat dates...
				java.sql.Date startDatesql = new java.sql.Date(startDate.getTime());
				java.sql.Date endDatesql = new java.sql.Date(endDate.getTime());
				System.out.println("STARTDATESQL - " + startDatesql);
				System.out.println("EndDATESQL - " + endDatesql);
				
				//insert into cancelledDates for redundancy
				PreparedStatement cps = con.prepareStatement("insert into dates(startDate, endDate, firstName, lastName, email, phone, confirmationId)" + "values (?,?,?,?,?,?,?)");
				cps.setDate(1, startDatesql);
				cps.setDate(2, endDatesql);
				cps.setString(3, firstName);
				cps.setString(4, lastName);
				cps.setString(5, email);
				cps.setString(6, phone);
				cps.setString(7, confirmationId);
				cps.execute();
				
				//delete from dates
				PreparedStatement ps = con.prepareStatement("delete from  cancelledTrips where confirmationId = ? and email = ?");
				ps.setString(1, confirmationId);
				ps.setString(2, email);
				ps.execute();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		//return to booked
	

}
