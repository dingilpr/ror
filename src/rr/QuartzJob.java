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
		
		//query DB for checkins 7 days from now
		try {
			PreparedStatement ps = con.prepareStatement("SELECT email FROM dates WHERE startDate=?");
			ps.setDate(1, startDatesql);
			ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()) {
				emails.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!emails.isEmpty()) {
		//email confirmation 
			Mailer mailer = new Mailer();
			mailer.sendMail("smtp.gmail.com", "587", "pdingilian@sartopartners.com", "pdingilian@sartopartners.com", "Sarto Partners", "pdingilian@sartopartners.com", "Pack your bags!",
					"Your trip to Ranch on the Rocks begins a week from today!");
		}
		
	}

}
