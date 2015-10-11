/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.parsers;

import static java.lang.String.format;
import java.util.UUID;
import javax.xml.bind.JAXBElement;

import medved.domain.AddrObj;
import medved.domain.AddrObjRepository;
import medved.generated.AddressObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author arshvin
 */
@Component
public class AddrObjParser extends AbstractParser<AddressObjects.Object> {

    @Autowired
    private AddrObjRepository repo;
    
    private Logger log = LoggerFactory.getLogger(AddrObjParser.class);

    @Override
    protected void processParsedData(final JAXBElement<AddressObjects.Object> element) {
        AddressObjects.Object modelAddrObj = element.getValue();

        log.info(format("Parsed object is %s", modelAddrObj));

        assert repo != null;

        AddrObj addrObj = repo.findByAoGuid(UUID.fromString(modelAddrObj.getAOGUID()));
        if ((addrObj == null) ||
        (modelAddrObj.getNEXTID() == null && !UUID.fromString(modelAddrObj.getAOID()).equals(addrObj.getAoId()))){
            /**
            * Other words, if we couldn't find the entity with given AOGUID, then we have to create NEW AObject.
            * But if the entity already exits we are looking for field nextId which having it says us that the entity is not 
            * finale or actual state and then we ought to go further.
            * If it doesn't have that netxtId field therefore the AObject element has actual state, but if the AoId equals with
            * AoId existing entity that we nothing have to do. Otherwise we must merge (in fact create and save) entity data
            * from AObject element.
             */
            AddrObj addrObjParent = null;
            if (modelAddrObj.getPARENTGUID() != null){
                addrObjParent = repo.findByAoGuid(UUID.fromString(modelAddrObj.getPARENTGUID()));
                if (addrObjParent == null){
                    log.info(format("Couldn't find parent Obj's guid %s. Processing object %s was skipped",
                            modelAddrObj.getPARENTGUID(), modelAddrObj));
                    return;
                }
            }
            addrObj = new medved.domain.AddrObj();
            addrObj.setAoGuid(UUID.fromString(modelAddrObj.getAOGUID()));
            addrObj.setAoId(UUID.fromString(modelAddrObj.getAOID()));
            addrObj.setFormalName(modelAddrObj.getFORMALNAME());
            addrObj.setOfficialName(modelAddrObj.getOFFNAME());
            addrObj.setPostalCode(modelAddrObj.getPOSTALCODE());
            addrObj.setShortName(modelAddrObj.getSHORTNAME());
            addrObj.setParentObj(addrObjParent);

            log.info(format("Saving domain object %s", addrObj));

            repo.save(addrObj);
        }

        log.info(String.format("Processing object %s has been skipped", modelAddrObj));
    }
    
}
