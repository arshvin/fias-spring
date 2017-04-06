package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import medved.fias.storage.domain.Job;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by arshvin on 26.01.17.
 */

/**We don't care about absence of container context. We will try to test
 *  the component by itself.
 */
public class JobMapperImplTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testJobToEntity() throws Exception {
        //jobData#1
        JobData jobData1 = new JobDataImpl(
                (long) 1,
                "SomeName.class",
                "Some simple job#1",
                new HashMap<String, String>(),
                "* * * * *",
                false
        );

        //jobData#2
        JobData jobData2 = new JobDataImpl();
        ((JobDataImpl) jobData2).setId((long) 2);
        jobData2.setClassName("Blah.class");
        jobData2.setName("Some simple job#2");

        HashMap<String,String> jobConfig2 = new HashMap<>();
        jobConfig2.put("key1","value1");
        jobConfig2.put("key2","value2");
        jobConfig2.put("key3","value3");

        jobData2.setConfig(jobConfig2);
        jobData2.setSchedule("2 3 * * *");
        jobData2.setActive(true);

        //jobData#3
        JobData jobData3 = new JobDataImpl();

        HashMap<String,String> jobConfig3 = new HashMap<>();
        jobConfig3.put("key1","value1");
        jobConfig3.put("key2","value2");
        jobConfig3.put("key3","value3");

        jobData3.setConfig(jobConfig3);


        //Job#1
        Job jobExpected1 = new Job(
                (long) 1,
                "SomeName.class",
                "Some simple job#1",
                "* * * * *",
                new HashMap<String,String>(),
                false);

        //Job#2
        jobConfig2 = new HashMap<>();
        jobConfig2.put("key1","value1");
        jobConfig2.put("key2","value2");
        jobConfig2.put("key3","value3");

        Job jobExpected2 = new Job();
        jobExpected2.setId((long) 2);
        jobExpected2.setName("Some simple job#2");
        jobExpected2.setClassName("Blah.class");
        jobExpected2.setSchedule("2 3 * * *");
        jobExpected2.setActive(true);
        jobExpected2.setConfig(jobConfig2);

        //Job#3
        jobConfig3 = new HashMap<>();
        jobConfig3.put("key1","value1");
        jobConfig3.put("key2","value2");
        jobConfig3.put("key3","value3");

        Job jobExpected3 = new Job();
        jobExpected3.setConfig(jobConfig3);

        //Mapping
        Job jobActual1 = JobMapperImpl.jobToEntity(jobData1);
        Job jobActual2 = JobMapperImpl.jobToEntity(jobData2);
        Job jobActual3 = JobMapperImpl.jobToEntity(jobData3);

        //Assertion
        Assert.assertEquals(jobExpected1, jobActual1);
        Assert.assertEquals(jobExpected2, jobActual2);
        Assert.assertEquals(jobExpected3, jobActual3);

    }

    @Test
    public void testEntityToJob() throws Exception {

        //Job#1
        Job job1 = new Job(
                (long) 10,
                "SomeName.class",
                "Some simple job#10",
                "* * * * *",
                new HashMap<String, String>(),
                false
        );

        //Job#2
        Job job2 = new Job();
        job2.setId((long) 11);
        job2.setName("Some simple job#11");
        job2.setActive(true);

        //jobDataExpected#1
        JobData jobDataExpected1 = new JobDataImpl(
                (long) 10,
                "SomeName.class",
                "Some simple job#10",
                new HashMap<String, String>(),
                "* * * * *",
                false
                );

        //jobDataExpected#2
        JobData jobDataExpected2 = new JobDataImpl();
        ((JobDataImpl)jobDataExpected2).setId((long) 11);
        jobDataExpected2.setName("Some simple job#11");
        jobDataExpected2.setActive(true);

        //Mapping
        JobData jobDataActual1 = JobMapperImpl.entityToJob(job1);
        JobData jobDataActual2 = JobMapperImpl.entityToJob(job2);

        Assert.assertEquals(jobDataExpected1,jobDataActual1);
        Assert.assertEquals(jobDataExpected2,jobDataActual2);
    }
}