package medved.fias.storage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import medved.fias.content.Data;
import medved.fias.content.DataStorage;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.mappers.*;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.AddrObjSearchImpl;
import medved.fias.storage.repositories.HouseJpaRepository;
import medved.fias.storage.repositories.HouseSearchImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private class DataMapperImpl implements DataMapper {

        @Override
        public AddrObj schemaToDomain(final AddressObjects.Object modelAddrObj) throws SchemaToDomainMapperException{

            AddrObj addrObjParent = null;
            if (modelAddrObj.getPARENTGUID() != null){
                addrObjParent = addrObjRepository.findByAoGuid(UUID.fromString(modelAddrObj.getPARENTGUID()));
                if (addrObjParent == null){
                    throw new SchemaToDomainMapperException(
                            String.format("Couldn't find parent Object guid %s. Processing object %s was skipped",
                            modelAddrObj.getPARENTGUID(), modelAddrObj));
                }
            }
            AddrObj addrObj = new AddrObj();
            addrObj.setAoGuid(UUID.fromString(modelAddrObj.getAOGUID()));
            addrObj.setAoId(UUID.fromString(modelAddrObj.getAOID()));
            addrObj.setFormalName(modelAddrObj.getFORMALNAME());
            addrObj.setOfficialName(modelAddrObj.getOFFNAME());
            addrObj.setPostalCode(modelAddrObj.getPOSTALCODE());
            addrObj.setShortName(modelAddrObj.getSHORTNAME());
            addrObj.setParentObj(addrObjParent);

            return addrObj;
        }

        @Override
        public House schemaToDomain(final Houses.House modelHouseObject) throws SchemaToDomainMapperException{

            AddrObj addrObj = addrObjRepository.findByAoGuid(UUID.fromString(modelHouseObject.getAOGUID()));

            if (addrObj == null){
                throw new SchemaToDomainMapperException(
                        String.format("Couldn't find parent guid %s", UUID.fromString(modelHouseObject.getAOGUID())));
            } else {

                House house = new House();
                house.setHouseGuid(UUID.fromString(modelHouseObject.getHOUSEGUID()));
                house.setHouseId(UUID.fromString(modelHouseObject.getHOUSEID()));
                house.setPostalCode(modelHouseObject.getPOSTALCODE());
                house.setParentObj(addrObj);

                return house;
            }
        }

        @Override
        public Data domainToData(final AddrObj addrObj) throws DomainToDataMapperException{
            String nameObject = addrObj.getOfficialName();

            String parentObjectName = null;
            if (addrObj.getParentObj() != null){
                parentObjectName = addrObj.getParentObj().getOfficialName();
            }

            //Trying find out whether the current addrObject has the children at the his type
            List<AddrObj> childrenAddrObj = addrObjRepository.findByParentObj(addrObj);
            List<String> children = null;
            if (childrenAddrObj.size() > 0){
                children = FluentIterable.from(childrenAddrObj).transform(
                        new Function<AddrObj, String>() {
                            @Override
                            public String apply(AddrObj input) {
                                return input.getOfficialName();
                            }
                        }
                ).toList();
            } else { // ...or at underlying Houses instances
                children = FluentIterable.from(houseRepository.findByParentObj(addrObj)).transform(
                        new Function<House, String>() {
                            @Override
                            public String apply(House input) {
                                return input.getHouseNum();
                            }
                        }
                ).toList();
            }

            return new DataImpl(nameObject,parentObjectName,children);
        }

        @Override
        public Data domainToData(final House house) throws DomainToDataMapperException {
            String parentObjectName = house.getParentObj().getOfficialName();
            return new DataImpl(
                    house.getHouseNum(),
                    house.getParentObj().getOfficialName(),
                    null);
        }
    }

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
    public Data getObjectsBy(UUID uuid) {
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

        List<Data> addrObjList = FluentIterable.from(addrObjSearch.findByContent(content,pageable,null))
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
        List<Data> housesList = FluentIterable.from(houseSearch.findByContent(content,pageable,null))
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

    //FIXME: Here we'll probably occur with large memory overhead due to returning big list
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

    //FIXME: Here we'll probably occur with large memory overhead due to returning big list
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
