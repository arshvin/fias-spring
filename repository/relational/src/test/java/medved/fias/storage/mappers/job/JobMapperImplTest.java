package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import medved.fias.storage.domain.Job;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


import java.util.HashMap;

/**
 * Created by arshvin on 26.01.17.
 */

/**We don't care about absence of container context. It's needed
 *  testing the component by itself.
 */
@Ignore("For best time")
public class JobMapperImplTest {

    private JobMapper mapper = new JobMapperImpl();

    @Test
    public void testJobToEntity() throws Exception {
        JobData jobData1 = new JobDataImpl(
                (long) 1,
                "SomeName.class",
                "Some simple job#1",
                new HashMap<String, String>(),
                "* * * * *",
                false
        );


        JobData jobData2 = new JobDataImpl();
        ((JobDataImpl) jobData2).setId((long) 2);
        jobData2.setClassName("Blah.class");
        jobData2.setName("Some simple job#2");

        HashMap<String,String> jobConfig1 = new HashMap<>();
        jobConfig1.put("key1","value1");
        jobConfig1.put("key2","value2");
        jobConfig1.put("key3","value3");

        jobData2.setConfig(jobConfig1);
        jobData2.setSchedule("* * * * *");
        jobData2.setActive(true);


        JobData jobData3 = new JobDataImpl();

        HashMap<String,String> jobConfig2 = new HashMap<>();
        jobConfig1.put("key1","value1");
        jobConfig1.put("key2","value2");
        jobConfig1.put("key3","value3");

        jobData3.setConfig(jobConfig2);


        Job job1 = mapper.jobToEntity(jobData1);
        Job job2 = mapper.jobToEntity(jobData2);
        Job job3 = mapper.jobToEntity(jobData3);

        Assert.assertEquals(job1.getClassName(),jobData1.getClazz());
        Assert.assertEquals(job1.getName(),jobData1.getName());
        Assert.assertEquals(job1.getConfig(),jobData1.getConfig());
        Assert.assertEquals(job1.getId(), jobData1.getId());
        Assert.assertEquals(job1.getActive(), jobData1.getActive());
        Assert.assertEquals(job1.getSchedule(), jobData1.getSchedule());

        Assert.assertEquals(job2.getClassName(),jobData2.getClazz());
        Assert.assertEquals(job2.getName(),jobData2.getName());
        Assert.assertEquals(job2.getConfig(),jobData2.getConfig());
        Assert.assertEquals(job2.getId(), jobData2.getId());
        Assert.assertEquals(job2.getActive(), jobData2.getActive());
        Assert.assertEquals(job2.getSchedule(), jobData2.getSchedule());

        Assert.assertEquals(job3.getClassName(),jobData3.getClazz());
        Assert.assertEquals(job3.getName(),jobData3.getName());
        Assert.assertEquals(job3.getConfig(),jobData3.getConfig());
        Assert.assertEquals(job3.getId(), jobData3.getId());
        Assert.assertEquals(job3.getActive(), jobData3.getActive());
        Assert.assertEquals(job3.getSchedule(), jobData3.getSchedule());
    }

    @Test
    public void testEntityToJob() throws Exception {

        Job job11 = new Job(
                (long) 10,
                "SomeName.class",
                "Some simple job#10",
                "* * * * *",
                new HashMap<String, String>(),
                false
        );

        Job job12 = new Job();
        job12.setId((long) 11);
        job12.setName("Some simple job#11");
        job12.setActive(true);

        JobData jobData11 = mapper.entityToJob(job11);
        JobData jobData12 = mapper.entityToJob(job12);

        Assert.assertEquals(job11.getClassName(),jobData11.getClazz());
        Assert.assertEquals(job11.getName(),jobData11.getName());
        Assert.assertEquals(job11.getConfig(),jobData11.getConfig());
        Assert.assertEquals(job11.getId(), jobData11.getId());
        Assert.assertEquals(job11.getActive(), jobData11.getActive());
        Assert.assertEquals(job11.getSchedule(), jobData11.getSchedule());

        Assert.assertEquals(job12.getClassName(),jobData12.getClazz());
        Assert.assertEquals(job12.getName(),jobData12.getName());
        Assert.assertEquals(job12.getConfig(),jobData12.getConfig());
        Assert.assertEquals(job12.getId(), jobData12.getId());
        Assert.assertEquals(job12.getActive(), jobData12.getActive());
        Assert.assertEquals(job12.getSchedule(), jobData12.getSchedule());

    }
}