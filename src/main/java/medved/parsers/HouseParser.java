package medved.parsers;

import medved.domain.AddrObj;
import medved.domain.AddrObjRepository;
import medved.domain.House;
import medved.domain.HouseRepository;
import medved.generated.Houses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;
import java.util.UUID;

/**
 * Created by arshvin on 08.10.15.
 */
@Component
public class HouseParser extends AbstractParser<Houses.House> {

    @Autowired
    private HouseRepository repoHouse;

    @Autowired
    private AddrObjRepository repoAddrObj;

    private Logger log = LoggerFactory.getLogger(HouseParser.class);

    @Override
    protected void processParsedData(JAXBElement<Houses.House> element) {
        Houses.House modelHouse = element.getValue();

        log.info(String.format("Parsed house is %s", modelHouse));

        assert repoHouse != null;

        House tmpHouse = repoHouse.findByHouseGuid(UUID.fromString(modelHouse.getHOUSEGUID()));
        if(tmpHouse == null ||
                !UUID.fromString(modelHouse.getHOUSEID()).equals(tmpHouse.getHouseId())){

            AddrObj addrObj = repoAddrObj.findByAoGuid(UUID.fromString(modelHouse.getAOGUID()));

            if (addrObj == null){
                log.info(String.format("Couldn't find parent guid %s", UUID.fromString(modelHouse.getAOGUID())));
            } else {

                tmpHouse = new House();
                tmpHouse.setHouseGuid(UUID.fromString(modelHouse.getHOUSEGUID()));
                tmpHouse.setHouseId(UUID.fromString(modelHouse.getHOUSEID()));
                tmpHouse.setPostalCode(modelHouse.getPOSTALCODE());
                tmpHouse.setParentObj(addrObj);

                log.info(String.format("Saving domain object %s", tmpHouse));

                repoHouse.save(tmpHouse);
            }
        }

        log.debug(String.format("Domanin object %s is actual. Skiping", tmpHouse));
    }
}
