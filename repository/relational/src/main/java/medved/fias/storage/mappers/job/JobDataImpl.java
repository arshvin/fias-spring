package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;

import java.util.Map;

/**
 * Created by arshvin on 21.08.16.
 */
public class JobDataImpl implements JobData {

    private Long id;
    private String clazz;
    private String name;
    private Map<String, String> config;
    private String schedule;
    private Boolean active;

    public JobDataImpl() {
    }

    public JobDataImpl(Long id, String clazz, String name, Map<String, String> config, String schedule, Boolean active) {
        this.id = id;
        this.clazz = clazz;
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

        if (id != null ? !id.equals(jobData.id) : jobData.id != null) return false;
        if (clazz != null ? !clazz.equals(jobData.clazz) : jobData.clazz != null) return false;
        if (name != null ? !name.equals(jobData.name) : jobData.name != null) return false;
        if (config != null ? !config.equals(jobData.config) : jobData.config != null) return false;
        if (schedule != null ? !schedule.equals(jobData.schedule) : jobData.schedule != null) return false;
        return active != null ? active.equals(jobData.active) : jobData.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
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
                ", clazz='" + clazz + '\'' +
                ", name='" + name + '\'' +
                ", config=" + config +
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
        this.clazz = className;
    }

    @Override
    public String getClazz() {
        return clazz;
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
