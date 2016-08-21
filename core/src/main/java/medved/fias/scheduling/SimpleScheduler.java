package medved.fias.scheduling;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import medved.fias.AppConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.data.domain.Pageable;

import java.util.AbstractMap;
import java.util.Map;

import static java.lang.String.format;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by arshvin on 27.07.16.
 */

@Component
public class SimpleScheduler implements Scheduler {

    @Autowired
    private JobStorage jobStorage;

    @Autowired
    //Environments for connection to storage???
    private AppConfig appConf;

    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private org.quartz.Scheduler scheduler;

    public SimpleScheduler() {
        try {
            scheduler = schedulerFactory.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void pushJobToScheduler(JobData jobData) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap(jobData.getConfig());
        JobDetail job = newJob(jobData.getClazz())
                .withIdentity(jobData.getName())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

        scheduler.addJob(job,true);

        String triggerId = format("%s-%d", "trigger_job", jobData.getId());

        if(jobData.getActive()){
            Trigger trigger = newTrigger()
                    .withIdentity(triggerId)
                    .startNow()
                    .withSchedule(cronSchedule(jobData.getSchedule()))
                    .forJob(job)
                    .build();
            scheduler.scheduleJob(trigger);
        } else {
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerId));
        }
    };

    @PostConstruct
    @Override
    public void initialize() {

        Pageable bigPage = new PageRequest(1, jobStorage.getCount().intValue());
        FluentIterable.from(jobStorage.getAll(bigPage)).transform(new Function<JobData, String>() {
            @Override
            public String apply(JobData input) {

                try {
                    pushJobToScheduler(input);
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });

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
    /** TODO:Implement updating jobs algorithm (persist and reload form storage to scheduler) if the it is changed by user
     *  through anyone of method: createJob changeJob eraseJob**/
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
    public void eraseJob(JobData job) {

        jobStorage.removeJob(job);

    }

    @Override
    public JobData showJobDetails(Long id) {

        return jobStorage.getBy(id);

    }

    @Override
    public Map<Long, String> listJobs(Pageable pageable) {

        Map map = FluentIterable.from(jobStorage.getAll(pageable)).toMap(new Function<JobData, Map.Entry<Long,String>>() {
            @Override
            public Map.Entry<Long, String> apply(JobData input) {
                return new AbstractMap.SimpleEntry<Long, String>(input.getId(),input.getName());
            }
        });

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
