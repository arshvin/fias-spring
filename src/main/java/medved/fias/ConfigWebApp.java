package medved.fias;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by arshvin on 28.05.16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "medved.fias.web")
public class ConfigWebApp {

}
