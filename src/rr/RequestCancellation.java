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

import javax.mail.*;

/**
 * Servlet implementation class RequestCancellation
 */
@WebServlet("/RequestCancellation")
public class RequestCancellation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestCancellation() {
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
		String id = request.getParameter("confirmationId");
		String email = request.getParameter("email");
		Boolean expired = false;
		
		//check confirmationId for 14 day limit
		String trimmedCode = id.substring(id.lastIndexOf("?")+1);
		Long timeStamp = Long.parseLong(trimmedCode);
		if ((System.currentTimeMillis() - timeStamp) >= (60*60*1000)) { //add 24 between 60 and 1000
			// interval is over 24 hours
			expired = true;
		} else {
			// interval is less than 24 hours
			expired = false;
		}

		if(expired = false) {
			DBManager db = new DBManager();
			Connection con = db.getConnection();
			if(con == null){
				System.out.println("failed");
			}
			else{
				System.out.println("success ");
			}
			
			PreparedStatement psd;
			try {
				psd = con.prepareStatement("insert into cancel_req(email, confirmationId)" + "values (?,?)");
				
				psd.setString(1, email);
				psd.setString(2, id);
				psd.execute();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Mailer mailer = new Mailer();
			mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Cancellation Request",
					"You have recieved a cancellation request from " + email + " for the trip " + id + ". Please visit the admin dashboard to refund and cancel this trip.");
		}
	}

}
