/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.parsers;

import static java.lang.String.format;
import java.util.UUID;
import javax.xml.bind.JAXBElement;
import medved.domain.AddrObjRepository;
import medved.generated.AddressObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.slf4j.LoggerFactory.getLogger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author arshvin
 */
public class AddrObjParser extends AbstractParser<AddressObjects.Object> {
    @Autowired
    private AddrObjRepository repo;
    
    private Logger log = LoggerFactory.getLogger(AddrObjParser.class);
    
    public AddrObjParser(String sourceFile) {
        super(sourceFile);
    }

    @Override
    public void processParsedData(final JAXBElement<AddressObjects.Object> element) {
        AddressObjects.Object modelAddrObj = element.getValue();
        log.info("Parsed object is {0}", modelAddrObj);
        log.info("UUID.fromString(modelAddrObj.getAOGUID()) returns %s", UUID.fromString(modelAddrObj.getAOGUID()));
        medved.domain.AddrObj tmpAddrObj = repo.findByAoGuid(UUID.fromString(modelAddrObj.getAOGUID()));
        if ((tmpAddrObj == null) ||
                (modelAddrObj.getNEXTID() == null &&
                    !UUID.fromString(modelAddrObj.getAOID()).equals(tmpAddrObj.getAoId()))){
            /**
            * Other words, if we couldn't find the entity with given AOGUID, then we have to create NEW AObject.
            * But if the entity already exits we are looking for field nextId which having it says us that the entity is not 
            * finale or actual state and then we ought to go further.
            * If it doesn't have that netxtId field therefore the AObject element has actual state, but if the AoId equals with
            * AoId existing entity that we nothing have to do. Otherwise we must merge (in fact create and save) entity data
            * from AObject element.
             */
            tmpAddrObj = new medved.domain.AddrObj();
            tmpAddrObj.setAoGuid(UUID.fromString(modelAddrObj.getAOGUID()));
            tmpAddrObj.setAoId(UUID.fromString(modelAddrObj.getAOGUID()));
            tmpAddrObj.setFormalName(modelAddrObj.getFORMALNAME());
            tmpAddrObj.setOfficialName(modelAddrObj.getOFFNAME());
            tmpAddrObj.setPostalCode(modelAddrObj.getPOSTALCODE());
            tmpAddrObj.setShortName(modelAddrObj.getSHORTNAME());
            repo.save(tmpAddrObj);
        }
    }
    
}
