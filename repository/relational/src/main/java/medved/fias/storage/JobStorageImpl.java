package medved.fias.storage;

import medved.fias.scheduling.JobData;
import medved.fias.scheduling.JobStorage;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by arshvin on 05.08.16.
 */
//TODO: IMPLEMENT AND TEST ALL METHODS
public class JobStorageImpl implements JobStorage {
    @Override
    public void saveJob(JobData jobData) {

    }

    @Override
    public void removeJob(JobData jobData) {

    }

    @Override
    public JobData getBy(Long id) {
        return null;
    }

    @Override
    public List<JobData> getBy(Class clazz) {
        return null;
    }

    @Override
    public List<JobData> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Long getCount() {
        return null;
    }
}
