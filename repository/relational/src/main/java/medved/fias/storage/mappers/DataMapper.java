package medved.fias.storage.mappers;

import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;

/**
 * Created by arshvin on 28.05.16.
 */
public interface DataMapper {
    AddrObj schemaToDomain(AddressObjects.Object addrObj);
    House schemaToDomain(Houses.House houseObj);
    medved.fias.storage.mappers.Data domainToData(AddrObj addrObj);
    medved.fias.storage.mappers.Data domainToData(House houseObj);
}
