package medved.fias.storage.searchkit;

import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by arshvin on 13.08.16.
 */
public interface FullTextSearchable<T> {
    List<T> findByContent(String content, Pageable pageable,String ...fields);
}
