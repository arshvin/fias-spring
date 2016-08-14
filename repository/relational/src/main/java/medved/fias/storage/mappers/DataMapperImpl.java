package medved.fias.storage.mappers;

import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import org.springframework.stereotype.Component;

/**
 * Created by arshvin on 13.08.16.
 */
@Component
//TODO: Implement the methods
public class DataMapperImpl implements DataMapper {
    @Override
    public AddrObj schemaToDomain(AddressObjects.Object addrObj) {
        return null;
    }

    @Override
    public House schemaToDomain(Houses.House houseObj) {
        return null;
    }

    @Override
    public Data domainToData(AddrObj addrObj) {
        return null;
    }

    @Override
    public Data domainToData(House houseObj) {
        return null;
    }
}
