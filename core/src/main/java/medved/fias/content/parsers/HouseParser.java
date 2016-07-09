package medved.fias.content.parsers;

import medved.fias.schema.Houses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;

/**
 * Created by arshvin on 08.10.15.
 */
@Component
public class HouseParser extends AbstractParser<Houses.House> {

    private Logger log = LoggerFactory.getLogger(HouseParser.class);

    @Override
    protected void processParsedData(JAXBElement<Houses.House> element) {
        Houses.House modelHouse = element.getValue();

        log.info(String.format("Parsed house is %s", modelHouse));

    }
}
