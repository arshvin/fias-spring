package medved.fias.storage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import medved.fias.scheduling.JobData;
import medved.fias.scheduling.JobStorage;
import medved.fias.storage.domain.Job;
import medved.fias.storage.mappers.exceptions.EntityToJobMapperException;
import medved.fias.storage.mappers.exceptions.JobToEntityMapperException;
import medved.fias.storage.mappers.job.JobMapperImpl;
import medved.fias.storage.repositories.JobsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by arshvin on 05.08.16.
 */
@Component
public class JobStorageImpl implements JobStorage {
    @Autowired
    JobsJpaRepository repository;

    JobMapperImpl jobMapper;

    @Override
    public JobData saveJob(JobData jobData) {
        try {
            Job job = jobMapper.jobToEntity(jobData);
            job = repository.save(job);
            return jobMapper.entityToJob(job);
        } catch (JobToEntityMapperException e) {
            e.printStackTrace();
        } catch (EntityToJobMapperException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeJob(JobData jobData) {
        try {
            Job job = jobMapper.jobToEntity(jobData);
            repository.delete(job);
        } catch (JobToEntityMapperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        repository.deleteAll();
    }

    @Override
    public JobData getBy(Long id) {
        JobData jobData = null;
        try {
            Job job = repository.findById(id);
            jobData = jobMapper.entityToJob(job);
        } catch (EntityToJobMapperException e) {
            e.printStackTrace();
        }
        return jobData;
    }

    @Override
    public List<JobData> getBy(String className) {
        List<JobData> result = FluentIterable.from(repository.findByClassName(className)).transform(new Function<Job, JobData>() {
            @Override
            public JobData apply(Job input) {
                JobData jobData = null;
                try {
                    jobData = jobMapper.entityToJob(input);
                } catch (EntityToJobMapperException e) {
                    e.printStackTrace();
                }
                return jobData;
            }
        }).toList();
        return result;
    }

    @Override
    public List<JobData> getAll(Pageable pageable) {
        return FluentIterable.from(repository.findAll(pageable)).transform(new Function<Job, JobData>() {
            @Override
            public JobData apply(Job input) {
                JobData jobData = null;
                try {
                    jobData = jobMapper.entityToJob(input);
                } catch (EntityToJobMapperException e) {
                    e.printStackTrace();
                }
                return jobData;
            }
        }).toList();
    }

    @Override
    public Long getCount() {
        return repository.count();
    }
}
