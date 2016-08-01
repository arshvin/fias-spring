package medved.fias.scheduling;

import java.util.Map;

/**
 * Created by arshvin on 27.07.16.
 */
public interface Scheduler {
    void initialize();
    void shutdown();

    void createJob(JobData job);
    void changeJob(JobData job);
    void eraseJob(Long id);
    JobData showJobDetails(Long id);
    Map<Long, String> listJobs();

    void startJob(Long id);
    void stopJob(Long id);
}
