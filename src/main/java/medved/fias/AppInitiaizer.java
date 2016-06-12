package medved.fias;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by arshvin on 28.05.16.
 */
public class AppInitiaizer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {ConfigApp.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {ConfigWebApp.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
