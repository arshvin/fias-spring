/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.fias.content.parsers;

import medved.fias.schema.AddressObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBElement;

import static java.lang.String.format;

/**
 *
 * @author arshvin
 */
@Component
public class AddrObjParser extends AbstractParser<AddressObjects.Object> {


    private Logger log = LoggerFactory.getLogger(AddrObjParser.class);

    @Override
    protected void processParsedData(final JAXBElement<AddressObjects.Object> element) {
        AddressObjects.Object modelAddrObj = element.getValue();

        log.info(format("Parsed object is %s", modelAddrObj));

    }
    
}
