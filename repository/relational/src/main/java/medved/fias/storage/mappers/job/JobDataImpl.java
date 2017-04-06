package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by arshvin on 21.08.16.
 */
public class JobDataImpl implements JobData {

    private static Logger logger = LoggerFactory.getLogger("JobDataImpl");

    private Long id;
    private String className;
    private String name;
    private Map<String, String> config;
    private String schedule;
    private Boolean active;

    public JobDataImpl() {
    }

    public JobDataImpl(Long id, String clazz, String name, Map<String, String> config, String schedule, Boolean active) {
        this.id = id;
        this.className = clazz;
        this.name = name;
        this.config = config;
        this.schedule = schedule;
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JobDataImpl jobData = (JobDataImpl) o;

        if (id != null ? !id.equals(jobData.id) : jobData.id != null){
            logger.debug("{} and {} don't equal by 'id'", this, jobData);
            return false;
        }

        if (className != null ? !className.equals(jobData.className) : jobData.className != null){
            logger.debug("{} and {} don't equal by 'clasName'", this, jobData);
            return false;
        }

        if (name != null ? !name.equals(jobData.name) : jobData.name != null) {
            logger.debug("{} and {} don't equal by 'name'", this, jobData);
            return false;
        }

        if (config != null ? !config.equals(jobData.config) : jobData.config != null) {
            logger.debug("{} and {} don't equal by 'config'", this, jobData);
            return false;
        }

        if (schedule != null ? !schedule.equals(jobData.schedule) : jobData.schedule != null){
            logger.debug("{} and {} don't equal by 'schedule'", this, jobData);
            return false;
        }

        return active != null ? active.equals(jobData.active) : jobData.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobDataImpl{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", name='" + name + '\'' +
//                ", config=" + config +
                ", schedule='" + schedule + '\'' +
                ", active=" + active +
                '}';
    }

    public void setId(Long id){
        this.id = id;
    };

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public Map<String, String> getConfig() {
        return config;
    }

    @Override
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public String getSchedule() {
        return schedule;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }
}
