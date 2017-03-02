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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (id != null ? !id.equals(job.id) : job.id != null) return false;
        if (className != null ? !className.equals(job.className) : job.className != null) return false;
        if (name != null ? !name.equals(job.name) : job.name != null) return false;
        if (schedule != null ? !schedule.equals(job.schedule) : job.schedule != null) return false;
        if (config != null ? !config.equals(job.config) : job.config != null) return false;
        return active != null ? active.equals(job.active) : job.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", name='" + name + '\'' +
                ", schedule='" + schedule + '\'' +
                ", config=" + config +
                ", active=" + active +
                '}';
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
