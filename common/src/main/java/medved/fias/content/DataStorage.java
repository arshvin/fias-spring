package medved.fias.content;

import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
public interface DataStorage {
    void putAddrObj(AddressObjects.Object addressObject);
    void putHouse(Houses.House house);
    Data getObjectBy(UUID uuid);
    Data getObjectBy(String content);
    List<Data> getAddrObjAll(Pageable pageable);
    List<Data> getHousesAll(Pageable pageable);
    Long getAddrObjCount();
    Long getHousesCount();

}
