package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Refund;

/**
 * Servlet implementation class RefundDeposit
 */
@WebServlet("/RefundDeposit")
public class RefundDeposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefundDeposit() {
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
		String confirmationId = request.getParameter("confirmationRD");
		String email = request.getParameter("emailRD");
		String stripe = null;
		
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select depositCode from dates where confirmationId = ?");
			ps.setString(1, confirmationId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				stripe = rs.getString("depositCode");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//refund
		Stripe.apiKey = "sk_test_5sP8eowPH6zWy1KZUBC43Zmn";
		
		

		Map<String, Object> refundParams = new HashMap<String, Object>();
		refundParams.put("charge", stripe);

		try {
			Refund.create(refundParams);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
				| APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreparedStatement psd;
		try {
			psd = con.prepareStatement("UPDATE dates SET depositPaid = ? WHERE  confirmationId = ?");
			psd.setInt(1, 3);
			psd.setString(2, confirmationId);
			psd.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
