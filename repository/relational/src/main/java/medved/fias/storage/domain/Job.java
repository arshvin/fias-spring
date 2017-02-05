package medved.fias.storage.domain;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by arshvin on 14.08.16.
 */
@Entity
public class Job{

    @Id
    private Long id;

    @Column(name = "INSTANCE_OF", nullable = false)
    private String className;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "SCHEDULE", nullable = false)
    private String schedule;

    @ElementCollection
    @CollectionTable(name = "JOB_CONFIG")
    @MapKeyColumn(name = "KEY")
    @Column(name = "VALUE")
    private Map<String,String> config;

    @Column(name = "ACTIVE")
    private Boolean active;

    public Job() {}

    public Job(Long id, String className, String name, String schedule, Map<String, String> config, Boolean active) {
        this.id = id;
        this.className = className;
        this.name = name;
        this.schedule = schedule;
        this.config = config;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
