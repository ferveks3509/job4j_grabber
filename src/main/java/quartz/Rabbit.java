package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Rabbit implements Job {
    private Connection cn;

    public void execute(JobExecutionContext context){
        System.out.println("rabbit runs here...");
        List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
        store.add(System.currentTimeMillis());
        try (PreparedStatement statement = cn.prepareStatement("insert into rabbit(create_date) values ('20-02-21')")) {
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
