package medved;

import medved.domain.AddrObj;
import medved.parsers.AbstractParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;

/**
 * Created by arshvin on 11.10.15.
 */
@Component
public class SimpleAddrObjParser extends AbstractParser<AddrObj> {
    Logger log = LoggerFactory.getLogger(SimpleAddrObjParser.class);

    @Override
    protected void processParsedData(JAXBElement element) {
        assert element.getValue() != null;

        log.info(String.format("Parsed obj %s", element.getValue()));
    }
}
