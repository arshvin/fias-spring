package medved.fias.storage.repositories;

import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.searchkit.AbstractQueryBuilder;
import medved.fias.storage.searchkit.FullTextSearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by arshvin on 13.08.16.
 */
public class AddrObjSearchImpl extends AbstractQueryBuilder implements FullTextSearchable<AddrObj> {

    @Autowired
    public AddrObjSearchImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public List<AddrObj> findByContent(String content, Pageable pageable, String ...fields) {
        return buildQuery(content, pageable, fields).execute();
    }
}
