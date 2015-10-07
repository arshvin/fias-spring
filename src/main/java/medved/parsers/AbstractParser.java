/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import medved.generated.AddressObjects;

/**
 *
 * @author arshvin
 */
public abstract class AbstractParser<T>{
    protected String sourceFile;
    private final Class clazz;
    
    public AbstractParser(String sourceFile) {
        //this.clazz = (Class<T>) ClassLoader.getSystemClassLoader().getClass();
        this.clazz = AddressObjects.Object.class;
        this.sourceFile = sourceFile;
    }
 
    public void parse() throws FileNotFoundException, XMLStreamException, JAXBException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(new FileReader(sourceFile));
        JAXBContext ucontext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = ucontext.createUnmarshaller();
        
        xmlReader.nextTag(); //<AddrObjects> or <Houses>
        xmlReader.nextTag(); //<AddrObj> or <House>
        
        while (xmlReader.getEventType() == START_ELEMENT){
            JAXBElement<AddressObjects.Object> element = unmarshaller.unmarshal(xmlReader, clazz);
            processParsedData(element);
            xmlReader.nextTag();
        }

    }
    
    public abstract void processParsedData(JAXBElement<AddressObjects.Object> element);
}
