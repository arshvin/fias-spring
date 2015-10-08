/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medved;

import java.io.FileNotFoundException;
import java.util.UUID;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import medved.domain.AddrObjRepository;

import medved.domain.HouseRepository;
import medved.parsers.AddrObjParser;
import medved.parsers.HouseParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 *
 * @author arshvin
 */
//@Ignore("Disactivated temporally for quikness...")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = medved.App.class)
public class TestParser {
    @Autowired
    private AddrObjRepository addrObjRepository;
    @Autowired
    private AddrObjParser addrObjParser;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private HouseParser houseParser;

    private String [] GUIDs = {
            "323febe6-cd89-4773-a46c-aab794fb7cbc",
            "0e8b1e3c-01e0-4ebd-9bfc-584b157b0b31",
            "8dac1393-f914-47f7-b78b-3cbacd8950f2",
            "00468156-f575-427d-9053-6a3523261a8d",
            "1b9deb75-7dbe-4844-85c6-0960df4185aa",
            "0aeaa2bf-e205-4316-8a0c-9f946d666ce4",
            "2edc2d55-26b1-446f-aa08-1cda9f130d50",
            "bb0fb535-0252-4296-89cb-4848f172f874",
            "86a36698-acd6-4cd3-ad70-9de5a6b2eb58"
    };
    
    @Test
    public void testAddrObjParser() throws FileNotFoundException, XMLStreamException, JAXBException{
        
        for (String item : GUIDs){
            assertNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }
        
        String testXml = ClassLoader.getSystemResource("AS_ADDROBJ_TEST.xml").getPath();
        addrObjParser.feed(testXml);
        addrObjParser.parse();
        
        for (String item : GUIDs){
            assertNotNull(addrObjRepository.findByAoGuid(UUID.fromString(item)));
        }
    }

    @Test
    public void testHouseParser() throws FileNotFoundException, XMLStreamException, JAXBException {
        assertFalse(houseRepository.findAll().iterator().hasNext());


        String testXml = ClassLoader.getSystemResource("AS_HOUSE_TEST.xml").getPath();
        houseParser.feed(testXml);
        houseParser.parse();


        for (String item : GUIDs){
            assertNotNull(houseRepository.findByParentObj(addrObjRepository.findByAoGuid(UUID.fromString(item))));
        }
    }
}
