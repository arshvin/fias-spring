package medved.fias.storage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import medved.fias.scheduling.JobData;
import medved.fias.scheduling.JobStorage;
import medved.fias.storage.domain.Job;
import medved.fias.storage.mappers.exceptions.EntityToJobMapperException;
import medved.fias.storage.mappers.exceptions.JobToEntityMapperException;
import medved.fias.storage.mappers.job.JobMapper;
import medved.fias.storage.repositories.JobsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by arshvin on 05.08.16.
 */
//@Component
public class JobStorageImpl implements JobStorage {
    @Autowired
    JobsJpaRepository repository;
    @Autowired
    JobMapper jobMapper;

    @Override
    public void saveJob(JobData jobData) {
        try {
            Job job = jobMapper.jobToEntity(jobData);
            repository.save(job);
        } catch (JobToEntityMapperException e) {
            e.printStackTrace();
        }
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
    public List<JobData> getBy(Class clazz) {
//        List<JobData> result = FluentIterable.from(repository.findByClazz(clazz)).transform(new Function<Job, JobData>() {
//            @Override
//            public JobData apply(Job input) {
//                JobData jobData = null;
//                try {
//                    jobData = jobMapper.entityToJob(input);
//                } catch (EntityToJobMapperException e) {
//                    e.printStackTrace();
//                }
//                return jobData;
//            }
//        }).toList();
            List result = null;
        return result;
    }

    //TODO:Implement the pageable functionality
    @Override
    public List<JobData> getAll(Pageable pageable) {
        return FluentIterable.from(repository.findAll()).transform(new Function<Job, JobData>() {
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
