package medved.fias.storage.repositories;

import medved.fias.storage.domain.Job;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by arshvin on 14.08.16.
 */
@Repository
@Transactional
public interface JobsJpaRepository extends PagingAndSortingRepository<Job,Long>{
    Job findById(Long id);
    Job findByName(String name);
    List<Job> findByClassName(String className);
}
