package medved.fias.interchange;

import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;

import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
public interface Storage {
    void pushAddr(AddressObjects.Object addressObject);
    void pushHouse(Houses.House house);
    StorageData getById(UUID uuid);
    StorageData getByText(String text);
}
