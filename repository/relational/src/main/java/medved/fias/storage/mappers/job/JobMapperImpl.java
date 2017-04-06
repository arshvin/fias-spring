package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import medved.fias.storage.domain.Job;
import medved.fias.storage.mappers.exceptions.EntityToJobMapperException;
import medved.fias.storage.mappers.exceptions.JobToEntityMapperException;

/**
 * Created by arshvin on 21.08.16.
 */
public class JobMapperImpl  {
    public static Job jobToEntity(JobData jobData) throws JobToEntityMapperException {

        Job job = new Job();
        job.setId(jobData.getId());
        job.setClassName(jobData.getClassName());
        job.setName(jobData.getName());
        job.setConfig(jobData.getConfig());
        job.setSchedule(jobData.getSchedule());
        job.setActive(jobData.getActive());

        return job;
    };

    public static JobData entityToJob(Job job) throws EntityToJobMapperException {

        JobData jobData = new JobDataImpl();
        ((JobDataImpl) jobData).setId(job.getId());
        jobData.setClassName(job.getClassName());
        jobData.setName(job.getName());
        jobData.setConfig(job.getConfig());
        jobData.setSchedule(job.getSchedule());
        jobData.setActive(job.getActive());

        return jobData;
    }

}
