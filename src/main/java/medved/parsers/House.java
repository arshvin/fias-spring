/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.parsers;

import javax.xml.bind.JAXBElement;
import medved.generated.Houses;

/**
 *
 * @author arshvin
 */
public class House extends AbstractParser<Houses> {

    public House(String sourceFile) {
        super(sourceFile);
    }

    @Override
    public void processParsedData(JAXBElement<Houses> element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
