package medved.fias;

import lombok.Data;

import java.util.Map;

/**
 * Created by arshvin on 25.06.16.
 */
public class AppConfig implements ApplicationEnvironment{

    @Override
    public void initialize() {

    }
    //TODO: implement the method getStorageConfig()
    @Override
    public Map getStorageConfig() {
        return null;
    }

    //TODO: implement the method getJobsConfig()
    @Override
    public Map getJobsConfig() {
        return null;
    }

    //TODO: implement the method getUsersConfig()
    @Override
    public Map getUsersConfig() {
        return null;
    }
}
