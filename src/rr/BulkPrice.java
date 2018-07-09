package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BulkPrice
 */
@WebServlet("/BulkPrice")
public class BulkPrice extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BulkPrice() {
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
		String passedMonth = request.getParameter("month");
		String passedAmount = request.getParameter("amount");
		
		String yearStr = passedMonth.split("-")[0];
		String monthStr = passedMonth.split("-")[1];
		
		int year = Integer.parseInt(yearStr);
		int month = Integer.parseInt(monthStr);
		
		int amount = Integer.parseInt(passedAmount);
		
		List<Date> dates = new ArrayList<Date>();
		
		//get all days of month
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        while (calendar.get(Calendar.MONTH) == month) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        
        //get prices already in DB
     
      	//initialize map
      	HashMap<Date, Integer> priceAndDate = new HashMap<>();
      	
      	//connect to DB
      	DBManager db = new DBManager();
      	Connection con = db.getConnection();
      	if(con == null){
      		System.out.println("failed");
      	}
      	else{
      		System.out.println("success ");
      	}
      	
      	//get price from DB
      	PreparedStatement ps;
      	try {
      		ps = con.prepareStatement("select * from pricing");
      		ResultSet rs = ps.executeQuery();
      		while(rs.next()) {
      			Date date = rs.getDate("date");
      			Integer price = rs.getInt("price");
      			priceAndDate.put(date, price);
      		}
      		//System.out.println("ArrayList to send back: " + list);
      			
      	} catch (SQLException e) {
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      	}
        
        //add each day to DB, replace if needed
      //send new dates and prices to DB
		PreparedStatement psT;
      	for(int j = 0; j < dates.size(); j++) {
    		try {
				psT = con.prepareStatement("insert into pricing(date,price)" + "values (?,?)");
				Date date = new Date();
				
				//format date and price for the last time
				date = dates.get(j);
				
				//check if date already exists
				if(priceAndDate.containsKey(date)) {
					System.out.println("Updating new Price!");
					PreparedStatement psX = con.prepareStatement("UPDATE pricing SET date = ?, price = ? WHERE  date = ?");
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					int price = amount;
					psX.setDate(1, sqlDate);
					psX.setInt(2, price);
					psX.setDate(3, sqlDate);
					psX.execute();
				}
				
				else if(!priceAndDate.containsKey(date)) {
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());
					int price = amount;
					
					//send over
					psT.setDate(1, sqlDate);
					psT.setInt(2,  price);
					
					psT.execute();
				}
			      
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
      	}

      	response.sendRedirect("/Pricing");
	}

}
