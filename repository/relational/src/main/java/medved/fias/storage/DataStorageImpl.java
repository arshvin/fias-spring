package medved.fias.storage;

import com.google.common.collect.Lists;
import medved.fias.content.DataStorage;
import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
@Component
public class DataStorageImpl implements DataStorage {
    @Override
    public void putAddrObj(AddressObjects.Object addressObject) {

    }

    //TODO: Implement the method putHouse()
    @Override
    public void putHouse(Houses.House house) {

    }

    //TODO: Implement the method getObjectBy()
    @Override
    public Data getObjectBy(UUID uuid) {

        return new medved.fias.storage.Data("I'm returned by UUID","I'm orphan", Lists.newArrayList("first","second","third"));

    }

    //TODO: Implement the method getObjectBy()
    @Override
    public Data getObjectBy(String content) {

        return new medved.fias.storage.Data("I'm returned by TEXT","I'm orphan", Lists.newArrayList("first","second","third"));

    }

    //TODO: Implement the method getAddrObjAll()
    @Override
    public List<Data> getAddrObjAll(Pageable pageable) {
        return null;
    }

    //TODO: Implement the method getHousesAll()
    @Override
    public List<Data> getHousesAll(Pageable pageable) {
        return null;
    }

    //TODO: Implement the method getAddrObjCount()
    @Override
    public Long getAddrObjCount() {
        return null;
    }

    //TODO: Implement the method getHousesCount()
    @Override
    public Long getHousesCount() {
        return null;
    }
}
