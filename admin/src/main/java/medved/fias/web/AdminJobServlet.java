package medved.fias.web;

import jdk.nashorn.internal.scripts.JO;
import medved.fias.scheduling.JobData;
import medved.fias.scheduling.JobStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by arshvin on 16.04.17.
 */
@RestController("/admin/jobs")
public class AdminJobServlet {

    @Autowired
    private JobStorage jobStorage;

    @GetMapping("/all")
    public List<JobData> getAllJobs(Integer page, Integer size){

        page = (page > 0) ? page : 0;
        //TODO:take out this hardcodded setting to the config file (property file)
        size = (size > 0) ? size : 50;

        return jobStorage.getAll(new PageRequest(page, size));
    }

    @GetMapping("details/{id}")
    public JobData getDetailOf(@PathVariable Long id){
        return jobStorage.getBy(id);
    }

    @GetMapping("details/class/{name}")
    public List<JobData> getDetailsOf(@PathVariable String className){
        return jobStorage.getBy(className);
    }

    @PostMapping
    public ResponseEntity<JobData> updateJob(JobData jobData) {
        JobData jobData1 = jobStorage.saveJob(jobData);
        if ( jobData1 == null ){
            return new ResponseEntity(HttpStatus.INSUFFICIENT_STORAGE);
        }
        else{
            return new ResponseEntity(jobData1, HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<JobData> deleteJob(JobData jobData){
        jobStorage.removeJob(jobData);
        //TODO: Actually we don't know that is all right. We need to track the some exceptions bubbled up from underlying levels
        return new ResponseEntity<JobData>(HttpStatus.OK);
    }

}

