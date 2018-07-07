package rr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		//see if anyone is checking in 7 days from now
		//get date 7 days in the future
		SimpleDateFormat formatter4=new SimpleDateFormat("E MMM dd HH:mm:ssz yyyy");
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 7);
		dt = c.getTime();
		
		java.sql.Date startDatesql = new java.sql.Date(dt.getTime());
		
		//connect to DB
		DBManager db = new DBManager();
		Connection con = db.getConnection();
		if(con == null){
			System.out.println("failed");
		}
		else{ 
			System.out.println("success ");
		}
		
		//to store emails
		List<String> emails = new ArrayList<String>();
		Date startDate = null;
		Date endDate = null;
		int id = 0;
		String firstName = null;
		
		
		//query DB for checkins 7 days from now
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM dates WHERE startDate=?");
			ps.setDate(1, startDatesql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				//if(!rs.getString("firstName").equals("imported")) {
					emails.add(rs.getString("email"));
					startDate = rs.getDate("startDate");
					endDate = rs.getDate("endDate");
					id = rs.getInt("id");
					firstName = rs.getString("firstName");
					System.out.println("ID: " + rs.getInt("id"));
					System.out.println("RESULT email: " + rs.getString("email"));
					System.out.println("RESULT SD: " + rs.getString("startDate"));
					System.out.println("RESULT ED: " + rs.getString("endDate"));
				//}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!emails.isEmpty()) {
			//initialize map of prices and dates
			 HashMap<String, Date> mailAndDate = new HashMap<>();
			//check if already sent
			try {
				PreparedStatement es = con.prepareStatement("SELECT * FROM email_sent");
				ResultSet er = es.executeQuery();
				while(er.next()) {
					Date date = er.getDate("date");
					String email = er.getString("email");
				    mailAndDate.put(email, date);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!(mailAndDate.containsKey(emails.get(0)) && !(mailAndDate.get(emails.get(0)).equals(new Date())))) {
				//email confirmation 
				String newline = "<br/>";
				Mailer mailer = new Mailer();
				mailer.sendMail("smtp.gmail.com", "587", "info@sartopartners.com", "info@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Pack your bags!",
						"Hi " + firstName +  ", " + newline + "Your trip is almost here! Ranch on the Rocks can be a bit tricky to get to so please consult the " + 
								"directions below before your arrival and as usual, Please don’t hesitate to contact Joe or " + 
								"Crystal if you have trouble finding it." + newline + "When you turn onto Alameda Parkway from Morrison Road you will notice that Alameda Pkwy is " + 
										"also Entrance 1 for Red Rocks Amphitheater." 
								  + newline + "Take Alameda Pkwy for .5 mi. Pass the Dinosaur Ridge Discovery Center on the right. After " + 
								  		"passing the Discovery Center continue to go straight on Alameda Pkwy, and take note of two " + 
								  		"dirt roads on the right to follow." + newline + 
								  		"As you approach the beautiful red rocks, you will turn on the second dirt road on the right. " + 
								  		"There is no street sign. There is a green dumpster on this road (see photo). If you get to the " + 
								  		"scenic view turn off or Red Rocks Amphitheater you have gone too far." + "After turning right on this dirt road continue to stay to the right and take it ALL THE WAY TO " + 
								  				"THE END. You will go over the hill and pass a few houses, but continue on until the road dead " + 
								  				"ends. Once you hit the dead need you are at Ranch on the Rocks, 17971 Alameda Pkwy.");
				try {
					PreparedStatement us = con.prepareStatement("insert into email_sent(date, email)" + "values (?,?)");
					us.setDate(1, new java.sql.Date(new Date().getTime()));
					us.setString(2, emails.get(0));
					us.execute();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
