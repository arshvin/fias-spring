package medved.fias.admin.config;

import medved.fias.ConfigCore;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by arshvin on 28.05.16.
 */
public class AdminAppInitiaizer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {ConfigCore.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ConfigAdminApp.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
