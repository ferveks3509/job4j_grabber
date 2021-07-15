package quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

public class AlertRabbit {

    public static Properties readProp() {
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream("./src/main/resources/rabbit.properties")) {
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static void main(String[] args) throws Exception {
        int init = Integer.parseInt(readProp().getProperty("rabbit.interval"));
        Properties properties = readProp();
        Class.forName("org.postgresql.Driver");
        try (Connection cn = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        )) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", cn);
            JobDetail job = JobBuilder.newJob(Rabbit.class)
                    .usingJobData(data).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(init)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {

        public void execute(JobExecutionContext context) {
            System.out.println("rabbit runs here...");
            Connection cn = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (PreparedStatement statement = cn.prepareStatement("insert into rabbit(create_date) values ('20-02-21')")) {
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
