package medved.dsl;

import medved.domain.AddrObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by arshvin on 31.10.15.
 */
@Component
public class AddrObjQuerySuite extends QuerySuite<AddrObj> {

    @Autowired
    public AddrObjQuerySuite(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
