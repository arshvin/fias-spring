package medved.fias.storage;

import com.google.common.collect.Lists;
import medved.fias.content.DataStorage;
import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
@Component
public class DataStorageImpl implements DataStorage {
    @Override
    public void putAddrObj(AddressObjects.Object addressObject) {

    }

    @Override
    public void putHouse(Houses.House house) {

    }

    @Override
    public Data getObjectByGuid(UUID uuid) {

        return new medved.fias.storage.Data("I'm returned by UUID","I'm orphan", Lists.newArrayList("first","second","third"));

    }

    @Override
    public Data getObjectByContent(String text) {

        return new medved.fias.storage.Data("I'm returned by TEXT","I'm orphan", Lists.newArrayList("first","second","third"));

    }

    @Override
    public Data getAddrObjAll() {
        return null;
    }

    @Override
    public Data getHousesAll() {
        return null;
    }

    @Override
    public Long getAddrObjCount() {
        return null;
    }

    @Override
    public Long getHousesCount() {
        return null;
    }
}
