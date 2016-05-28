package medved.fias;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by arshvin on 28.05.16.
 */
@EnableWebMvc
@ComponentScan(basePackages = "medved.fias.web")
public class ConfigCore {

}
