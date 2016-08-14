package medved.fias.storage.repositories;

import medved.fias.storage.domain.House;
import medved.fias.storage.searchkit.AbstractQueryBuilder;
import medved.fias.storage.searchkit.FullTextSearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by arshvin on 14.08.16.
 */
public class HouseSearchImpl extends AbstractQueryBuilder implements FullTextSearchable<House> {

    @Autowired
    public HouseSearchImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<House> findByContent(String content, Pageable pageable, String... fields) {
        return buildQuery(content, pageable, fields).execute();
    }
}
