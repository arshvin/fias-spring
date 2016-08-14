package medved.fias.storage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import medved.fias.content.DataStorage;
import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.mappers.DataMapper;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.AddrObjSearchImpl;
import medved.fias.storage.repositories.HouseJpaRepository;
import medved.fias.storage.repositories.HouseSearchImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
    @Autowired
    private DataMapper dataMapper;


    @Override
    public void putAddrObj(AddressObjects.Object addressObject) {

        addrObjRepository.save(dataMapper.schemaToDomain(addressObject));

    }

    @Override
    public void putHouse(Houses.House house) {

        houseRepository.save(dataMapper.schemaToDomain(house));

    }

    @Override
    public Data getObjectsBy(UUID uuid) {
        AddrObj addrObj = addrObjRepository.findByAoGuid(uuid);
        if (addrObj == null) {
            House house = houseRepository.findByHouseGuid(uuid);
            if (house != null){
                return dataMapper.domainToData(house);
            }
        }
        else{
            return dataMapper.domainToData(addrObj);
        }

        return null;

    }

    @Override
    public List<Data> getObjectsBy(String content, Pageable pageable) {

        List<Data> addrObjList = FluentIterable.from(addrObjSearch.findByContent(content,pageable,null))
                .transform(new Function<AddrObj, Data>() {
                    @Override
                    public Data apply(AddrObj input) {
                        return dataMapper.domainToData(input);
                    }
                }).toList();
        List<Data> housesList = FluentIterable.from(houseSearch.findByContent(content,pageable,null))
                .transform(new Function<House, Data>() {
                    @Override
                    public Data apply(House input) {
                        return dataMapper.domainToData(input);
                    }
                }).toList();
        List result = Lists.newLinkedList(addrObjList);
        result.addAll(housesList);

        return result;


    }

    @Override
    public List<Data> getAddrObjAll(Pageable pageable) {
        return FluentIterable.from(addrObjRepository.findAll()).transform(new Function<AddrObj, Data>() {
            @Override
            public Data apply(AddrObj input) {
                return dataMapper.domainToData(input);
            }
        }).toList();
    }

    @Override
    public List<Data> getHousesAll(Pageable pageable) {
        return FluentIterable.from(houseRepository.findAll()).transform(new Function<House, Data>() {
            @Override
            public Data apply(House input) {
                return dataMapper.domainToData(input);
            }
        }).toList();
    }

    @Override
    public Long getAddrObjCount() {

        return addrObjRepository.count();

    }

    //TODO: Implement the method getHousesCount()
    @Override
    public Long getHousesCount() {

        return houseRepository.count();

    }
}
