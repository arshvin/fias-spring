package medved.fias.content;

import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;

import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
public interface DataStorage {
    void putAddrObj(AddressObjects.Object addressObject);
    void putHouse(Houses.House house);
    Data getObjectByGuid(UUID uuid);
    Data getObjectByContent(String text);
    //TODO: It's probably an mistake to return the Data object rather than list of the objects. To think
    Data getAddrObjAll();
    Data getHousesAll();
    Long getAddrObjCount();
    Long getHousesCount();

}
