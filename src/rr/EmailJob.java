package rr;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class EmailJob
 */
@WebServlet("/EmailJob")
public class EmailJob extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailJob() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		 ServletContext ctx = config.getServletContext(); 
		 StdSchedulerFactory factory = (StdSchedulerFactory) ctx
	                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY); 
   	 Scheduler sched = null;
		try {
			sched = factory.getScheduler();
		} catch (SchedulerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


           // computer a time that is on the next round minute
           Date runTime = evenMinuteDate(new Date());

           
           // define the job and tie it to our HelloJob class
           JobDetail job = newJob(QuartzJob.class)
               .withIdentity("job1", "group1")
               .build();
           
           //add airbnb job
           JobDetail jobB = newJob(SyncJob.class)
        		   .withIdentity("job2", "group1")
        		   .build();

           Trigger trigger = newTrigger()
        		    .withIdentity("trigger7", "group1")
        		    .withSchedule(simpleSchedule()
        		        .withIntervalInMinutes(2)
        		        .repeatForever())
        		    .build();
           
           Trigger triggerB = newTrigger()
       		    .withIdentity("trigger8", "group1")
       		    .withSchedule(simpleSchedule()
       		        .withIntervalInMinutes(10)
       		        .repeatForever())
       		    .build();

           // Tell quartz to schedule the job using our trigger
           try {
				sched.scheduleJob(job, trigger);
				sched.scheduleJob(jobB, triggerB);
			} catch (SchedulerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

           // Start up the scheduler (nothing can actually run until the 
           // scheduler has been started)
           try {
				sched.start();
			} catch (SchedulerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


           // wait long enough so that the scheduler as an opportunity to 
           // run the job!
           try {
               // wait 65 seconds to show job
               Thread.sleep(65L * 1000L); 
               // executing...
           } catch (Exception e) {
           }

           
           // shut down the scheduler
           try {
				sched.shutdown(true);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

}
