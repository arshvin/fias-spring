package medved.fias;

import java.util.Map;

/**
 * Created by arshvin on 05.08.16.
 */
public interface ApplicationEnvironment {
    void initialize();
    Map getStorageConfig();
    Map getJobsConfig();
    Map getUsersConfig();
}
