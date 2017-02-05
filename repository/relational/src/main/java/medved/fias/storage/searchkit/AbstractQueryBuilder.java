package medved.fias.storage.searchkit;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by arshvin on 13.08.16.
 */
public class AbstractQueryBuilder<T>{

    private final FullTextEntityManager fullTextEntityManager;
    private final Class<T> clazz;
    private FullTextQuery currentQuery;
    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass().getName());

    public AbstractQueryBuilder(EntityManagerFactory entityManagerFactory) {
        logger.debug("GenericSuperclass is {}", getClass().getGenericSuperclass());
        logger.debug("ActualTypeArguments are \'{}\'", String.valueOf(((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()));

        clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        EntityManager em = entityManagerFactory.createEntityManager();
        fullTextEntityManager = Search.getFullTextEntityManager(em);
    }

    public AbstractQueryBuilder<T> buildQuery(String query, Pageable pageable, String... fields){
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(clazz).get();
        Query preparedQuery1 = queryBuilder.keyword().onFields(fields)
                .matching(query).createQuery();

        currentQuery = fullTextEntityManager.createFullTextQuery(preparedQuery1,clazz);
        currentQuery.setFirstResult(pageable.getOffset());
        currentQuery.setMaxResults(pageable.getPageSize());
        return this;
    };

    public List<T> execute() {
        return currentQuery.getResultList();
    };

    public int getResultSize(){
        return currentQuery.getResultSize();
    };

}
