package medved.fias.scheduling;

import java.util.Map;

/**
 * Created by arshvin on 09.07.16.
 */
public interface JobData {
    Long getId();
    void setClassName(String className);
    String getClazz();
    void setName(String name);
    String getName();
    void setConfig(Map<String,String> config);
    Map<String,String> getConfig();
    void setSchedule(String schedule);
    String getSchedule();
    void setActive(Boolean active);
    Boolean getActive();
}
