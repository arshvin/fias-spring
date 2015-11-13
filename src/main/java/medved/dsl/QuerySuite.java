package medved.dsl;

import medved.domain.AddrObj;
import medved.domain.House;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by arshvin on 18.10.15.
 */
public class QuerySuite<T> {

    private final FullTextEntityManager fullTextEntityManager;
    private Class<T> clazz;

    private Query currentQuery;
    private FullTextQuery hibernateQuery;

    public QuerySuite(EntityManagerFactory entityManagerFactory) {
        clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (clazz != AddrObj.class && clazz != House.class){
            throw new IllegalArgumentException("Parameterized type must be AddrObj or House");
        }
        EntityManager em = entityManagerFactory.createEntityManager();
        fullTextEntityManager = Search.getFullTextEntityManager(em);
    }
//    public QuerySuite(EntityManager entityManager) {
//        clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//        if (clazz != AddrObj.class && clazz != House.class){
//            throw new IllegalArgumentException("Parameterized type must be AddrObj or House");
//        }
//        fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//    }
    //"formalName", "officialName", "postalCode"
    public QuerySuite<T> simpleQuery(String query, String ...fields){
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
                .forEntity(clazz).get();
        currentQuery = queryBuilder.keyword().onFields(fields)
                .matching(query).createQuery();
        return this;
    };

    public List execute() {
        hibernateQuery = fullTextEntityManager.createFullTextQuery(currentQuery, clazz);
        return hibernateQuery.getResultList();
    };

    public int getResultSize(){
        return hibernateQuery.getResultSize();
    };
}
