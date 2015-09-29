/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.parsers;

import javax.xml.bind.JAXBElement;
import medved.domain.AddrObjRepository;
import medved.generated.AddressObjects;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author arshvin
 */
public class AddrObj extends AbstractParser<AddressObjects> {
    @Autowired
    private AddrObjRepository repo;
    
    public AddrObj(String sourceFile) {
        super(sourceFile);
    }

    @Override
    public void processParsedData(JAXBElement<AddressObjects> element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
