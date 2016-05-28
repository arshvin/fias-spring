/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved.fias.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

/**
 *
 * @author arshvin
 */
public abstract class AbstractParser<T>{
    protected String sourceFile;

    private final Class<T> clazz;

    private Logger log = LoggerFactory.getLogger(AbstractParser.class);

    public AbstractParser() {
        clazz = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void feed(String sourceFile) {
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
            JAXBElement<T> element = unmarshaller.unmarshal(xmlReader, clazz);
            processParsedData(element);
            xmlReader.nextTag();
        }

    }
    
    protected abstract void processParsedData(JAXBElement<T> element);
}
