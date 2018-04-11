package rr;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.DateBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import java.util.Date;

public class EmailJob {
	 /**
     * @param args
     */
     public void run() throws Exception {
            // First we must get a reference to a scheduler
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();


            // computer a time that is on the next round minute
            Date runTime = evenMinuteDate(new Date());


            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(QuartzJob.class)
                .withIdentity("job1", "group1")
                .build();

            // Trigger the job to run on the next round minute
            Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runTime)
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .build();

            // Tell quartz to schedule the job using our trigger
            sched.scheduleJob(job, trigger);

            // Start up the scheduler (nothing can actually run until the 
            // scheduler has been started)
            sched.start();


            // wait long enough so that the scheduler as an opportunity to 
            // run the job!
            try {
                // wait 65 seconds to show job
                Thread.sleep(65L * 1000L); 
                // executing...
            } catch (Exception e) {
            }

            // shut down the scheduler
            sched.shutdown(true);
        }

        public static void main(String[] args) throws Exception {

           EmailJob example = new EmailJob();
           example.run();
        }
	
}
