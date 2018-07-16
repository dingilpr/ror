package rr;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//pre process serve list to admin jsp
				JSONArray list=new JSONArray();
				JSONArray dates=new JSONArray();
				
			    //connect to DB
				DBManager db = new DBManager();
				Connection con = db.getConnection();
				if(con == null){
					System.out.println("failed");
				}
				else{
					System.out.println("success ");
				}
				
				//get data from DB
				PreparedStatement ps;
				try {
					ps = con.prepareStatement("select * from pricing");
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						Date date = rs.getDate("date");
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						String dateString = df.format(date);
						String price = Integer.toString(rs.getInt("price"));
						list.add(dateString);
						list.add(price);
					}
					System.out.println("ArrayList to send back: " + list);
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				PreparedStatement dps;
				try {
					dps = con.prepareStatement("select * from dates");
					ResultSet drs = dps.executeQuery();
					while(drs.next()) {
						Date startDate = drs.getDate("startDate");
						Date endDate = drs.getDate("endDate");
						DateFormat ddf = new SimpleDateFormat("yyyy-MM-dd");
						String stringStart = ddf.format(startDate);
						String stringEnd = ddf.format(endDate);
						dates.add(stringStart);
						dates.add(stringEnd);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//check if session exists(if it does, someone pressed back from checkout, as that is the only place a session is created)
				HttpSession session = request.getSession(false);
				if(session != null) {
					//cancel listener will listen for this attribute to be added, and remove the dates from temp_dates (to free them up)
					String startDate = (String)session.getAttribute("startDate");
					String endDate = (String)session.getAttribute("endDate");
					ArrayList<String> obj = new ArrayList<String>();
					obj.add(startDate);
					obj.add(endDate);
			
					session.setAttribute("pressedBack", obj);
				}
				
				
				
				
				request.setAttribute("list", list);
				request.setAttribute("dates", dates);
				request.getRequestDispatcher("indexTesting.jsp").forward(request, response);
				
				response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
