package medved.fias.storage.mappers.data;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import medved.fias.content.Data;
import medved.fias.schema.AddressObjects;
import medved.fias.schema.Houses;
import medved.fias.storage.domain.AddrObj;
import medved.fias.storage.domain.House;
import medved.fias.storage.mappers.exceptions.DomainToDataMapperException;
import medved.fias.storage.mappers.exceptions.SchemaToDomainMapperException;
import medved.fias.storage.repositories.AddrObjJpaRepository;
import medved.fias.storage.repositories.HouseJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by arshvin on 21.08.16.
 */
public class DataMapperImpl implements DataMapper {

    private AddrObjJpaRepository addrObjRepository;
    private HouseJpaRepository houseRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public DataMapperImpl(AddrObjJpaRepository addrObjRepository, HouseJpaRepository houseRepository) {
        this.addrObjRepository = addrObjRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public AddrObj schemaToDomain(final AddressObjects.Object modelAddrObj) throws SchemaToDomainMapperException {

        AddrObj addrObjParent = null;

        if (modelAddrObj.getPARENTGUID() != null){
            addrObjParent = addrObjRepository.findByAoGuid(UUID.fromString(modelAddrObj.getPARENTGUID()));

            logger.debug("addrObjParent of {} is {}", modelAddrObj.getAOGUID(), addrObjParent);

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
            house.setHouseNum(modelHouseObject.getHOUSENUM());

            return house;
        }
    }

    @Override
    public Data domainToData(final AddrObj addrObj) throws DomainToDataMapperException {
        String nameObject = addrObj.getOfficialName();

        String parentObjectName = null;
        if (addrObj.getParentObj() != null){
            parentObjectName = addrObj.getParentObj().getOfficialName();
        }

        //Trying find out whether the current addrObject has the children at the his type
        List<AddrObj> childrenAddrObj = addrObjRepository.findByParentObj(addrObj);
        List<String> children = null;

        logger.debug("The components of new Data object are nameObject={}, parentObjectName={}, children ... ",
                nameObject,parentObjectName);

        if (childrenAddrObj.size() > 0){
            for (AddrObj addrObj1 : childrenAddrObj){
                logger.debug("{}", addrObj1);
            }
        } else {
            for (House house : houseRepository.findByParentObj(addrObj)){
                logger.debug("{}", house);
            }
        }

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