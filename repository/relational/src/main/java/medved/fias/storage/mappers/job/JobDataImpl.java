package medved.fias.storage.mappers.job;

import medved.fias.scheduling.JobData;

import java.util.Map;

/**
 * Created by arshvin on 21.08.16.
 */
public class JobDataImpl implements JobData {

    private Long id;
    private Class clazz;
    private String name;
    private Map<String, String> config;
    private String schedule;
    private Boolean active;

    public JobDataImpl() {
    }

    public JobDataImpl(Long id, Class clazz, String name, Map<String, String> config, String schedule, Boolean active) {
        this.id = id;
        this.clazz = clazz;
        this.name = name;
        this.config = config;
        this.schedule = schedule;
        this.active = active;
    }

    public void setId(Long id){
        this.id = id;
    };

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class getClazz() {
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
