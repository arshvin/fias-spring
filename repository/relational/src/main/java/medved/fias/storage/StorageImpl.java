package medved.fias.storage;

import com.google.common.collect.Lists;
import medved.fias.interchange.Storage;
import medved.fias.interchange.StorageData;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
@Component
public class StorageImpl implements Storage {
    @Override
    public void pushAddr(AddressObjects.Object addressObject) {

    }

    @Override
    public void pushHouse(Houses.House house) {

    }

    @Override
    public StorageData getById(UUID uuid) {

        return new Data("I'm returned by UUID","I'm orphan", Lists.newArrayList("first","second","third"));

    }

    @Override
    public StorageData getByText(String text) {

        return new Data("I'm returned by TEXT","I'm orphan", Lists.newArrayList("first","second","third"));

    }
}
