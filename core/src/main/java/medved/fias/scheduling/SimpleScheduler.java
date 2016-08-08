package medved.fias.scheduling;

import medved.fias.ApplicationEnvironment;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.data.domain.Pageable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by arshvin on 27.07.16.
 */

@Component
public class SimpleScheduler implements Scheduler {

    @Autowired
    private JobStorage jobStorage;

    @Autowired
    private ApplicationEnvironment appConf;

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private org.quartz.Scheduler scheduler;

    public SimpleScheduler() {

        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    //TODO: implement the method initialize()
    @PostConstruct
    @Override
    public void initialize() {

        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    //TODO: implement the method shutdown()
    @PreDestroy
    @Override
    public void shutdown() {

        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    //TODO: implement the method createJob()
    @Override
    public void createJob(JobData job) {

        jobStorage.saveJob(job);

    }

    //TODO: implement the method changeJob()
    @Override
    public void changeJob(JobData job) {

        jobStorage.saveJob(job);

    }

    //TODO: implement the method eraseJob()
    @Override
    public void eraseJob(Long id) {

        JobData job = jobStorage.getBy(id);
        jobStorage.removeJob(job);

    }

    //TODO: implement the method eraseJob()
    @Override
    public void eraseJob(JobData job) {

    }

    //TODO: implement the method showJobDetails()
    @Override
    public JobData showJobDetails(Long id) {

        return jobStorage.getBy(id);

    }

    //TODO: implement the method listJobs()
    @Override
    public Map<Long, String> listJobs(Pageable pageable) {

        LinkedHashMap<Long, String> map = new LinkedHashMap<>();

        for( JobData job: jobStorage.getAll(pageable)){
            map.put(job.getId(),job.getName());
        }

        return map;

    }

    @Override
    //TODO: implement the method startJob()
    public void startJob(Long id) {

    }

    @Override
    //TODO: implement the method stopJob()
    public void stopJob(Long id) {

    }
}
