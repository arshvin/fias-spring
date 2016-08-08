package medved.fias.scheduling;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by arshvin on 09.07.16.
 */
public interface JobStorage {
    void saveJob(JobData jobData);
    void removeJob(JobData jobData);
    JobData getBy(Long id);
    List<JobData> getBy(Class clazz);
    List<JobData> getAll(Pageable pageable);
    Long getCount();
}
