package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import medved.fias.storage.domain.Job;
import medved.fias.storage.mappers.exceptions.EntityToJobMapperException;
import medved.fias.storage.mappers.exceptions.JobToEntityMapperException;

/**
 * Created by arshvin on 21.08.16.
 */
public interface JobMapper {
    Job jobToEntity(JobData jobData) throws JobToEntityMapperException;
    JobData entityToJob(Job job) throws EntityToJobMapperException;
}
