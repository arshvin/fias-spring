package medved.fias.scheduling;

import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by arshvin on 27.07.16.
 */
public interface Scheduler {
    void initialize();
    void shutdown();

    void createJob(JobData job);
    void changeJob(JobData job);
    void eraseJob(JobData job);
    void eraseJob(Long id);
    JobData showJobDetails(Long id);
    Map<Long, String> listJobs(Pageable pageable);

    void startJob(Long id);
    void stopJob(Long id);
}
