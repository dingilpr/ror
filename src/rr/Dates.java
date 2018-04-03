package rr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Dates
 */
@WebServlet("/Dates")
public class Dates extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dates() {
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
		
		//first get db dates
		
		//connect to DB 
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{
			System.out.println("success ");
		}
		
		ArrayList<Date> alreadyDates = new ArrayList<Date>();
		PreparedStatement dps;
		try {
			dps = con.prepareStatement("select * from dates");
			ResultSet drs = dps.executeQuery();
			while(drs.next()) {
				Date startDate = drs.getDate("startDate");
				Date endDate = drs.getDate("endDate");
				
				alreadyDates.add(startDate);
				alreadyDates.add(endDate);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//handle sent dates
		String actions[] = request.getParameterValues("hiddenArrayField");
		Date[] finalDates = new Date[2];
		String[] splitDates = actions[0].split(",");
		boolean alreadyExists = false;
		
		
		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd yyyy"); 
		
		for(int i = 0; i < splitDates.length; i++) {
			String date = splitDates[i].substring(0, 15);
			try {
				finalDates[i] = formatter4.parse(date);
				//make sure date doesn't already exist in DB for some reason
				for(int j = 0; j < alreadyDates.size(); j++) {
					if(alreadyDates.get(i).getTime() == finalDates[i].getTime()) {
						alreadyExists = true;
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		
		System.out.println(Arrays.toString(finalDates));
		
		//send dates to DB
		if(alreadyExists == false) {
				PreparedStatement ps;
				try {
					ps = con.prepareStatement("insert into dates(startDate, endDate)" + "values (?,?)");
					java.sql.Date sqlstartDate = new java.sql.Date(finalDates[0].getTime());
					java.sql.Date sqlendDate = new java.sql.Date(finalDates[1].getTime());
					ps.setDate(1, sqlstartDate);
					ps.setDate(2, sqlendDate);
					
					ps.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		else {
			System.out.println("ALREADY EXISTS");
		}
			
	}

}
