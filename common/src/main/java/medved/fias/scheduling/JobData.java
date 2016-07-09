package medved.fias.scheduling;

import java.util.Map;

/**
 * Created by arshvin on 09.07.16.
 */
public interface JobData {
    void setClazz(Class clazz);
    Class getClazz();
    void setName(String name);
    String getName();
    void setConfig(Map config);
    Map getConfig();
}
