package medved.dsl;

import medved.domain.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Created by arshvin on 31.10.15.
 */
@Component
public class HouseQuerySuite extends QuerySuite<House> {

    @Autowired
    public HouseQuerySuite(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }
}
