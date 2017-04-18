package medved.fias.storage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import medved.fias.content.Data;
import medved.fias.content.DataStorage;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.mappers.data.DataMapper;
import medved.fias.storage.mappers.data.DataMapperImpl;
import medved.fias.storage.mappers.exceptions.DomainToDataMapperException;
import medved.fias.storage.mappers.exceptions.MapperException;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.AddrObjSearchImpl;
import medved.fias.storage.repositories.HouseJpaRepository;
import medved.fias.storage.repositories.HouseSearchImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 23.05.16.
 */
@Component
public class DataStorageImpl implements DataStorage {
    @Autowired
    private AddrObjJpaRepository addrObjRepository;
    @Autowired
    private AddrObjSearchImpl addrObjSearch;
    @Autowired
    private HouseJpaRepository houseRepository;
    @Autowired
    private HouseSearchImpl houseSearch;

    private DataMapper dataMapper = new DataMapperImpl(
            addrObjRepository,
            houseRepository
    );

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void putAddrObj(AddressObjects.Object addressObject) {

        try {
            AddrObj addrObj = dataMapper.schemaToDomain(addressObject);
            addrObjRepository.save(addrObj);
        }
        catch(MapperException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void putHouse(Houses.House houseObject) {

        try{
            House house = dataMapper.schemaToDomain(houseObject);
            houseRepository.save(house);
        }
        catch(MapperException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Data getObjectBy(UUID uuid) {
        AddrObj addrObj = addrObjRepository.findByAoGuid(uuid);
        if (addrObj == null) {
            House house = houseRepository.findByHouseGuid(uuid);
            if (house != null){
                try {
                    return dataMapper.domainToData(house);
                } catch (DomainToDataMapperException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            try {
                return dataMapper.domainToData(addrObj);
            } catch (DomainToDataMapperException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    @Override
    public List<Data> getObjectsBy(String content, Pageable pageable) {

        List<Data> addrObjList = FluentIterable.from(addrObjSearch.findByContent(content,pageable, (String[]) null))
                .transform(new Function<AddrObj, Data>() {
                    @Override
                    public Data apply(AddrObj input) {
                        try {
                            return dataMapper.domainToData(input);
                        } catch (DomainToDataMapperException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).toList();
        List<Data> housesList = FluentIterable.from(houseSearch.findByContent(content,pageable, (String[]) null))
                .transform(new Function<House, Data>() {
                    @Override
                    public Data apply(House input) {
                        try {
                            return dataMapper.domainToData(input);
                        } catch (DomainToDataMapperException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }).toList();
        List result = Lists.newLinkedList(addrObjList);
        result.addAll(housesList);

        return result;


    }

    //TODO: Implement me
    @Override
    public List<Data> getChildrenOf(UUID uuid, Pageable pageable) {
        return null;
    }

    @Override
    public List<Data> getAddrObjAll(Pageable pageable) {
        return FluentIterable.from(addrObjRepository.findAll()).transform(new Function<AddrObj, Data>() {
            @Override
            public Data apply(AddrObj input) {
                try {
                    return dataMapper.domainToData(input);
                } catch (DomainToDataMapperException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).toList();
    }

    @Override
    public List<Data> getHousesAll(Pageable pageable) {
        return FluentIterable.from(houseRepository.findAll()).transform(new Function<House, Data>() {
            @Override
            public Data apply(House input) {
                try {
                    return dataMapper.domainToData(input);
                } catch (DomainToDataMapperException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).toList();
    }

    @Override
    public Long getAddrObjCount() {

        return addrObjRepository.count();

    }

    @Override
    public Long getHousesCount() {

        return houseRepository.count();

    }
}
