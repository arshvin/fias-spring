package medved.fias.storage;

import medved.fias.content.Data;
import medved.fias.scheduling.JobData;
import medved.fias.storage.mappers.job.JobDataImpl;
import org.apache.commons.logging.LogFactory;
import org.hamcrest.CoreMatchers;
import org.hibernate.PropertyValueException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by arshvin on 01.02.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ConfigStorage.class})
public class JobStorageImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobStorageImpl jobStorage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @After
    public void cleanJobs(){
        jobStorage.removeAll();
    }

    @Test
    public void testSaveJob1() {

        JobData jobData1 = new JobDataImpl();
        jobData1.setSchedule("* * * * *");
        jobData1.setName("Job#1");
        jobData1.setClassName("SomeJob.class");

        HashMap<String, String> config = new HashMap<>();
        config.put("property#1", "value#1");
        config.put("property#2", "value#2");
        config.put("property#3", "value#3");
        config.put("property#4", "value#4");
        jobData1.setConfig(config);
        jobStorage.saveJob(jobData1);

        JobData jobData2 = new JobDataImpl();
        jobData2.setSchedule("* * * * *");
        jobData2.setName("Job#2");
        jobData2.setClassName("SomeJob2.class");
        jobStorage.saveJob(jobData2);
    }

    @Test
    public void testSaveJob2() {
        JobData jobData3 = new JobDataImpl();
        jobData3.setName("Job#3");
        jobData3.setClassName("SomeJob3.class");
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage(CoreMatchers.startsWith("not-null property references a null or transient value : medved.fias.storage.domain.Job.schedule"));
        jobStorage.saveJob(jobData3);
    }

    @Test
    public void testSaveJob3() {
        JobData jobData4 = new JobDataImpl();
        jobData4.setName("Job#4");
        jobData4.setSchedule("* * * * *");
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage(CoreMatchers.startsWith("not-null property references a null or transient value : medved.fias.storage.domain.Job.className"));
        jobStorage.saveJob(jobData4);
    }

    @Test
    public void testSaveJob4() {
        JobData jobData5 = new JobDataImpl();
        jobData5.setClassName("SomeJob5.class");
        jobData5.setSchedule("* * * * *");
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage(CoreMatchers.startsWith("not-null property references a null or transient value : medved.fias.storage.domain.Job.name"));
        jobStorage.saveJob(jobData5);
    }

    @Test
    public void testSaveJob5() {
        JobData jobData2 = new JobDataImpl();
        jobData2.setSchedule("* * * * *");
        jobData2.setName("Job#2");
        jobData2.setClassName("SomeJob2.class");
        jobStorage.saveJob(jobData2);

        JobData jobData6 = new JobDataImpl();
        jobData6.setName("Job#2");
        jobData6.setClassName("SomeJob5.class");
        jobData6.setSchedule("* * * * *");
        thrown.expect(DataIntegrityViolationException.class);
        thrown.expectMessage(CoreMatchers.startsWith("could not execute statement; SQL [n/a]; constraint"));
        jobStorage.saveJob(jobData6);

    }

    @Test
    public void testRemoveJob() {
        JobData jobData1 = new JobDataImpl();
        jobData1.setSchedule("* * * * *");
        jobData1.setName("Job#1");
        jobData1.setClassName("SomeJob.class");

        HashMap<String, String> config = new HashMap<>();
        config.put("property#1", "value#1");
        config.put("property#2", "value#2");
        config.put("property#3", "value#3");
        config.put("property#4", "value#4");
        jobData1.setConfig(config);

        JobData jobData2 = jobStorage.saveJob(jobData1);
        jobStorage.removeJob(jobData2);

        Assert.assertEquals(Long.valueOf(0), jobStorage.getCount());
    }

    @Test
    public void testGetBy() {
        JobData jobData1 = new JobDataImpl();
        jobData1.setSchedule("* * * * *");
        jobData1.setName("Job#1");
        jobData1.setClassName("SomeJob.class");
        jobData1 = jobStorage.saveJob(jobData1);

        JobData jobData2 = new JobDataImpl();
        jobData2.setSchedule("* * * * *");
        jobData2.setName("Job#2");
        jobData2.setClassName("SomeJob.class");
        jobData2 = jobStorage.saveJob(jobData2);

        JobData jobData3 = new JobDataImpl();
        jobData3.setSchedule("* * * * *");
        jobData3.setName("Job#3");
        jobData3.setClassName("SomeJob.class");
        jobData3 = jobStorage.saveJob(jobData3);

        JobData jobData11 = jobStorage.getBy(jobData1.getId());
        JobData jobData21 = jobStorage.getBy(jobData2.getId());
        JobData jobData31 = jobStorage.getBy(jobData3.getId());

        Assert.assertEquals(jobData1, jobData11);
        Assert.assertEquals(jobData2, jobData21);
        Assert.assertEquals(jobData3, jobData31);

        List<JobData> jobs = jobStorage.getBy("SomeJob.class");
        Assert.assertEquals(3, jobs.size());
        Assert.assertTrue(jobs.contains(jobData1));
        Assert.assertTrue(jobs.contains(jobData2));
        Assert.assertTrue(jobs.contains(jobData3));
    }

    @Test
    public void testGetAll() {

        List<JobData> jobs = new LinkedList();

        for (int i=0;i<20;i++){
            JobData jobData = new JobDataImpl();
            jobData.setName(String.format("Job#%d", i));
            jobData.setClassName("SomeClass.class");
            jobData.setSchedule("* * * * *");
            jobs.add(jobStorage.saveJob(jobData));
        }

        List<JobData> jobList1 = jobStorage.getAll(new PageRequest(1,3));
        List<JobData> jobList2 = jobStorage.getAll(new PageRequest(2,6));
        List<JobData> jobList3 = jobStorage.getAll(new PageRequest(3,5));

        Assert.assertEquals(jobs.get(3), jobList1.get(0));
        Assert.assertEquals(jobs.get(4), jobList1.get(1));
        Assert.assertEquals(jobs.get(5), jobList1.get(2));

        Assert.assertEquals(jobs.get(12), jobList2.get(0));
        Assert.assertEquals(jobs.get(15), jobList2.get(3));
        Assert.assertEquals(jobs.get(17), jobList2.get(5));

        Assert.assertEquals(jobs.get(15), jobList3.get(0));
        Assert.assertEquals(jobs.get(16), jobList3.get(1));
        Assert.assertEquals(jobs.get(19), jobList3.get(4));


    }

    @Test
    public void testGetCount() {
        JobData jobData1 = new JobDataImpl();
        jobData1.setSchedule("* * * * *");
        jobData1.setName("Job#1");
        jobData1.setClassName("SomeJob.class");
        jobData1 = jobStorage.saveJob(jobData1);

        JobData jobData2 = new JobDataImpl();
        jobData2.setSchedule("* * * * *");
        jobData2.setName("Job#2");
        jobData2.setClassName("SomeJob.class");
        jobData2 = jobStorage.saveJob(jobData2);

        JobData jobData3 = new JobDataImpl();
        jobData3.setSchedule("* * * * *");
        jobData3.setName("Job#3");
        jobData3.setClassName("SomeJob.class");
        jobData3 = jobStorage.saveJob(jobData3);

        Assert.assertEquals(Long.valueOf(3), jobStorage.getCount());
    }

}