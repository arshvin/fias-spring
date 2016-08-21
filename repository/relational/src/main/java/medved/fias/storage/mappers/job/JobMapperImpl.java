package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import medved.fias.storage.domain.Job;
import medved.fias.storage.mappers.exceptions.EntityToJobMapperException;
import medved.fias.storage.mappers.exceptions.JobToEntityMapperException;
import medved.fias.storage.repositories.JobsJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by arshvin on 21.08.16.
 */
@Component
//FIXME:Probably it could be static class
public class JobMapperImpl implements JobMapper  {
    @Override
    public Job jobToEntity(JobData jobData) throws JobToEntityMapperException {
        Job job = new Job();
        job.setId(jobData.getId());
        job.setClazz(jobData.getClazz());
        job.setName(jobData.getName());
        job.setConfig(jobData.getConfig());

        return job;
    };

    @Override
    public JobData entityToJob(Job job) throws EntityToJobMapperException {
        JobData jobData = new JobDataImpl();
        ((JobDataImpl) jobData).setId(job.getId());
        jobData.setClazz(job.getClazz());
        jobData.setName(job.getName());
        jobData.setConfig(job.getConfig());

        return jobData;
    }

}
