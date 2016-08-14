package medved.fias.storage.mappers;

import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;

/**
 * Created by arshvin on 28.05.16.
 */
public interface DataMapper {
    AddrObj schemaToDomain(AddressObjects.Object addrObj) throws SchemaToDomainMapperException;
    House schemaToDomain(Houses.House houseObj) throws SchemaToDomainMapperException;
    Data domainToData(AddrObj addrObj) throws DomainToDataMapperException;
    Data domainToData(House houseObj) throws DomainToDataMapperException;
}
