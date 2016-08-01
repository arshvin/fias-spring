package medved.fias.scheduling;

import com.google.common.collect.Maps;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by arshvin on 27.07.16.
 */

public class SimpleScheduler implements Scheduler {

    @Autowired
    private JobStorage jobStorage;

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private org.quartz.Scheduler scheduler;

    public SimpleScheduler() {

        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    @Override
    public void initialize() {

        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    @Override
    public void shutdown() {

        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createJob(JobData job) {

        jobStorage.saveJob(job);

    }

    @Override
    public void changeJob(JobData job) {

        jobStorage.saveJob(job);

    }

    @Override
    public void eraseJob(Long id) {

        JobData job = jobStorage.getBy(id);
        jobStorage.removeJob(job);

    }

    @Override
    public JobData showJobDetails(Long id) {

        return jobStorage.getBy(id);

    }

    @Override
    public Map<Long, String> listJobs() {

        LinkedHashMap<Long, String> map = new LinkedHashMap<>();

        for( JobData job: jobStorage.getAll()){
            map.put(job.getId(),job.getName());
        }

        return map;

    }

    @Override
    public void startJob(Long id) {

    }

    @Override
    public void stopJob(Long id) {

    }
}
