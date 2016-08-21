package medved.fias;

import java.util.Map;

/**
 * Created by arshvin on 05.08.16.
 */
public interface AppConfig {
    void initialize();
    Map getStorageConfig();
    Map getJobsConfig();
    Map getUsersConfig();
}
